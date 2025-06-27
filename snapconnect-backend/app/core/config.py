"""Application configuration settings."""

from pydantic_settings import BaseSettings
from typing import Optional


class Settings(BaseSettings):
    """Application settings."""
    
    # API Keys
    OPENAI_API_KEY: str
    PINECONE_API_KEY: str
    SUPABASE_URL: str
    SUPABASE_SERVICE_ROLE_KEY: str
    API_KEY: str  # For API authentication
    
    # Pinecone settings
    PINECONE_INDEX_NAME: str = "snapconnect"
    PINECONE_ENVIRONMENT: str = "us-east-1-aws"
    
    # OpenAI settings
    TEXT_EMBEDDING_MODEL: str = "text-embedding-3-large"
    TEXT_EMBEDDING_DIM: int = 1024
    
    class Config:
        env_file = ".env"
        case_sensitive = True


# Create settings instance
settings = Settings() 