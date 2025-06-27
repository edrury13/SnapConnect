"""Vision service for analyzing images and extracting tags."""

import logging
from typing import List, Dict, Any
import json
from openai import OpenAI
from app.core.config import settings

logger = logging.getLogger(__name__)


class VisionService:
    """Service for analyzing images using OpenAI Vision API."""
    
    def __init__(self):
        self.client = OpenAI(api_key=settings.OPENAI_API_KEY)
    
    async def analyze_image(self, image_url: str) -> List[str]:
        """Extract descriptive tags from an image using GPT-4 Vision."""
        try:
            response = self.client.chat.completions.create(
                model="gpt-4-vision-preview",
                messages=[
                    {
                        "role": "system",
                        "content": "You are an image analysis assistant. Analyze the image and return only a comma-separated list of descriptive tags (objects, scene, mood, colors, style). No explanations, just tags."
                    },
                    {
                        "role": "user",
                        "content": [
                            {
                                "type": "text",
                                "text": "Extract descriptive tags from this image:"
                            },
                            {
                                "type": "image_url",
                                "image_url": {
                                    "url": image_url,
                                    "detail": "low"  # Use low detail for faster/cheaper analysis
                                }
                            }
                        ]
                    }
                ],
                max_tokens=150,
                temperature=0.5
            )
            
            # Parse comma-separated tags
            tags_text = response.choices[0].message.content.strip()
            tags = [tag.strip().lower().replace(' ', '_') for tag in tags_text.split(',')]
            
            # Filter out empty tags and limit to reasonable number
            tags = [tag for tag in tags if tag][:10]
            
            logger.info(f"Extracted tags from image: {tags}")
            return tags
            
        except Exception as e:
            logger.error(f"Vision analysis failed: {e}")
            # Return generic tags as fallback
            return ["image", "photo"]
    
    async def analyze_image_detailed(self, image_url: str) -> Dict[str, Any]:
        """Perform detailed analysis of an image for style and technique detection."""
        try:
            prompt = """
            Analyze this image factually and provide a JSON response with the following structure:
            {
                "artistic_style": "art style if identifiable, otherwise descriptive style (e.g., impressionism, minimalist, realistic, abstract)",
                "technique": "actual medium/technique visible (e.g., oil_painting, digital_art, photography, pencil_drawing, watercolor)",
                "subjects": ["list specific objects/subjects visible, in order of prominence"],
                "composition": "factual description of layout (e.g., 'centered subject', 'rule of thirds', 'symmetrical')",
                "colors": ["actual dominant colors (e.g., 'deep blue', 'warm orange', 'monochrome')"],
                "mood": "observable atmosphere (e.g., 'calm', 'energetic', 'dramatic', 'neutral')",
                "style_characteristics": ["specific visual elements present (e.g., 'thick brushstrokes', 'sharp lines', 'soft focus')"],
                "technical_details": ["observable techniques (e.g., 'visible brush texture', 'digital layers', 'high contrast')"],
                "influences": ["only if clearly evident (e.g., 'Warhol-inspired', 'anime-influenced') or empty array"]
            }
            
            IMPORTANT:
            - Be literal and factual, not interpretive
            - List what you actually see, not what it might represent
            - For subjects, be specific (e.g., "red apple on wooden table" not "fruit still life")
            - For artistic_style: use descriptive terms if no specific movement is clear (e.g., "realistic", "abstract", "minimalist")
            - Avoid using "none" - instead describe the visual style you observe
            Return ONLY valid JSON.
            """
            
            response = self.client.chat.completions.create(
                model="gpt-4-vision-preview",
                messages=[
                    {
                        "role": "system",
                        "content": "You are an expert art historian and visual analyst. Provide detailed, accurate analysis of images."
                    },
                    {
                        "role": "user",
                        "content": [
                            {
                                "type": "text",
                                "text": prompt
                            },
                            {
                                "type": "image_url",
                                "image_url": {
                                    "url": image_url,
                                    "detail": "high"  # Use high detail for better style analysis
                                }
                            }
                        ]
                    }
                ],
                max_tokens=500,  # Increased for detailed analysis
                temperature=0.1   # Very low temperature for factual, consistent analysis
            )
            
            # Parse JSON response
            try:
                analysis = json.loads(response.choices[0].message.content.strip())
                logger.info(f"Detailed analysis completed: style={analysis.get('artistic_style')}, technique={analysis.get('technique')}")
                return analysis
            except json.JSONDecodeError:
                logger.error("Failed to parse vision analysis as JSON")
                # Return structured fallback
                return {
                    "artistic_style": "general",
                    "technique": "unidentified",
                    "subjects": ["unidentified content"],
                    "composition": "standard layout",
                    "colors": ["various colors"],
                    "mood": "neutral",
                    "style_characteristics": [],
                    "technical_details": [],
                    "influences": []
                }
                
        except Exception as e:
            logger.error(f"Detailed vision analysis failed: {e}")
            # Return structured fallback
            return {
                "artistic_style": "general",
                "technique": "unidentified",
                "subjects": ["unidentified content"],
                "composition": "standard layout",
                "colors": ["various colors"],
                "mood": "neutral",
                "style_characteristics": [],
                "technical_details": [],
                "influences": []
            } 