from fastapi import APIRouter, Depends, HTTPException
from pydantic import BaseModel
from typing import Dict

from app.core.security import verify_api_key
from app.dependencies import get_vision_service
from app.services.vision_service import VisionService

router = APIRouter()

class VisionAnalyzeRequest(BaseModel):
    image_url: str

@router.post("/analyze")
async def analyze_image(
    req: VisionAnalyzeRequest,
    vision_service: VisionService = Depends(get_vision_service),
    _ : str = Depends(verify_api_key)
) -> Dict:
    """Analyze an image and return detailed vision analysis.
    Currently returns placeholder data from VisionService."""
    try:
        return await vision_service.analyze_image_detailed(req.image_url)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e)) 