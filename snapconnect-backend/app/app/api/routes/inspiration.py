"""Inspiration API routes for moodboard and style analysis."""

from fastapi import APIRouter, Depends, HTTPException, Body
from pydantic import BaseModel
from typing import List, Optional
from collections import Counter

from app.core.security import verify_api_key
from app.dependencies import get_embedding_service, get_pinecone_service
from app.services.embedding_service import EmbeddingService
from app.services.pinecone_service import PineconeService

router = APIRouter()


class MoodBoardRequest(BaseModel):
    description: str
    limit: int = 8


class MoodBoardItem(BaseModel):
    content_id: str
    score: float
    content_url: str = ""
    style_tags: List[str] = []
    creator_id: str = ""


class MoodBoardResponse(BaseModel):
    items: List[MoodBoardItem]


class StyleAnalysisRequest(BaseModel):
    description: str
    limit: int = 10


class SimilarCreator(BaseModel):
    creator_id: str
    score: float


class StyleAnalysisResponse(BaseModel):
    dominant_style: str
    similar_creators: List[SimilarCreator]
    reference_items: List[MoodBoardItem]


@router.post("/moodboard", response_model=MoodBoardResponse)
async def generate_moodboard(
    request: MoodBoardRequest,
    api_key: str = Depends(verify_api_key),
    embedding_service: EmbeddingService = Depends(get_embedding_service),
    pinecone_service: PineconeService = Depends(get_pinecone_service),
):
    """Generate a moodboard based on text description."""
    try:
        # Generate embedding for the description
        query_vec = await embedding_service.generate_text_embedding(request.description)
        if not query_vec:
            raise HTTPException(status_code=500, detail="Failed to generate embedding")
        
        # Search for similar content
        matches = pinecone_service.search_similar(
            query_embedding=query_vec,
            top_k=request.limit,
            namespace="community-assets"
        )
        
        # Convert matches to MoodBoardItems
        items = []
        for match in matches:
            metadata = match.metadata or {}
            items.append(
                MoodBoardItem(
                    content_id=match.id,
                    score=match.score,
                    content_url=metadata.get("content_url", ""),
                    style_tags=metadata.get("style_tags", []),
                    creator_id=metadata.get("creator_id", "")
                )
            )
        
        return MoodBoardResponse(items=items)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("/style-analysis", response_model=StyleAnalysisResponse)
async def style_analysis(
    request: StyleAnalysisRequest = Body(...),
    api_key: str = Depends(verify_api_key),
    embedding_service: EmbeddingService = Depends(get_embedding_service),
    pinecone_service: PineconeService = Depends(get_pinecone_service),
):
    """Analyze an uploaded image's style and return similar creators."""
    # Use caption/text embedding only (image upload disabled for now)
    query_vec = await embedding_service.generate_text_embedding(request.description)
    if not query_vec:
        raise HTTPException(status_code=500, detail="Failed to generate text embedding")

    matches = pinecone_service.search_similar(
        query_embedding=query_vec,
        top_k=request.limit,
        filter={"content_type": {"$eq": "image"}},
        namespace="community-assets",
    )

    # Determine dominant style by majority vote among style tags
    style_counter: Counter = Counter()
    creators: List[SimilarCreator] = []
    items: List[MoodBoardItem] = []

    for m in matches:
        md = m.metadata or {}
        style_tags = md.get("style_tags", [])
        style_counter.update(style_tags)

        items.append(
            MoodBoardItem(
                content_id=m.id,
                score=m.score,
                content_url=md.get("content_url", ""),
                style_tags=style_tags,
                creator_id=md.get("creator_id", ""),
            )
        )

        if md.get("creator_id"):
            creators.append(
                SimilarCreator(
                    creator_id=md["creator_id"],
                    score=m.score,
                )
            )

    dominant_style = style_counter.most_common(1)[0][0] if style_counter else "unknown"

    # Deduplicate creators and keep top 3 by score
    unique_creators = {}
    for c in sorted(creators, key=lambda x: x.score, reverse=True):
        if c.creator_id not in unique_creators:
            unique_creators[c.creator_id] = c
        if len(unique_creators) >= 3:
            break

    return StyleAnalysisResponse(
        dominant_style=dominant_style,
        similar_creators=list(unique_creators.values()),
        reference_items=items,
    ) 