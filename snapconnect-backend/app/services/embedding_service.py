from typing import List, Optional, Union
import hashlib
import io
from app.core.config import settings
import logging

logger = logging.getLogger(__name__)

try:
    from openai import OpenAI
    OPENAI_AVAILABLE = True
except ImportError:
    OPENAI_AVAILABLE = False
    logger.warning("OpenAI not available - running in limited mode")

# Optional local image embedding with CLIP
try:
    import torch
    from PIL import Image
    from transformers import CLIPModel, CLIPProcessor

    CLIP_AVAILABLE = True
except ImportError:
    CLIP_AVAILABLE = False
    logger.warning("Transformers/CLIP not available - image embeddings will be dummy")

class EmbeddingService:
    def __init__(self):
        """Initializes the embedding back-ends lazily."""
        # OpenAI text embedding client
        if OPENAI_AVAILABLE and settings.OPENAI_API_KEY:
            logger.info("Initializing OpenAI client for text embeddings …")
            self.client = OpenAI(api_key=settings.OPENAI_API_KEY)
        else:
            self.client = None

        # Local CLIP model for image embeddings (optional)
        if CLIP_AVAILABLE:
            try:
                logger.info("Loading CLIP model for image embeddings … this may take a few seconds …")
                self.clip_model = CLIPModel.from_pretrained(settings.IMAGE_EMBEDDING_MODEL)
                self.clip_processor = CLIPProcessor.from_pretrained(settings.IMAGE_EMBEDDING_MODEL)
                self.clip_model.eval()
                # Ensure on CPU to avoid issues in cheap deployments
            except Exception as exc:
                logger.error(f"Failed to load CLIP model: {exc}")
                self.clip_model = None
                self.clip_processor = None
        else:
            self.clip_model = None
            self.clip_processor = None
    
    async def generate_text_embedding(self, text: str) -> Optional[List[float]]:
        """Generate embedding for text content"""
        if not self.client:
            logger.warning("OpenAI not available, returning dummy embedding")
            return [0.0] * 1536  # OpenAI ada-002 dimension
            
        try:
            response = self.client.embeddings.create(
                model=settings.EMBEDDING_MODEL,
                input=text,
                dimensions=settings.IMAGE_EMBEDDING_DIM,
            )
            embedding = response.data[0].embedding
            return embedding
        except Exception as e:
            logger.error(f"Failed to generate embedding: {e}")
            return None
    
    async def generate_image_embedding(self, image: Union[bytes, "Image.Image", str]) -> Optional[List[float]]:
        """Generate embedding for an image using CLIP. Accepts bytes, PIL Image, or path."""
        # Prefer local CLIP if available
        if self.clip_model and self.clip_processor:
            try:
                # Convert input to PIL Image
                if isinstance(image, bytes):
                    image = Image.open(io.BytesIO(image)).convert("RGB")
                elif isinstance(image, str):
                    image = Image.open(image).convert("RGB")

                inputs = self.clip_processor(images=image, return_tensors="pt")
                with torch.no_grad():
                    embeddings = self.clip_model.get_image_features(**inputs)
                return embeddings[0].cpu().tolist()
            except Exception as exc:
                logger.error(f"Local CLIP embedding failed, falling back to OpenAI: {exc}")

        # Fallback: use OpenAI hosted image embeddings
        if self.client:
            try:
                import base64, mimetypes

                if isinstance(image, bytes):
                    img_b64 = base64.b64encode(image).decode()
                    mime = "image/png"  # default
                elif isinstance(image, str):
                    with open(image, "rb") as f:
                        data = f.read()
                    img_b64 = base64.b64encode(data).decode()
                    mime, _ = mimetypes.guess_type(image)
                    mime = mime or "image/png"
                else:
                    logger.error("Unsupported image input type for OpenAI embedding")
                    return None

                response = self.client.embeddings.create(
                    model=settings.IMAGE_EMBEDDING_MODEL,
                    input=[{"image": img_b64, "mime_type": mime}],
                    dimension=settings.IMAGE_EMBEDDING_DIM,
                )
                return response.data[0].embedding
            except Exception as exc:
                logger.error(f"OpenAI image embedding failed: {exc}")
                return None

        logger.warning("No image embedding backend available, returning dummy vector")
        return [0.0] * settings.IMAGE_EMBEDDING_DIM
    
    def prepare_metadata(self, content_type: str, user_id: str, **kwargs) -> dict:
        """Prepare metadata for storage"""
        metadata = {
            "content_type": content_type,
            "user_id": user_id,
            "timestamp": kwargs.get("timestamp", ""),
            "tags": kwargs.get("tags", []),
            "collaboration_open": kwargs.get("collaboration_open", True)
        }
        
        # Add type-specific metadata
        if content_type == "image":
            metadata.update({
                "tools_used": kwargs.get("tools_used", []),
                "style": kwargs.get("style", ""),
                "medium": kwargs.get("medium", "digital")
            })
        elif content_type == "audio":
            metadata.update({
                "genre": kwargs.get("genre", ""),
                "instruments": kwargs.get("instruments", []),
                "duration": kwargs.get("duration", 0)
            })
        
        return metadata 