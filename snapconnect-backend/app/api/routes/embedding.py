"""Embedding API routes."""

from fastapi import APIRouter, Depends, HTTPException
from pydantic import BaseModel
from typing import List, Optional

from app.core.security import verify_api_key
from app.dependencies import get_embedding_service
from app.services.embedding_service import EmbeddingService

router = APIRouter()


class TextEmbeddingRequest(BaseModel):
    text: str


class TextEmbeddingResponse(BaseModel):
    embedding: List[float]
    dimension: int


@router.post("/text", response_model=TextEmbeddingResponse)
async def generate_text_embedding(
    request: TextEmbeddingRequest,
    api_key: str = Depends(verify_api_key),
    embedding_service: EmbeddingService = Depends(get_embedding_service),
):
    """Generate embedding for text input."""
    try:
        embedding = await embedding_service.generate_text_embedding(request.text)
        if not embedding:
            raise HTTPException(status_code=500, detail="Failed to generate embedding")
        
        return TextEmbeddingResponse(
            embedding=embedding,
            dimension=len(embedding)
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


# Image embedding endpoint (placeholder - returns 501 Not Implemented)
@router.post("/image")
async def generate_image_embedding(
    api_key: str = Depends(verify_api_key),
):
    """Generate embedding for image input (not implemented)."""
    raise HTTPException(status_code=501, detail="Image embedding not implemented yet") 