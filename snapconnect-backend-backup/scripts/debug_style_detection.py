#!/usr/bin/env python3
"""Debug style detection to understand why everything returns 'none'."""

import os
import asyncio
from dotenv import load_dotenv

# Add parent directory to path
import sys
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from app.services.langchain_service import LangChainService
from app.services.vision_service import VisionService

load_dotenv()

async def test_style_detection():
    # Initialize services
    vision_service = VisionService()
    langchain_service = LangChainService()
    
    # Test cases with different types of content
    test_images = [
        {
            "name": "Test 1: Clear artistic style",
            "analysis": {
                "artistic_style": "impressionism",
                "technique": "oil_painting",
                "subjects": ["water lilies", "pond"],
                "colors": ["soft blues", "greens"],
                "mood": "peaceful",
                "style_characteristics": ["soft brushstrokes", "light play", "atmospheric"]
            }
        },
        {
            "name": "Test 2: Photography (no art movement)",
            "analysis": {
                "artistic_style": "realistic",
                "technique": "photography",
                "subjects": ["person", "street scene"],
                "colors": ["natural tones"],
                "mood": "candid",
                "style_characteristics": ["sharp focus", "natural lighting"]
            }
        },
        {
            "name": "Test 3: Generic/unclear style",
            "analysis": {
                "artistic_style": "general",
                "technique": "digital_art",
                "subjects": ["abstract shapes"],
                "colors": ["mixed colors"],
                "mood": "neutral",
                "style_characteristics": []
            }
        }
    ]
    
    print("STYLE DETECTION DEBUGGING\n" + "="*50)
    
    for test in test_images:
        print(f"\n{test['name']}")
        print(f"Input analysis: {test['analysis']['artistic_style']} / {test['analysis']['technique']}")
        
        # Test style detection
        style_info = await langchain_service.detect_style_with_confidence(test['analysis'])
        
        print(f"Style description generated: {style_info.get('description', 'N/A')}")
        print(f"Detected style: {style_info['style_name']}")
        print(f"Confidence: {style_info['confidence']:.3f}")
        print(f"Technique: {style_info.get('technique', 'N/A')}")
        
        # Check what happens with caption generation
        caption = await langchain_service.generate_style_aware_caption(
            test['analysis'],
            style_info,
            "image"
        )
        print(f"Generated caption: {caption}")
    
    print("\n" + "="*50)
    print("Style detection should now work better with:")
    print("- More descriptive style terms instead of 'none'")
    print("- Better handling of technique-based matching")
    print("- Inclusion of colors and mood when style is generic")

if __name__ == "__main__":
    asyncio.run(test_style_detection()) 