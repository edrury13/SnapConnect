# SnapConnect RAG Backend

A FastAPI-based backend service for handling RAG (Retrieval-Augmented Generation) operations for the SnapConnect app.

## Features

- Text and image embeddings using OpenAI
- Vector storage and retrieval with Pinecone (single index, two namespaces)
- Content-based search and recommendations
- Inspiration Engine (Mood-board generation & Style analysis)
- API key authentication
- Ready for deployment on Render

## Local Development

### Prerequisites

- Python 3.11+
- OpenAI API key
- Pinecone account and API key

### Setup (Local)

1. Create a virtual environment:
```bash
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

2. Install dependencies (CPU-only):
```bash
pip install -r requirements.txt
# optional dev extras
pip install python-dotenv httpx requests
```

3. Copy `.env.example` to `.env` and fill in your credentials:
```bash
cp .env.example .env
```

4. Run the development server:
```bash
uvicorn app.main:app --reload
```

The API will be available at `http://localhost:8000`

## API Documentation

Once running, visit:
- Interactive docs: `http://localhost:8000/docs`
- ReDoc: `http://localhost:8000/redoc`

## Deployment on Render

1. Push this code to a GitHub repository
2. Sign up at [render.com](https://render.com)
3. Create a new Web Service and connect your repo
4. Render will automatically detect the `render.yaml` file
5. Add your environment variables in the Render dashboard:
   - `OPENAI_API_KEY`
   - `PINECONE_API_KEY`
   - `PINECONE_ENVIRONMENT`
   - `PINECONE_INDEX_NAME` (default: `snapconnect`)
   - `API_KEY` (random string your mobile app will send in `X-API-Key`)
6. Deploy!

## API Endpoints (v1)

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/embed/text` | Embed and upsert text content |
| POST | `/api/v1/inspiration/moodboard` | Generate mood-board (text prompt → reference items) |
| POST | `/api/v1/inspiration/style-analysis` | Analyse style from caption text |
| POST | `/api/v1/search` | Generic semantic search |
| GET  | `/api/v1/recommend/{user_id}` | Collaborative recommendations |

All protected routes require `X-API-Key`.

## Testing

### Quick-seed helper

The repo contains `scripts/seed_pinecone.py` that populates the
`community-assets` and `style-taxonomy` namespaces with example vectors
so you can see non-empty responses immediately.

```bash
# Health check
curl http://localhost:8000/health

# Test embedding (with API key)
curl -X POST http://localhost:8000/api/v1/embed/text \
  -H "Content-Type: application/json" \
  -H "X-API-Key: your-api-key" \
  -d '{
    "content": "Digital art exploring surrealism",
    "user_id": "user123",
    "tags": ["digital", "surrealism"]
  }'

# Mood-board (local)
curl -X POST http://localhost:8000/api/v1/inspiration/moodboard \
  -H "Content-Type: application/json" \
  -H "X-API-Key: your-api-key" \
  -d '{"description":"futuristic neon city poster","limit":8}'

# Style analysis (caption only)
curl -X POST http://localhost:8000/api/v1/inspiration/style-analysis \
  -H "Content-Type: application/json" \
  -H "X-API-Key: your-api-key" \
  -d '{"description":"impressionist painting of water lilies","limit":5}'
``` 