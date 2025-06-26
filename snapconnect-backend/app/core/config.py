from pydantic_settings import BaseSettings
from typing import List
import os

class Settings(BaseSettings):
    # API Settings
    API_KEY: str = os.getenv("API_KEY", "your-api-key")
    ALLOWED_ORIGINS: List[str] = ["http://localhost:3000", "https://snapconnect.app"]
    
    # OpenAI Settings
    OPENAI_API_KEY: str = os.getenv("OPENAI_API_KEY", "")
    EMBEDDING_MODEL: str = os.getenv("TEXT_EMBEDDING_MODEL", "text-embedding-3-large")
    
    # Image Embedding Settings
    IMAGE_EMBEDDING_MODEL: str = os.getenv("IMAGE_EMBEDDING_MODEL", "image-embedding-3-small")
    IMAGE_EMBEDDING_DIM: int = int(os.getenv("IMAGE_EMBEDDING_DIM", 1024))
    
    # Pinecone Settings
    PINECONE_API_KEY: str = os.getenv("PINECONE_API_KEY", "")
    PINECONE_ENVIRONMENT: str = "us-east-1"  # Just the region without "-aws"
    PINECONE_INDEX_NAME: str = os.getenv("PINECONE_INDEX_NAME", "snapconnect")
    
    # Redis Settings (optional for caching)
    REDIS_URL: str = os.getenv("REDIS_URL", "")
    
    # App Settings
    ENVIRONMENT: str = os.getenv("ENVIRONMENT", "development")
    MAX_UPLOAD_SIZE: int = 10 * 1024 * 1024  # 10MB
    
    class Config:
        env_file = ".env"

settings = Settings() 