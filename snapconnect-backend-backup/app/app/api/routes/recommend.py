from fastapi import APIRouter, HTTPException, Depends
from pydantic import BaseModel
from typing import List, Dict, Optional
from datetime import datetime, timedelta
import logging
from collections import defaultdict

from app.models.schemas import SearchResult
from app.services.pinecone_service import PineconeService
from app.services.embedding_service import EmbeddingService
from app.core.security import verify_api_key
from app.dependencies import get_pinecone_service, get_embedding_service
import httpx
import os

logger = logging.getLogger(__name__)

router = APIRouter()

class RecommendationResponse(BaseModel):
    user_id: str
    recommendations: List[Dict]
    total_found: int
    recommendation_type: str

@router.get("/{user_id}", response_model=RecommendationResponse)
async def get_recommendations(
    user_id: str,
    recommendation_type: str = "personalized",
    limit: int = 20,
    api_key: str = Depends(verify_api_key),
    pinecone_service: PineconeService = Depends(get_pinecone_service),
    embedding_service: EmbeddingService = Depends(get_embedding_service)
):
    """Get personalized recommendations for a user based on their interactions"""
    try:
        # Get user's interaction history from Supabase
        supabase_url = os.getenv("SUPABASE_URL")
        supabase_key = os.getenv("SUPABASE_SERVICE_ROLE_KEY")
        
        if not supabase_url or not supabase_key:
            raise HTTPException(status_code=500, detail="Supabase not configured")
        
        headers = {
            "apikey": supabase_key,
            "Authorization": f"Bearer {supabase_key}",
        }
        
        recommendations = []
        story_scores = defaultdict(float)
        processed_story_ids = set()
        
        # 1. Get stories the user has liked
        async with httpx.AsyncClient() as client:
            # Get liked stories
            liked_response = await client.get(
                f"{supabase_url}/rest/v1/story_reactions?user_id=eq.{user_id}&reaction_type=eq.LIKE&select=story_id",
                headers=headers
            )
            liked_stories = liked_response.json() if liked_response.status_code == 200 else []
            
            # Get stories the user has posted
            posted_response = await client.get(
                f"{supabase_url}/rest/v1/stories?user_id=eq.{user_id}&select=id",
                headers=headers
            )
            posted_stories = posted_response.json() if posted_response.status_code == 200 else []
            
            # Get stories the user has commented on
            comments_response = await client.get(
                f"{supabase_url}/rest/v1/comments?user_id=eq.{user_id}&select=story_id",
                headers=headers
            )
            commented_stories = comments_response.json() if comments_response.status_code == 200 else []
            
            # Combine all interaction story IDs with weights
            interaction_weights = []
            
            # Likes have highest weight (1.0)
            for story in liked_stories:
                if story.get('story_id'):
                    interaction_weights.append((story['story_id'], 1.0))
                    processed_story_ids.add(story['story_id'])
            
            # Comments have medium weight (0.7)
            for story in commented_stories:
                if story.get('story_id'):
                    interaction_weights.append((story['story_id'], 0.7))
                    processed_story_ids.add(story['story_id'])
            
            # Posted stories have lower weight for discovery (0.5)
            for story in posted_stories:
                if story.get('id'):
                    interaction_weights.append((story['id'], 0.5))
                    processed_story_ids.add(story['id'])
            
            logger.info(f"User {user_id} has interacted with {len(processed_story_ids)} stories")
            
            # 2. For each interacted story, find similar content in Pinecone
            if interaction_weights and pinecone_service.index is not None:
                for story_id, weight in interaction_weights:
                    # Query Pinecone for stories with this story_id in metadata
                    try:
                        # First, get the embedding for this story
                        query_response = pinecone_service.index.query(
                            vector=[0] * 1024,  # Dummy vector for metadata-only query (1024 dimensions)
                            filter={"story_id": story_id},
                            top_k=1,
                            include_metadata=True,
                            include_values=True,
                            namespace="community-assets"
                        )
                        
                        if query_response.matches:
                            # Use the actual embedding to find similar stories
                            story_embedding = query_response.matches[0].values
                            
                            similar_response = pinecone_service.index.query(
                                vector=story_embedding,
                                filter={
                                    "user_id": {"$ne": user_id}  # Exclude user's own stories
                                },
                                top_k=10,
                                include_metadata=True,
                                namespace="community-assets"
                            )
                            
                            # Aggregate scores for similar stories
                            for match in similar_response.matches:
                                if match.metadata.get('story_id') and match.metadata['story_id'] not in processed_story_ids:
                                    # Score = similarity * interaction_weight
                                    score = match.score * weight
                                    story_scores[match.metadata['story_id']] = max(
                                        story_scores[match.metadata['story_id']], 
                                        score
                                    )
                    except Exception as e:
                        logger.error(f"Error querying similar stories for {story_id}: {e}")
                        continue
            
            # 3. Sort stories by score and fetch details
            sorted_stories = sorted(story_scores.items(), key=lambda x: x[1], reverse=True)[:limit]
            
            if sorted_stories:
                story_ids_str = ','.join([f"'{sid}'" for sid, _ in sorted_stories])
                stories_response = await client.get(
                    f"{supabase_url}/rest/v1/stories?id=in.({story_ids_str})&select=*",
                    headers=headers
                )
                
                if stories_response.status_code == 200:
                    stories_data = stories_response.json()
                    stories_dict = {story['id']: story for story in stories_data}
                    
                    # Build recommendations with scores
                    for story_id, score in sorted_stories:
                        if story_id in stories_dict:
                            story = stories_dict[story_id]
                            story['recommendation_score'] = score
                            story['recommendation_reason'] = 'similar_to_your_interests'
                            recommendations.append(story)
            
            # 4. If not enough recommendations, add trending stories
            if len(recommendations) < limit // 2:
                # Get recent popular stories
                one_day_ago = (datetime.utcnow() - timedelta(days=1)).isoformat()
                trending_response = await client.get(
                    f"{supabase_url}/rest/v1/stories?created_at=gte.{one_day_ago}&select=*&order=likes_count.desc&limit={limit}",
                    headers=headers
                )
                
                if trending_response.status_code == 200:
                    trending_stories = trending_response.json()
                    existing_ids = {r['id'] for r in recommendations}
                    
                    for story in trending_stories:
                        if story['id'] not in existing_ids and story['id'] not in processed_story_ids:
                            story['recommendation_score'] = 0.3  # Lower score for trending
                            story['recommendation_reason'] = 'trending'
                            recommendations.append(story)
                            if len(recommendations) >= limit:
                                break
        
        return RecommendationResponse(
            user_id=user_id,
            recommendations=recommendations[:limit],
            total_found=len(recommendations),
            recommendation_type=recommendation_type
        )
    
    except Exception as e:
        logger.error(f"Recommendation error: {str(e)}")
        raise HTTPException(status_code=500, detail=str(e)) 