from typing import Optional, List, Dict, Any
import os
import pinecone
from pinecone import Pinecone
import logging

logger = logging.getLogger(__name__)

class PineconeService:
    def __init__(self):
        """Initialize Pinecone service"""
        self.index = None
        self.index_name = os.getenv("PINECONE_INDEX_NAME", "snapconnect")
        
        api_key = os.getenv("PINECONE_API_KEY")
        if api_key:
            try:
                # Initialize Pinecone
                pc = Pinecone(api_key=api_key)
                
                # Connect to index
                if self.index_name in pc.list_indexes().names():
                    self.index = pc.Index(self.index_name)
                    logger.info(f"Connected to Pinecone index: {self.index_name}")
                else:
                    logger.warning(f"Pinecone index {self.index_name} not found")
                    # Don't create index automatically - it should already exist
                    # with the correct dimensions (1025)
            except Exception as e:
                logger.error(f"Failed to initialize Pinecone: {e}")
        else:
            logger.warning("PINECONE_API_KEY not set, Pinecone functionality disabled")
    
    def upsert_embedding(
        self, 
        id: str, 
        embedding: List[float], 
        metadata: Dict[str, Any], 
        namespace: str = ""
    ) -> bool:
        """Upsert a single embedding to Pinecone"""
        if self.index is None:
            logger.warning("Pinecone index not initialized")
            return False
        
        try:
            self.index.upsert(
                vectors=[(id, embedding, metadata)],
                namespace=namespace
            )
            return True
        except Exception as e:
            logger.error(f"Failed to upsert embedding: {e}")
            return False
    
    def search_similar(
        self,
        query_embedding: List[float],
        top_k: int = 10,
        filter: Optional[Dict[str, Any]] = None,
        namespace: str = "",
        include_metadata: bool = True
    ) -> List[Any]:
        """Search for similar embeddings in Pinecone"""
        if self.index is None:
            logger.warning("Pinecone index not initialized")
            return []
        
        try:
            results = self.index.query(
                vector=query_embedding,
                top_k=top_k,
                filter=filter,
                namespace=namespace,
                include_metadata=include_metadata
            )
            return results.matches
        except Exception as e:
            logger.error(f"Failed to search similar embeddings: {e}")
            return []
    
    def delete_by_id(self, ids: List[str], namespace: str = "") -> bool:
        """Delete embeddings by ID"""
        if self.index is None:
            logger.warning("Pinecone index not initialized")
            return False
        
        try:
            self.index.delete(ids=ids, namespace=namespace)
            return True
        except Exception as e:
            logger.error(f"Failed to delete embeddings: {e}")
            return False
    
    def delete_by_filter(self, filter: Dict[str, Any], namespace: str = "") -> bool:
        """Delete embeddings by metadata filter"""
        if self.index is None:
            logger.warning("Pinecone index not initialized")
            return False
        
        try:
            self.index.delete(filter=filter, namespace=namespace)
            return True
        except Exception as e:
            logger.error(f"Failed to delete embeddings by filter: {e}")
            return False 