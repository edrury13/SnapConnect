# WARNING: This duplicate file is kept in sync with `app/services/embedding_service.py`.
# Consider consolidating duplicated modules in the future.

from typing import List, Optional, Union
import hashlib
import io
import logging

from app.core.config import settings

logger = logging.getLogger(__name__)

# ---------------------------------------------
# Dependency gates
# ---------------------------------------------
try:
    from openai import OpenAI

    OPENAI_AVAILABLE = True
except ImportError:
    OPENAI_AVAILABLE = False
    logger.warning("OpenAI not available - running in limited mode")

try:
    import torch
    from PIL import Image
    from transformers import CLIPModel, CLIPProcessor

    CLIP_AVAILABLE = True
except ImportError:
    CLIP_AVAILABLE = False
    logger.warning("Transformers / CLIP not available - image embeddings will be dummy")


class EmbeddingService:
    """Generates embeddings for both text and image inputs."""

    def __init__(self):
        # OpenAI client for text
        if OPENAI_AVAILABLE and settings.OPENAI_API_KEY:
            self.client = OpenAI(api_key=settings.OPENAI_API_KEY)
        else:
            self.client = None

        # CLIP model for image
        if CLIP_AVAILABLE:
            try:
                logger.info("Loading CLIP model for image embeddings …")
                self.clip_model = CLIPModel.from_pretrained(settings.IMAGE_EMBEDDING_MODEL)
                self.clip_processor = CLIPProcessor.from_pretrained(settings.IMAGE_EMBEDDING_MODEL)
                self.clip_model.eval()
            except Exception as exc:
                logger.error(f"Failed to load CLIP model: {exc}")
                self.clip_model = None
                self.clip_processor = None
        else:
            self.clip_model = None
            self.clip_processor = None

    # ------------------------------------------------------------------
    # Text Embeddings
    # ------------------------------------------------------------------
    async def generate_text_embedding(self, text: str) -> Optional[List[float]]:
        """Generate an embedding for a given string."""
        if not self.client:
            logger.warning("OpenAI not available, returning dummy text embedding")
            return [0.0] * 1536

        try:
            # Optional: deterministic hash for caching key
            text_hash = hashlib.md5(text.encode()).hexdigest()

            response = self.client.embeddings.create(
                model=settings.EMBEDDING_MODEL,
                input=text,
                dimensions=settings.IMAGE_EMBEDDING_DIM,
            )
            embedding = response.data[0].embedding
            return embedding
        except Exception as exc:
            logger.error(f"Failed to generate text embedding: {exc}")
            return None

    # ------------------------------------------------------------------
    # Image Embeddings
    # ------------------------------------------------------------------
    async def generate_image_embedding(self, image: Union[bytes, "Image.Image", str]) -> Optional[List[float]]:
        """Generate an embedding for an image (bytes, PIL Image, or file path)."""
        # Prefer local CLIP if available
        if self.clip_model and self.clip_processor:
            try:
                if isinstance(image, bytes):
                    image = Image.open(io.BytesIO(image)).convert("RGB")
                elif isinstance(image, str):
                    image = Image.open(image).convert("RGB")

                inputs = self.clip_processor(images=image, return_tensors="pt")
                with torch.no_grad():
                    output = self.clip_model.get_image_features(**inputs)
                return output[0].cpu().tolist()
            except Exception as exc:
                logger.error(f"Local CLIP embedding failed, falling back to OpenAI: {exc}")

        # Fallback to OpenAI hosted image embeddings
        if self.client:
            try:
                import base64, mimetypes

                if isinstance(image, bytes):
                    img_b64 = base64.b64encode(image).decode()
                    mime = "image/png"
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

        logger.warning("No image embedding backend available, returning dummy image embedding")
        return [0.0] * settings.IMAGE_EMBEDDING_DIM

    # ------------------------------------------------------------------
    # Metadata Helper
    # ------------------------------------------------------------------
    def prepare_metadata(self, content_type: str, user_id: str, **kwargs) -> dict:
        """Prepare metadata dict for Pinecone upsert."""
        metadata = {
            "content_type": content_type,
            "user_id": user_id,
            "timestamp": kwargs.get("timestamp", ""),
            "tags": kwargs.get("tags", []),
            "collaboration_open": kwargs.get("collaboration_open", True),
        }

        if content_type == "image":
            metadata.update(
                {
                    "tools_used": kwargs.get("tools_used", []),
                    "style": kwargs.get("style", ""),
                    "medium": kwargs.get("medium", "digital"),
                }
            )
        elif content_type == "audio":
            metadata.update(
                {
                    "genre": kwargs.get("genre", ""),
                    "instruments": kwargs.get("instruments", []),
                    "duration": kwargs.get("duration", 0),
                }
            )

        return metadata 