from typing import List, Optional, Dict, Any
import os
import openai
import logging
import hashlib

logger = logging.getLogger(__name__)

class EmbeddingService:
    def __init__(self):
        """Initialize embedding service with OpenAI"""
        self.api_key = os.getenv("OPENAI_API_KEY")
        if self.api_key:
            openai.api_key = self.api_key
            self.client = openai.OpenAI(api_key=self.api_key)
            logger.info("OpenAI embedding service initialized")
        else:
            self.client = None
            logger.warning("OPENAI_API_KEY not set, embedding functionality disabled")
        
        # Use text-embedding-3-large for 1024 dimensions
        self.embedding_model = os.getenv("TEXT_EMBEDDING_MODEL", "text-embedding-3-large")
        self.embedding_dimensions = 1024  # For text-embedding-3-large with custom dimensions
    
    async def generate_text_embedding(self, text: str) -> Optional[List[float]]:
        """Generate embedding for text using OpenAI"""
        if not self.client:
            logger.warning("OpenAI client not initialized")
            return None
        
        try:
            response = self.client.embeddings.create(
                model=self.embedding_model,
                input=text,
                dimensions=self.embedding_dimensions  # Specify dimensions for v3 models
            )
            embedding = response.data[0].embedding
            return embedding
        except Exception as e:
            logger.error(f"Failed to generate text embedding: {e}")
            return None
    
    async def generate_image_embedding(self, image_url: str) -> Optional[List[float]]:
        """Generate embedding for image (placeholder - would need vision model)"""
        # For now, we'll generate a text embedding based on image URL
        # In production, you'd use a vision model or multimodal embedding
        if not image_url:
            return None
        
        # Extract filename or identifier from URL
        filename = image_url.split('/')[-1].split('?')[0]
        return await self.generate_text_embedding(f"Image: {filename}")
    
    def prepare_metadata(
        self,
        content_type: str,
        user_id: str,
        tags: List[str] = None,
        **kwargs
    ) -> Dict[str, Any]:
        """Prepare metadata for Pinecone storage"""
        metadata = {
            "content_type": content_type,
            "user_id": user_id,
            "timestamp": kwargs.get("timestamp", "")
        }
        
        if tags:
            metadata["tags"] = tags
        
        # Add any additional metadata
        for key, value in kwargs.items():
            if key not in metadata:
                metadata[key] = value
        
        return metadata
    
    def generate_id(self, content: str) -> str:
        """Generate unique ID for content"""
        return hashlib.sha256(content.encode()).hexdigest()[:32] 