"""Pinecone service for vector database operations."""

import logging
from typing import List, Dict, Any, Optional
from dataclasses import dataclass
import pinecone
from pinecone import Pinecone, ServerlessSpec

from app.core.config import settings

logger = logging.getLogger(__name__)


@dataclass
class SearchResult:
    """Search result from Pinecone."""
    id: str
    score: float
    metadata: Optional[Dict[str, Any]] = None


class PineconeService:
    """Service for interacting with Pinecone vector database."""
    
    def __init__(self):
        self.index = None
        self._init_pinecone()
    
    def _init_pinecone(self):
        """Initialize Pinecone client and index."""
        try:
            # Initialize Pinecone client
            pc = Pinecone(api_key=settings.PINECONE_API_KEY)
            
            # Create index if it doesn't exist
            if settings.PINECONE_INDEX_NAME not in [idx.name for idx in pc.list_indexes()]:
                logger.info(f"Creating Pinecone index '{settings.PINECONE_INDEX_NAME}'")
                pc.create_index(
                    name=settings.PINECONE_INDEX_NAME,
                    dimension=settings.TEXT_EMBEDDING_DIM,
                    metric="cosine",
                    spec=ServerlessSpec(
                        cloud="aws",
                        region=settings.PINECONE_ENVIRONMENT
                    )
                )
            
            # Get index reference
            self.index = pc.Index(settings.PINECONE_INDEX_NAME)
            logger.info("Pinecone service initialized successfully")
            
        except Exception as e:
            logger.error(f"Failed to initialize Pinecone: {e}")
            self.index = None
    
    def upsert_embedding(
        self,
        id: str,
        embedding: List[float],
        metadata: Dict[str, Any],
        namespace: str = "default"
    ) -> bool:
        """Upsert an embedding to Pinecone."""
        if self.index is None:
            logger.error("Pinecone index not initialized")
            return False
        
        try:
            self.index.upsert(
                vectors=[(id, embedding, metadata)],
                namespace=namespace
            )
            logger.info(f"Upserted embedding {id} to namespace {namespace}")
            return True
        except Exception as e:
            logger.error(f"Failed to upsert embedding: {e}")
            return False
    
    def search_similar(
        self,
        query_embedding: List[float],
        top_k: int = 10,
        filter: Optional[Dict[str, Any]] = None,
        namespace: str = "default"
    ) -> List[SearchResult]:
        """Search for similar vectors in Pinecone."""
        if self.index is None:
            logger.error("Pinecone index not initialized")
            return []
        
        try:
            results = self.index.query(
                vector=query_embedding,
                top_k=top_k,
                filter=filter,
                namespace=namespace,
                include_metadata=True
            )
            
            return [
                SearchResult(
                    id=match.id,
                    score=match.score,
                    metadata=match.metadata
                )
                for match in results.matches
            ]
        except Exception as e:
            logger.error(f"Failed to search similar vectors: {e}")
            return []
    
    def delete_embedding(self, id: str, namespace: str = "default") -> bool:
        """Delete an embedding from Pinecone."""
        if self.index is None:
            logger.error("Pinecone index not initialized")
            return False
        
        try:
            self.index.delete(ids=[id], namespace=namespace)
            logger.info(f"Deleted embedding {id} from namespace {namespace}")
            return True
        except Exception as e:
            logger.error(f"Failed to delete embedding: {e}")
            return False 