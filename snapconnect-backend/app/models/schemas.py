from pydantic import BaseModel
from typing import List, Optional, Dict, Any
from datetime import datetime

class EmbedRequest(BaseModel):
    content: str
    user_id: str
    tags: Optional[List[str]] = []
    metadata: Optional[Dict[str, Any]] = {}

class EmbedResponse(BaseModel):
    content_id: str
    status: str
    message: str

class SearchRequest(BaseModel):
    query: str
    search_type: str = "general"  # general, collaborator, inspiration
    filters: Optional[Dict[str, Any]] = {}
    top_k: int = 10

class SearchResult(BaseModel):
    content_id: str
    score: float
    metadata: Dict[str, Any]

class SearchResponse(BaseModel):
    results: List[SearchResult]
    total: int

# ---------------- Inspiration Feature Schemas ----------------

class MoodBoardRequest(BaseModel):
    description: str
    limit: int = 8

class MoodBoardItem(BaseModel):
    content_id: str
    score: float
    content_url: str
    style_tags: List[str] = []
    creator_id: str

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