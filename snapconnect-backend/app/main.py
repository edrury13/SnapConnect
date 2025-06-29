from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from contextlib import asynccontextmanager
import os
import sys
from dotenv import load_dotenv

# Debug information
print(f"Python path: {sys.path}")
print(f"Current working directory: {os.getcwd()}")
print(f"__file__ location: {__file__}")

# Import routes - with better error handling
try:
    from app.api.routes import embed, search, recommend, inspiration
    try:
        from app.api.routes import vision  # optional
    except ImportError:
        vision = None
    try:
        from app.api.routes import langchain  # optional
    except ImportError:
        langchain = None
    print("Successfully imported routes")
except ImportError as e:
    print(f"Import error: {e}")
    # Try alternate import path
    try:
        from api.routes import embed, search, recommend, inspiration
        try:
            from api.routes import vision as vision
        except ImportError:
            vision = None
        try:
            from api.routes import langchain as langchain
        except ImportError:
            langchain = None
        print("Successfully imported routes using alternate path")
    except ImportError as e2:
        print(f"Alternate import also failed: {e2}")
        raise

from app.services.pinecone_service import PineconeService
from app.services.embedding_service import EmbeddingService
from app.core.config import settings
from app.dependencies import get_pinecone_service, get_embedding_service

load_dotenv()

# Initialize services through dependency injection
# These will be created on first use
embedding_service = get_embedding_service()

# Lifecycle management
@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup
    print("Initializing services...")
    try:
        # Initialize Pinecone service (creates instance if needed)
        pinecone_service = get_pinecone_service()
        if pinecone_service.index is None:
            print("Warning: Pinecone index not available")
        else:
            print("Pinecone service initialized successfully")
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

# Inspiration (moodboard & style analysis)
app.include_router(inspiration.router, prefix="/api/v1/inspiration", tags=["inspiration"])

# Optionally mount vision / langchain if present
if vision is not None:
    app.include_router(vision.router, prefix="/api/v1/vision", tags=["vision"])
if langchain is not None:
    app.include_router(langchain.router, prefix="/api/v1/langchain", tags=["langchain"])

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
    pinecone_service = get_pinecone_service()
    return {
        "status": "operational",
        "version": "1.0.0",
        "environment": os.getenv("ENVIRONMENT", "development"),
        "services": {
            "openai": bool(settings.OPENAI_API_KEY) and hasattr(embedding_service, 'client') and embedding_service.client is not None,
            "pinecone": pinecone_service.index is not None
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