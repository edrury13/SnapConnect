from fastapi import APIRouter, Depends, HTTPException
from pydantic import BaseModel
from typing import List
import os, logging, openai

from app.core.security import verify_api_key

logger = logging.getLogger(__name__)

router = APIRouter()

class GenerateImagesRequest(BaseModel):
    prompt: str
    n: int = 4
    size: str = "1024x1024"

class GenerateImagesResponse(BaseModel):
    urls: List[str]

@router.post("/generate", response_model=GenerateImagesResponse)
async def generate_images(
    req: GenerateImagesRequest,
    _: str = Depends(verify_api_key)
):
    key = os.getenv("OPENAI_API_KEY")
    if not key:
        raise HTTPException(status_code=500, detail="OpenAI key not configured")
    try:
        client = openai.OpenAI(api_key=key)
        resp = await client.images.generate_async(prompt=req.prompt, n=req.n, size=req.size)
        urls = [d.url for d in resp.data]
        return GenerateImagesResponse(urls=urls)
    except Exception as e:
        logger.error(f"OpenAI image generation failed: {e}")
        raise HTTPException(status_code=500, detail=str(e)) 