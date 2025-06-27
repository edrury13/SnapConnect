"""Vision API routes for image analysis."""

from fastapi import APIRouter, Depends, HTTPException
from pydantic import BaseModel
from typing import List

from app.core.security import verify_api_key
from app.dependencies import get_vision_service
from app.services.vision_service import VisionService

router = APIRouter()


class AnalyzeImageRequest(BaseModel):
    image_url: str


class AnalyzeImageResponse(BaseModel):
    tags: List[str]


@router.post("/analyze", response_model=AnalyzeImageResponse)
async def analyze_image(
    request: AnalyzeImageRequest,
    api_key: str = Depends(verify_api_key),
    vision_service: VisionService = Depends(get_vision_service)
):
    """Analyze an image and extract descriptive tags."""
    try:
        tags = await vision_service.analyze_image(request.image_url)
        return AnalyzeImageResponse(tags=tags)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e)) 