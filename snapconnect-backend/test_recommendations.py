#!/usr/bin/env python3
"""Test the recommendation system functionality"""

import httpx
import asyncio
import json
from datetime import datetime

async def test_recommendations():
    """Test the recommendation endpoint"""
    base_url = "https://snapconnect-backend.onrender.com"
    api_key = "RT5PrU6c8dnk5UUaP1dyDIqQjY6KuV2IXXKZvKvkMiz8N2AwrZhHJwYm8GZlCjQWPqaAB9UAMqwYkzPx67DQnx6muHfXscKpmmJVVVp9FWoyWIudwx35Qrv5H3TqwCnm"
    
    # Test user ID (you can replace with an actual user ID)
    test_user_id = "fbeba978-c758-43e9-a725-7d130c0f3530"
    
    print(f"Testing recommendation system for user: {test_user_id}")
    print("=" * 60)
    
    async with httpx.AsyncClient() as client:
        try:
            # Test the recommendation endpoint
            response = await client.get(
                f"{base_url}/api/v1/recommend/{test_user_id}",
                headers={"X-API-Key": api_key},
                params={"limit": 5}
            )
            
            print(f"Status Code: {response.status_code}")
            
            if response.status_code == 200:
                data = response.json()
                print(f"\nRecommendation Type: {data.get('recommendation_type')}")
                print(f"Total Recommendations Found: {data.get('total_found')}")
                print(f"\nRecommendations:")
                
                for i, rec in enumerate(data.get('recommendations', []), 1):
                    print(f"\n{i}. Story ID: {rec.get('id')}")
                    print(f"   User: {rec.get('user_id')}")
                    print(f"   Caption: {rec.get('caption', 'No caption')}")
                    print(f"   AI Caption: {rec.get('ai_caption', 'No AI caption')}")
                    print(f"   Style Tags: {rec.get('style_tags', [])}")
                    print(f"   Score: {rec.get('recommendation_score', 0):.3f}")
                    print(f"   Reason: {rec.get('recommendation_reason', 'Unknown')}")
                    print(f"   Likes: {rec.get('likes_count', 0)}, Dislikes: {rec.get('dislikes_count', 0)}")
                    print(f"   Created: {rec.get('created_at', 'Unknown')}")
                
                # Summary
                print("\n" + "=" * 60)
                print("RECOMMENDATION ANALYSIS:")
                
                # Count by reason
                reasons = {}
                for rec in data.get('recommendations', []):
                    reason = rec.get('recommendation_reason', 'unknown')
                    reasons[reason] = reasons.get(reason, 0) + 1
                
                print("\nRecommendation Reasons:")
                for reason, count in reasons.items():
                    print(f"  - {reason}: {count}")
                
                # Count by style
                styles = {}
                for rec in data.get('recommendations', []):
                    for style in rec.get('style_tags', []):
                        styles[style] = styles.get(style, 0) + 1
                
                if styles:
                    print("\nStyle Distribution:")
                    for style, count in sorted(styles.items(), key=lambda x: x[1], reverse=True):
                        print(f"  - {style}: {count}")
                
                # Score analysis
                scores = [rec.get('recommendation_score', 0) for rec in data.get('recommendations', [])]
                if scores:
                    print(f"\nScore Analysis:")
                    print(f"  - Highest Score: {max(scores):.3f}")
                    print(f"  - Lowest Score: {min(scores):.3f}")
                    print(f"  - Average Score: {sum(scores) / len(scores):.3f}")
                
            else:
                print(f"Error: {response.text}")
                
        except Exception as e:
            print(f"Error testing recommendations: {e}")
    
    print("\n" + "=" * 60)
    print("Test complete!")

if __name__ == "__main__":
    asyncio.run(test_recommendations()) 