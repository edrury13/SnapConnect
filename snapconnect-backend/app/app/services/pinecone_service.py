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
    index = None
    pc = None
    
    @classmethod
    def initialize(cls):
        """Initialize Pinecone connection"""
        if not PINECONE_AVAILABLE:
            logger.warning("Pinecone module not available, skipping initialization")
            return
            
        try:
            # Initialize Pinecone client (new API)
            cls.pc = Pinecone(api_key=settings.PINECONE_API_KEY)
            
            # Get list of indexes
            indexes = cls.pc.list_indexes()
            index_names = [idx.name for idx in indexes]
            
            # Create index if it doesn't exist
            if settings.PINECONE_INDEX_NAME not in index_names:
                cls.pc.create_index(
                    name=settings.PINECONE_INDEX_NAME,
                    dimension=1536,  # OpenAI embedding dimension
                    metric="cosine",
                    spec=ServerlessSpec(
                        cloud='aws',
                        region=settings.PINECONE_ENVIRONMENT
                    )
                )
                logger.info(f"Created new index: {settings.PINECONE_INDEX_NAME}")
            
            cls.index = cls.pc.Index(settings.PINECONE_INDEX_NAME)
            logger.info("Pinecone initialized successfully")
        except Exception as e:
            logger.error(f"Failed to initialize Pinecone: {e}")
            raise
    
    @classmethod
    def upsert_embedding(cls, id: str, embedding: List[float], metadata: Dict):
        """Store embedding in Pinecone"""
        try:
            cls.index.upsert([(id, embedding, metadata)])
            return True
        except Exception as e:
            logger.error(f"Failed to upsert embedding: {e}")
            return False
    
    @classmethod
    def search_similar(
        cls, 
        query_embedding: List[float], 
        top_k: int = 10,
        filter: Optional[Dict] = None
    ):
        """Search for similar embeddings"""
        try:
            results = cls.index.query(
                vector=query_embedding,
                top_k=top_k,
                filter=filter,
                include_metadata=True
            )
            return results.matches
        except Exception as e:
            logger.error(f"Search failed: {e}")
            return [] 