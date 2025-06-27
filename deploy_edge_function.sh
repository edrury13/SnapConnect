#!/bin/bash

echo "Deploying Story Cleanup Edge Function..."

# Check if supabase CLI is installed
if ! command -v supabase &> /dev/null
then
    echo "Supabase CLI not found. Please install it first:"
    echo "https://supabase.com/docs/guides/cli"
    exit 1
fi

# Check if we're in the project root
if [ ! -f "supabase/functions/cleanup-stories/index.ts" ]; then
    echo "Error: Edge function not found at supabase/functions/cleanup-stories/index.ts"
    echo "Make sure you're running this script from the project root directory"
    exit 1
fi

# Deploy the function
echo "Deploying cleanup-stories function..."
supabase functions deploy cleanup-stories

if [ $? -eq 0 ]; then
    echo "✅ Edge function deployed successfully!"
    echo ""
    echo "Test it with:"
    echo "curl -i --location --request POST \\"
    echo "  'https://ngrbhordabshfvlbqmzu.supabase.co/functions/v1/cleanup-stories' \\"
    echo "  --header 'Authorization: Bearer YOUR_ANON_KEY' \\"
    echo "  --header 'Content-Type: application/json'"
else
    echo "❌ Deployment failed"
    echo "Make sure you've run 'supabase login' and linked your project"
fi 