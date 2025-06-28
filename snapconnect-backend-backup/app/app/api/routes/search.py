from fastapi import APIRouter, HTTPException, Depends
from app.models.schemas import SearchRequest, SearchResponse, SearchResult
from app.services.embedding_service import EmbeddingService
from app.services.pinecone_service import PineconeService
from app.core.security import verify_api_key
from app.dependencies import get_embedding_service, get_pinecone_service

router = APIRouter()

@router.post("/", response_model=SearchResponse)
async def search_content(
    request: SearchRequest,
    api_key: str = Depends(verify_api_key),
    embedding_service: EmbeddingService = Depends(get_embedding_service),
    pinecone_service: PineconeService = Depends(get_pinecone_service)
):
    """Search for similar content"""
    try:
        # Generate query embedding
        query_embedding = await embedding_service.generate_text_embedding(request.query)
        if not query_embedding:
            raise HTTPException(status_code=500, detail="Failed to generate query embedding")
        
        # Search in Pinecone
        if pinecone_service.index is not None:
            matches = pinecone_service.search_similar(
                query_embedding=query_embedding,
                top_k=request.top_k,
                filter=request.filters
            )
        else:
            # Pinecone not available, return empty results
            matches = []
        
        # Format results
        results = [
            SearchResult(
                content_id=match.id,
                score=match.score,
                metadata=match.metadata
            )
            for match in matches
        ]
        
        return SearchResponse(
            results=results,
            total=len(results)
        )
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e)) 