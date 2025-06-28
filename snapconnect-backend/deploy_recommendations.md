# Deploying the Recommendation System

## Environment Variables Required

Add these to your backend deployment (e.g., Render, Heroku, etc.):

```bash
# Existing variables
SUPABASE_URL=your_supabase_url
SUPABASE_SERVICE_ROLE_KEY=your_supabase_service_role_key
OPENAI_API_KEY=your_openai_api_key

# Pinecone configuration
PINECONE_API_KEY=your_pinecone_api_key
PINECONE_INDEX_NAME=snapconnect  # Your existing index name

# Embedding model configuration
TEXT_EMBEDDING_MODEL=text-embedding-3-large  # This produces 1024 dimensions (custom)
```

## Using Existing Pinecone Index

Your existing Pinecone index "snapconnect" is already configured with:
- **Dimensions**: 1024 (matching text-embedding-3-large with custom dimensions)
- **Metric**: cosine
- **Namespaces**: 
  - `community-assets` - For story embeddings
  - `style-taxonomy` - For style classifications

The recommendation system will use the `community-assets` namespace to find similar stories.

## Backend Deployment Steps

1. Push the updated backend code to your repository
2. The backend will automatically redeploy if you have auto-deploy enabled
3. Add the new environment variables in your hosting platform
4. Restart the backend service

## Testing the Recommendation System

1. Create some stories with the app
2. Like, comment on, or post stories
3. The recommendation system will start working after you have some interactions
4. Check the home feed - recommended stories should appear at the top

## Frontend Configuration

The backend URL is already configured in `StoryRepository.kt`:
- `val baseUrl = "https://snapconnect-backend.onrender.com"`

## Troubleshooting

If recommendations aren't showing:
1. Check backend logs for any errors
2. Verify Pinecone connection is working (check for "Connected to Pinecone index: snapconnect" in logs)
3. Ensure users have some interactions (likes, posts, comments)
4. Check that stories have embeddings in Pinecone's `community-assets` namespace
5. Verify the embedding model is producing 1024-dimensional vectors

## How It Works

1. When a user likes, posts, or comments on a story, it's tracked in Supabase
2. The recommendation engine queries Pinecone to find stories with similar embeddings
3. Stories are searched in the `community-assets` namespace using 1024-dimensional vectors
4. Results are ranked by similarity score weighted by interaction type:
   - Likes: 1.0 weight
   - Comments: 0.7 weight  
   - Posted stories: 0.5 weight
5. Recommended stories appear at the top of the feed with special styling
6. Stories without AI captions or style tags appear at the bottom 