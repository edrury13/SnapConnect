"""Embedding service for generating text and image embeddings."""

import logging
from typing import List, Optional, Dict, Any
from openai import OpenAI

from app.core.config import settings

logger = logging.getLogger(__name__)


class EmbeddingService:
    """Service for generating embeddings using OpenAI."""
    
    def __init__(self):
        self.client = OpenAI(api_key=settings.OPENAI_API_KEY)
        self.model = settings.TEXT_EMBEDDING_MODEL
        self.dimensions = settings.TEXT_EMBEDDING_DIM
    
    async def generate_text_embedding(self, text: str) -> Optional[List[float]]:
        """Generate embedding for text input."""
        try:
            response = self.client.embeddings.create(
                model=self.model,
                input=text,
                dimensions=self.dimensions
            )
            embedding = response.data[0].embedding
            logger.info(f"Generated text embedding of dimension {len(embedding)}")
            return embedding
        except Exception as e:
            logger.error(f"Failed to generate text embedding: {e}")
            return None
    
    async def generate_image_embedding(self, image_url: str) -> Optional[List[float]]:
        """Generate embedding for image (not implemented yet)."""
        logger.warning("Image embedding not implemented, returning None")
        return None
    
    def prepare_metadata(
        self, 
        content_type: str,
        user_id: str,
        tags: List[str],
        **kwargs
    ) -> Dict[str, Any]:
        """Prepare metadata for storing with embeddings."""
        metadata = {
            "content_type": content_type,
            "user_id": user_id,
            "tags": tags,
        }
        # Add any additional metadata
        metadata.update(kwargs)
        return metadata 