# -*- coding: utf-8 -*-
"""LangChain service module.
High-level operations combining OpenAI and Pinecone via LangChain.
"""

from typing import List, Dict
import logging

from langchain_community.chat_models import ChatOpenAI
from langchain_community.embeddings import OpenAIEmbeddings
from langchain_community.vectorstores import Pinecone
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
            temperature=0.7,
        )
        self.embeddings = OpenAIEmbeddings(openai_api_key=settings.OPENAI_API_KEY)
        self.vectorstore = None
        self._init_vectorstore()

    # ------------------------------------------------------------------
    # Internal helpers
    # ------------------------------------------------------------------
    def _init_vectorstore(self) -> None:
        """Attach the existing Pinecone index to LangChain."""
        try:
            pinecone.init(api_key=settings.PINECONE_API_KEY, environment=settings.PINECONE_ENVIRONMENT)
            self.vectorstore = Pinecone.from_existing_index(
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