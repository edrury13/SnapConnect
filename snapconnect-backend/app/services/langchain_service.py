from typing import List, Dict, Any
import logging

from langchain_community.chat_models import ChatOpenAI
from langchain_community.embeddings import OpenAIEmbeddings
from langchain_community.vectorstores import Pinecone as LCPinecone
from langchain.chains import RetrievalQA
from langchain.prompts import PromptTemplate
import pinecone

from app.core.config import settings

logger = logging.getLogger(__name__)


class LangChainService:
    """Service wrapper around LangChain components for RAG-style workflows."""

    def __init__(self) -> None:
        # Initialise heavy clients once per container
        self.llm = ChatOpenAI(
            openai_api_key=settings.OPENAI_API_KEY,
            model_name="gpt-3.5-turbo",
            temperature=0.8,
            max_tokens=50  # Explicit limit for captions
        )
        self.embeddings = OpenAIEmbeddings(
            model="text-embedding-3-large",
            dimensions=1024,
            openai_api_key=settings.OPENAI_API_KEY
        )
        self.vectorstore = None
        self._init_vectorstore()

    # ------------------------------------------------------------------
    # Internal helpers
    # ------------------------------------------------------------------
    def _init_vectorstore(self) -> None:
        """Attach the existing Pinecone index to LangChain."""
        try:
            # pinecone <3 style initialisation
            if getattr(pinecone, "Pinecone", None) is not None:
                pc = pinecone.Pinecone(api_key=settings.PINECONE_API_KEY, environment=settings.PINECONE_ENVIRONMENT)
                index = pc.Index(settings.PINECONE_INDEX_NAME)
                self.vectorstore = LCPinecone(index, self.embeddings, "text")
            else:
                pinecone.init(api_key=settings.PINECONE_API_KEY, environment=getattr(settings, "PINECONE_ENVIRONMENT", None))
                self.vectorstore = LCPinecone.from_existing_index(
                    index_name=settings.PINECONE_INDEX_NAME,
                    embedding=self.embeddings,
                )
            logger.info("LangChain vectorstore initialised")
        except Exception as exc:
            logger.error(f"Failed to initialise vectorstore: {exc}")

    # ------------------------------------------------------------------
    # Public operations
    # ------------------------------------------------------------------
    async def generate_feedback(self, description: str, content_type: str = "art") -> str:
        """Generate constructive feedback for a creative work."""
        similar = self.vectorstore.similarity_search(
            description, k=3, filter={"content_type": content_type}
        )
        context = "\n".join(doc.page_content for doc in similar)

        template = (
            """
            You are an expert creative consultant providing feedback to artists, filmmakers, and musicians.

            Based on similar successful works:
            {context}

            Provide constructive feedback for this {content_type} work:
            {description}

            Focus on:
            1. Technical execution
            2. Creative direction
            3. Potential improvements
            4. What works well

            Keep the feedback encouraging and actionable.
            """
        )
        prompt = PromptTemplate(template=template, input_variables=["context", "content_type", "description"])
        return self.llm.predict(prompt.format(context=context, content_type=content_type, description=description))

    async def find_collaborators(self, user_profile: Dict, project_needs: str):
        """Find potential collaborators that complement a user's skills."""
        qa_chain = RetrievalQA.from_chain_type(
            llm=self.llm,
            chain_type="stuff",
            retriever=self.vectorstore.as_retriever(search_kwargs={"k": 10, "filter": {"collaboration_open": True}}),
        )
        query = (
            f"Find artists who match these requirements:\n"
            f"Project needs: {project_needs}\n"
            f"Complementary to skills: {user_profile.get('skills', [])}"
        )
        return qa_chain.run(query)

    async def generate_project_summary(self, updates: List[str]) -> str:
        """Summarise project progress from multiple updates."""
        template = (
            """
            Summarise the creative project progress based on these updates:
            {updates}

            Include:
            - Key milestones achieved
            - Creative evolution
            - Technical challenges overcome
            - Next steps suggested

            Keep it concise but comprehensive.
            """
        )
        prompt = PromptTemplate(template=template, input_variables=["updates"])
        updates_text = "\n---\n".join(updates)
        return self.llm.predict(prompt.format(updates=updates_text))

    async def generate_caption(self, tags: str, media_type: str = "image") -> str:
        """Generate concise caption adapted to media type."""
        if media_type == "image":
            # Use art-specific prompt for images
            prompt = (
                "You are a creative assistant. Given the style tags, "
                "generate a concise, vivid art caption in 20 words or fewer. "
                "Avoid hashtags or metadata. "
                f"Style tags: {tags.replace('_', ' ')}."
            )
        else:
            # Keep generic prompt for videos
            prompt = (
                "You are a creative assistant. "
                f"Write a concise, vivid {media_type} description in 20 words or fewer. "
                "Avoid hashtags or metadata.\n"
                f"Tags: {tags.replace('_', ' ')}"
            )
        return self.llm.predict(prompt)

    async def style_from_text(self, text: str) -> str:
        """Infer dominant creative style from caption using LLM. Falls back to vectorstore if available."""
        if self.vectorstore is None:
            logger.warning("Vectorstore not initialised; returning 'unknown' for style")
            return "unknown"

        try:
            # Get Pinecone index directly to query with namespace
            if hasattr(self.vectorstore, '_index'):
                index = self.vectorstore._index
            else:
                pc = pinecone.Pinecone(api_key=settings.PINECONE_API_KEY)
                index = pc.Index(settings.PINECONE_INDEX_NAME)
            
            # Generate embedding for the text
            query_embedding = self.embeddings.embed_query(text)
            
            # Query directly with namespace support
            results = index.query(
                vector=query_embedding,
                top_k=1,
                namespace="style-taxonomy",
                include_metadata=True
            )
            
            if results.matches:
                return results.matches[0].metadata.get("style_name", "unknown")
        except Exception as exc:
            logger.warning("style_from_text vector search failed: %s", exc, exc_info=True)
        return "unknown"
    
    async def detect_style_with_confidence(self, vision_analysis: Dict[str, Any]) -> Dict[str, Any]:
        """Detect style from vision analysis and return with confidence score."""
        if self.vectorstore is None:
            logger.warning("Vectorstore not initialised; returning unknown style")
            return {"style_name": "unknown", "confidence": 0.0, "technique": "unknown"}
        
        try:
            # Create a rich description from vision analysis
            artistic_style = vision_analysis.get('artistic_style', '').strip()
            technique = vision_analysis.get('technique', '').strip()
            characteristics = vision_analysis.get('style_characteristics', [])
            
            # Build description parts
            parts = []
            
            # Include artistic style only if it's specific (not 'none' or 'unknown')
            if artistic_style and artistic_style not in ['unknown', 'none', 'unidentified']:
                parts.append(artistic_style)
            
            # Always include technique if available (this helps with style matching)
            if technique and technique not in ['unknown', 'unidentified']:
                parts.append(f"{technique} technique")
            
            # Include visual characteristics (these are important for style matching)
            if characteristics:
                parts.append(f"characterized by {', '.join(characteristics[:3])}")
            
            # If we have very little info, try to use other elements
            if not parts or (len(parts) == 1 and 'technique' in parts[0]):
                # Add colors if available
                colors = vision_analysis.get('colors', [])
                if colors:
                    parts.append(f"with {colors[0]} palette")
                    
                # Add mood if available
                mood = vision_analysis.get('mood', '')
                if mood and mood != 'neutral':
                    parts.append(f"{mood} mood")
            
            style_description = " ".join(parts) if parts else "general artistic work"
            logger.info(f"Generated style description: {style_description}")
            
            # Get Pinecone index directly to query with namespace
            if hasattr(self.vectorstore, '_index'):
                index = self.vectorstore._index
            else:
                pc = pinecone.Pinecone(api_key=settings.PINECONE_API_KEY)
                index = pc.Index(settings.PINECONE_INDEX_NAME)
            
            # Generate embedding for the style description
            query_embedding = self.embeddings.embed_query(style_description)
            
            # Query directly with namespace support
            results = index.query(
                vector=query_embedding,
                top_k=5,  # Get top 5 matches to see distribution
                namespace="style-taxonomy",
                include_metadata=True
            )
            
            if results.matches:
                # Get best match
                best_match = results.matches[0]
                style_name = best_match.metadata.get("style_name", "unknown")
                confidence = float(best_match.score)  # Cosine similarity is already 0-1
                
                # Log all matches for debugging
                logger.info(f"Style matches for '{style_description[:50]}...':")
                for match in results.matches[:3]:
                    logger.info(f"  - {match.metadata.get('style_name', 'unknown')}: {match.score:.3f}")
                
                return {
                    "style_name": style_name,
                    "confidence": confidence,
                    "technique": vision_analysis.get("technique", "unknown"),
                    "description": style_description
                }
            else:
                logger.warning(f"No style matches found for: {style_description}")
            
        except Exception as exc:
            logger.error(f"Style detection failed: {exc}")
        
        return {
            "style_name": "unknown",
            "confidence": 0.0,
            "technique": vision_analysis.get("technique", "unknown")
        }
    
    async def check_style_exists(self, style_name: str, threshold: float = 0.9) -> bool:
        """Check if a style already exists in the taxonomy with high similarity."""
        if self.vectorstore is None:
            return False
            
        try:
            matches = self.vectorstore.similarity_search_with_score(
                style_name,
                k=1,
                namespace="style-taxonomy"
            )
            
            if matches:
                _, score = matches[0]
                return float(score) > threshold
                
        except Exception as exc:
            logger.error(f"Style existence check failed: {exc}")
            
        return False
    
    async def add_style_to_taxonomy(self, style_info: Dict[str, Any]) -> bool:
        """Add a new style to the style-taxonomy namespace."""
        try:
            # Generate unique ID for the style
            style_id = f"style-{style_info['style_name'].lower().replace(' ', '_')}"
            
            # Create rich description for embedding
            description = (
                f"{style_info['style_name']} style characterized by "
                f"{', '.join(style_info.get('style_characteristics', []))}. "
                f"Technique: {style_info.get('technique', 'various')}. "
                f"{style_info.get('description', '')}"
            )
            
            # Generate embedding
            embedding = self.embeddings.embed_query(description)
            
            # Create metadata
            metadata = {
                "style_name": style_info['style_name'],
                "technique": style_info.get('technique', 'unknown'),
                "description": description,
                "text": description,  # LangChain expects this field
                "auto_detected": True,
                "characteristics": style_info.get('style_characteristics', []),
                "first_seen": style_info.get('story_id', 'unknown')
            }
            
            # Get Pinecone index directly
            if hasattr(self.vectorstore, '_index'):
                # For newer LangChain versions
                index = self.vectorstore._index
            else:
                # For older versions, recreate index connection
                pc = pinecone.Pinecone(api_key=settings.PINECONE_API_KEY)
                index = pc.Index(settings.PINECONE_INDEX_NAME)
            
            # Upsert to Pinecone
            index.upsert(
                vectors=[(style_id, embedding, metadata)],
                namespace="style-taxonomy"
            )
            
            logger.info(f"Added new style to taxonomy: {style_info['style_name']}")
            return True
            
        except Exception as exc:
            logger.error(f"Failed to add style to taxonomy: {exc}")
            return False
    
    async def generate_style_aware_caption(self, vision_analysis: Dict[str, Any], style_info: Dict[str, Any], media_type: str = "image") -> str:
        """Generate factual caption based on vision analysis and style detection."""
        
        # Extract key information
        style_name = style_info.get('style_name', 'unknown')
        technique = style_info.get('technique', 'unknown')
        subjects = vision_analysis.get('subjects', [])
        colors = vision_analysis.get('colors', [])
        mood = vision_analysis.get('mood', '')
        composition = vision_analysis.get('composition', '')
        
        # Build factual description components
        components = []
        
        # Main subjects (most important)
        if subjects:
            subjects_str = ", ".join(subjects[:3])  # Limit to top 3 subjects
            components.append(subjects_str)
        
        # Style and technique
        if style_name and style_name not in ['unknown', 'none']:
            components.append(f"in {style_name} style")
        elif technique and technique not in ['unknown', 'unidentified', 'none']:
            components.append(f"using {technique}")
        
        # Key visual elements
        if colors and len(colors) > 0:
            color_str = colors[0] if isinstance(colors[0], str) else "vibrant colors"
            components.append(f"with {color_str}")
        
        # Simple, factual prompt - similar to what worked in generate_captions.py
        description_parts = " ".join(components) if components else "image"
        
        prompt = (
            f"Given this image analysis: {description_parts}. "
            f"Generate a concise, factual description in 20 words or fewer. "
            f"Focus on what is literally visible. Avoid metaphors or artistic interpretation. "
            f"Be specific about objects, colors, and techniques."
        )
        
        # Create a new LLM instance with lower temperature for factual descriptions
        factual_llm = ChatOpenAI(
            openai_api_key=settings.OPENAI_API_KEY,
            model_name="gpt-3.5-turbo",
            temperature=0.3,  # Low temperature for factual consistency
            max_tokens=50
        )
        
        caption = factual_llm.predict(prompt)
        return caption 