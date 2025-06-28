from fastapi import APIRouter, Depends, HTTPException
from pydantic import BaseModel
from typing import List, Dict, Optional
import hashlib
from uuid import uuid4
import logging

from app.core.security import verify_api_key
from app.dependencies import get_langchain_service, get_embedding_service, get_pinecone_service, get_vision_service
from app.services.langchain_service import LangChainService
from app.services.embedding_service import EmbeddingService
from app.services.pinecone_service import PineconeService
from app.services.vision_service import VisionService

logger = logging.getLogger(__name__)

router = APIRouter()

class FeedbackRequest(BaseModel):
    description: str
    content_type: str = "art"

class CollaboratorRequest(BaseModel):
    user_profile: Dict
    project_needs: str

class SummaryRequest(BaseModel):
    updates: List[str]

class AutoCaptionRequest(BaseModel):
    tags: str
    user_id: str = ""

class ProcessPostRequest(BaseModel):
    user_id: str
    story_id: str
    caption: str
    tags: List[str] = []
    image_url: Optional[str] = None  # Added for vision analysis

class ProcessPostResponse(BaseModel):
    ai_caption: str
    style: str
    style_confidence: Optional[float] = None  # Added confidence
    technique: Optional[str] = None  # Added technique

@router.post("/feedback")
async def generate_feedback(
    request: FeedbackRequest,
    api_key: str = Depends(verify_api_key),
    langchain_service: LangChainService = Depends(get_langchain_service)
):
    """Generate constructive feedback for a creative work using LangChain."""
    try:
        feedback = await langchain_service.generate_feedback(
            description=request.description,
            content_type=request.content_type,
        )
        return {"feedback": feedback}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("/collaborators")
async def find_collaborators(
    request: CollaboratorRequest,
    api_key: str = Depends(verify_api_key),
    langchain_service: LangChainService = Depends(get_langchain_service)
):
    """Find potential collaborators that complement the user's skills."""
    try:
        result = await langchain_service.find_collaborators(
            user_profile=request.user_profile,
            project_needs=request.project_needs,
        )
        return {"result": result}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("/summary")
async def project_summary(
    request: SummaryRequest,
    api_key: str = Depends(verify_api_key),
    langchain_service: LangChainService = Depends(get_langchain_service)
):
    """Summarise project updates into a concise report."""
    try:
        summary = await langchain_service.generate_project_summary(request.updates)
        return {"summary": summary}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("/auto-caption")
async def auto_caption(
    request: AutoCaptionRequest,
    api_key: str = Depends(verify_api_key),
    langchain_service: LangChainService = Depends(get_langchain_service),
    embedding_service: EmbeddingService = Depends(get_embedding_service),
    pinecone_service: PineconeService = Depends(get_pinecone_service),
):
    """Generate AI caption and embed it immediately."""
    try:
        caption = await langchain_service.generate_caption(request.tags)
        # Embed into pinecone
        vec = await embedding_service.generate_text_embedding(caption)
        if vec and pinecone_service.index is not None:
            mid = hashlib.sha256(caption.encode()).hexdigest()[:32]
            metadata = embedding_service.prepare_metadata(
                content_type="text",
                user_id=request.user_id,
                tags=request.tags.split(","),
            )
            pinecone_service.upsert_embedding(mid, vec, metadata, namespace="community-assets")
        return {"caption": caption}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@router.post("/process-post", response_model=ProcessPostResponse)
async def process_post(
    req: ProcessPostRequest,
    api_key: str = Depends(verify_api_key),
    langchain_service: LangChainService = Depends(get_langchain_service),
    embedding_service: EmbeddingService = Depends(get_embedding_service),
    pinecone_service: PineconeService = Depends(get_pinecone_service),
    vision_service: VisionService = Depends(get_vision_service),
):
    """Enhanced process_post with style-first workflow."""
    logger.info(f"Processing post with tags: {req.tags}")
    media_type = "video" if "video" in req.tags else "image"
    
    # Step 1: Perform detailed vision analysis if image URL provided
    vision_analysis = None
    if req.image_url and media_type == "image":
        try:
            vision_analysis = await vision_service.analyze_image_detailed(req.image_url)
            logger.info(f"Vision analysis completed: style={vision_analysis.get('artistic_style')}, technique={vision_analysis.get('technique')}")
        except Exception as e:
            logger.error(f"Vision analysis failed: {e}")
            # Fallback to basic analysis
            vision_analysis = {
                "artistic_style": "unknown",
                "technique": "image",
                "subjects": req.tags,
                "mood": "neutral",
                "style_characteristics": []
            }
    else:
        # Use tags for basic analysis
        vision_analysis = {
            "artistic_style": " ".join(req.tags),
            "technique": media_type,
            "subjects": req.tags,
            "mood": "neutral",
            "style_characteristics": []
        }
    
    # Step 2: Detect style with confidence
    style_info = await langchain_service.detect_style_with_confidence(vision_analysis)
    logger.info(f"Style detection: {style_info['style_name']} (confidence: {style_info['confidence']})")
    
    # Step 3: Check if we need to add new style
    # Note: Cosine similarity ranges from -1 to 1, with 1 being identical
    # In practice, we usually see values between 0.5-0.95 for text similarity
    # Using 0.65 as threshold - below this, the match is too weak
    if style_info['confidence'] < 0.65:
        logger.info(f"Low confidence style match ({style_info['confidence']:.3f}), checking if new style needed")
        logger.info(f"Detected style: {style_info['style_name']}, Description: {style_info.get('description', '')[:100]}...")
        
        # Extract style name from vision analysis
        artistic_style = vision_analysis.get('artistic_style', 'unknown')
        new_style_name = artistic_style.lower().replace(' ', '_')
        
        # Check if this style already exists (skip if it's generic)
        style_exists = await langchain_service.check_style_exists(new_style_name, threshold=0.9)
        
        # Don't add generic/unclear styles to taxonomy
        if not style_exists and new_style_name not in ['unknown', 'none', 'unidentified', 'general']:
            # Prepare style info for addition
            new_style_info = {
                'style_name': new_style_name,
                'technique': vision_analysis.get('technique', 'unknown'),
                'style_characteristics': vision_analysis.get('style_characteristics', []),
                'description': style_info.get('description', ''),
                'story_id': req.story_id
            }
            
            # Add to taxonomy
            added = await langchain_service.add_style_to_taxonomy(new_style_info)
            if added:
                logger.info(f"Added new style to taxonomy: {new_style_name}")
                # Update style_info to use the new style
                style_info['style_name'] = new_style_name
    
    # Step 4: Generate style-aware caption
    ai_caption = await langchain_service.generate_style_aware_caption(
        vision_analysis, 
        style_info,
        media_type
    )
    logger.info(f"Generated style-aware AI caption: {ai_caption}")
    
    # Step 5: Store embeddings
    metadata = {
        "user_id": req.user_id,
        "tags": req.tags,
        "style_tags": [style_info['style_name']],
        "technique": style_info.get('technique', 'unknown'),
        "story_id": req.story_id,
    }

    # Embed both user caption and AI caption
    for text in (req.caption, ai_caption):
        if text:  # Only embed non-empty text
            vec = await embedding_service.generate_text_embedding(text)
            if vec and pinecone_service.index is not None:
                pinecone_service.upsert_embedding(
                    id=str(uuid4()),
                    embedding=vec,
                    metadata=metadata,
                    namespace="community-assets",
                )
    
    # Step 6: Update Supabase stories table
    try:
        import httpx, os, json
        supabase_url = os.getenv("SUPABASE_URL")
        supabase_key = os.getenv("SUPABASE_SERVICE_ROLE_KEY")
        if supabase_url and supabase_key:
            headers = {
                "apikey": supabase_key,
                "Authorization": f"Bearer {supabase_key}",
                "Content-Type": "application/json",
                "Prefer": "return=representation",
            }
            response = httpx.patch(
                f"{supabase_url}/rest/v1/stories?id=eq.{req.story_id}",
                headers=headers,
                content=json.dumps({
                    "style_tags": [style_info['style_name']],
                    "ai_caption": ai_caption
                }),
            )
            logger.info(f"Supabase update response: {response.status_code}")
            if response.status_code not in (200, 204):
                logger.error(f"Supabase update failed: {response.text}")
        else:
            logger.warning("Supabase credentials not configured")
    except Exception as e:
        logger.warning(f"Failed to update Supabase style tags: {e}")

    return ProcessPostResponse(
        ai_caption=ai_caption, 
        style=style_info['style_name'],
        style_confidence=style_info['confidence'],
        technique=style_info.get('technique', 'unknown')
    ) 