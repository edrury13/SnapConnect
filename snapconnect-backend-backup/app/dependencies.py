from app.app.services.pinecone_service import PineconeService
from app.app.services.embedding_service import EmbeddingService
from app.app.services.langchain_service import LangChainService
from app.app.services.vision_service import VisionService

# Singleton instances
_pinecone_service = None
_embedding_service = None
_langchain_service = None
_vision_service = None

def get_pinecone_service() -> PineconeService:
    """Get or create PineconeService singleton"""
    global _pinecone_service
    if _pinecone_service is None:
        _pinecone_service = PineconeService()
    return _pinecone_service

def get_embedding_service() -> EmbeddingService:
    """Get or create EmbeddingService singleton"""
    global _embedding_service
    if _embedding_service is None:
        from app.app.services.embedding_service import EmbeddingService
        _embedding_service = EmbeddingService()
    return _embedding_service

def get_langchain_service() -> LangChainService:
    """Get or create LangChainService singleton"""
    global _langchain_service
    if _langchain_service is None:
        from app.app.services.langchain_service import LangChainService
        _langchain_service = LangChainService()
    return _langchain_service

def get_vision_service() -> VisionService:
    """Get or create VisionService singleton"""
    global _vision_service
    if _vision_service is None:
        from app.app.services.vision_service import VisionService
        _vision_service = VisionService()
    return _vision_service 