from typing import List, Dict, Optional
from app.core.config import settings
import logging

logger = logging.getLogger(__name__)

try:
    from pinecone import Pinecone, ServerlessSpec
    PINECONE_AVAILABLE = True
except ImportError:
    PINECONE_AVAILABLE = False
    logger.warning("Pinecone not available - running in limited mode")

class PineconeService:
    def __init__(self):
        """Initialize Pinecone connection on first instantiation."""
        self.index = None
        self.pc = None
        if not PINECONE_AVAILABLE:
            logger.warning("Pinecone module not available, skipping initialization")
            return
            
        try:
            logger.info("Initializing Pinecone Service (Lazy-Loaded)...")
            # Initialize Pinecone client
            self.pc = Pinecone(api_key=settings.PINECONE_API_KEY)
            
            # Get list of indexes
            index_names = [idx.name for idx in self.pc.list_indexes()]
            
            # Create index if it doesn't exist
            if settings.PINECONE_INDEX_NAME not in index_names:
                self.pc.create_index(
                    name=settings.PINECONE_INDEX_NAME,
                    dimension=settings.IMAGE_EMBEDDING_DIM,
                    metric="cosine",
                    spec=ServerlessSpec(
                        cloud='aws',
                        region=settings.PINECONE_ENVIRONMENT
                    )
                )
                logger.info(f"Created new index: {settings.PINECONE_INDEX_NAME}")
            
            self.index = self.pc.Index(settings.PINECONE_INDEX_NAME)
            logger.info("Pinecone initialized successfully")
        except Exception as e:
            logger.error(f"Failed to initialize Pinecone: {e}")
            # Ensure index is None if init fails
            self.index = None

    def upsert_embedding(
        self,
        id: str,
        embedding: List[float],
        metadata: Dict,
        namespace: str = "community-assets",
    ):
        """Store embedding in Pinecone under the given namespace (default: community-assets)"""
        if not self.index:
            logger.error("Cannot upsert: Pinecone index not available.")
            return False
        try:
            self.index.upsert([(id, embedding, metadata)], namespace=namespace)
            return True
        except Exception as e:
            logger.error(f"Failed to upsert embedding: {e}")
            return False
    
    def search_similar(
        self,
        query_embedding: List[float],
        top_k: int = 10,
        filter: Optional[Dict] = None,
        namespace: str = "community-assets",
    ):
        """Search for similar embeddings"""
        if not self.index:
            logger.error("Cannot search: Pinecone index not available.")
            return []
        try:
            results = self.index.query(
                vector=query_embedding,
                top_k=top_k,
                filter=filter,
                include_metadata=True,
                namespace=namespace,
            )
            return results.matches
        except Exception as e:
            logger.error(f"Search failed: {e}")
            return [] 