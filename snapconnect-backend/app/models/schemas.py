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