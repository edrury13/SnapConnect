"""Main FastAPI application module."""

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import logging

from app.api.routes import langchain, vision, inspiration, embedding
from app.core.config import settings

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Create FastAPI app
app = FastAPI(
    title="SnapConnect Backend",
    description="Backend API for SnapConnect social media app",
    version="1.0.0"
)

# Configure CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Configure appropriately for production
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Include routers
app.include_router(langchain.router, prefix="/api/v1/langchain", tags=["langchain"])
app.include_router(vision.router, prefix="/api/v1/vision", tags=["vision"])
app.include_router(inspiration.router, prefix="/api/v1/inspiration", tags=["inspiration"])
app.include_router(embedding.router, prefix="/api/v1/embedding", tags=["embedding"])

@app.get("/")
async def root():
    """Root endpoint."""
    return {"message": "SnapConnect API", "version": "1.0.0"}

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {"status": "healthy"}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000) 