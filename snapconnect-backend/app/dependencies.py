from functools import lru_cache
from app.services.pinecone_service import PineconeService
from app.services.embedding_service import EmbeddingService
from app.services.langchain_service import LangChainService

# lru_cache(maxsize=1) is a simple way to create a singleton.
# The service will be instantiated only on the first call,
# and subsequent calls will return the same instance from cache.

@lru_cache(maxsize=1)
def get_pinecone_service() -> PineconeService:
    """Lazy-loads and returns a singleton instance of PineconeService."""
    return PineconeService()

@lru_cache(maxsize=1)
def get_embedding_service() -> EmbeddingService:
    """Lazy-loads and returns a singleton instance of EmbeddingService."""
    return EmbeddingService()

@lru_cache(maxsize=1)
def get_langchain_service() -> LangChainService:
    """Lazy-loads and returns a singleton instance of LangChainService."""
    return LangChainService() 