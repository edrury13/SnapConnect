from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from contextlib import asynccontextmanager
import os
from dotenv import load_dotenv

from app.api.routes import embed, search, recommend
from app.services.pinecone_service import PineconeService
from app.services.embedding_service import EmbeddingService
from app.core.config import settings

load_dotenv()

# Initialize services
embedding_service = EmbeddingService()

# Lifecycle management
@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup
    print("Initializing services...")
    try:
        PineconeService.initialize()
    except Exception as e:
        print(f"Warning: Pinecone initialization failed: {e}")
        print("Running without Pinecone support")
    yield
    # Shutdown
    print("Shutting down...")

app = FastAPI(
    title="SnapConnect RAG API",
    version="1.0.0",
    lifespan=lifespan
)

# CORS configuration
app.add_middleware(
    CORSMiddleware,
    allow_origins=settings.ALLOWED_ORIGINS,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Include routers
app.include_router(embed.router, prefix="/api/v1/embed", tags=["embeddings"])
app.include_router(search.router, prefix="/api/v1/search", tags=["search"])
app.include_router(recommend.router, prefix="/api/v1/recommend", tags=["recommendations"])

# Note: LangChain routes are disabled until langchain is added back
# from app.api.routes import langchain
# app.include_router(langchain.router, prefix="/api/v1/langchain", tags=["langchain"])

@app.get("/")
async def root():
    return {"message": "SnapConnect RAG API", "status": "healthy"}

@app.get("/health")
async def health_check():
    return {
        "status": "healthy",
        "environment": os.getenv("ENVIRONMENT", "development")
    }

@app.get("/status")
async def status():
    """Detailed status endpoint for debugging"""
    return {
        "status": "operational",
        "version": "1.0.0",
        "environment": os.getenv("ENVIRONMENT", "development"),
        "services": {
            "openai": bool(settings.OPENAI_API_KEY) and hasattr(embedding_service, 'client') and embedding_service.client is not None,
            "pinecone": PineconeService.index is not None
        },
        "endpoints": [
            "/health",
            "/status", 
            "/docs",
            "/api/v1/embed/text",
            "/api/v1/search",
            "/api/v1/recommend/{user_id}"
        ]
    } 