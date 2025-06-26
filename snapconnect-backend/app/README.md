# SnapConnect RAG Backend

A FastAPI-based backend service for handling RAG (Retrieval-Augmented Generation) operations for the SnapConnect app.

## Features

- Text and image embeddings using OpenAI
- Vector storage and retrieval with Pinecone
- Content-based search and recommendations
- API key authentication
- Ready for deployment on Render

## Local Development

### Prerequisites

- Python 3.11+
- OpenAI API key
- Pinecone account and API key

### Setup

1. Create a virtual environment:
```bash
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

2. Install dependencies:
```bash
pip install -r requirements.txt
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
6. Deploy!

## API Endpoints

- `POST /api/v1/embed/text` - Embed text content
- `POST /api/v1/embed/image` - Embed image content
- `POST /api/v1/search` - Search for similar content
- `GET /api/v1/recommend/{user_id}` - Get recommendations

All endpoints require an `X-API-Key` header for authentication.

## Testing

```bash
# Test health endpoint
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
``` 