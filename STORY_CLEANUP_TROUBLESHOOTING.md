# Story Cleanup Troubleshooting Guide

## Problem
Stories older than 24 hours are not being automatically deleted from Supabase.

## Root Causes
1. **No automatic scheduling**: The cleanup function only runs when triggered by the app
2. **Limited trigger points**: Only runs when users open specific screens
3. **pg_cron not enabled**: The best solution (pg_cron) might not be available on your Supabase plan

## Current Implementation (Edge Function with RPC Fallback)

The app now calls a Supabase Edge Function every time it opens to clean up expired stories. If the Edge Function isn't deployed (404 error), it automatically falls back to the RPC method.

### Quick Deployment

**For Windows (PowerShell):**
```powershell
.\deploy_edge_function.ps1
```

**For Mac/Linux:**
```bash
chmod +x deploy_edge_function.sh
./deploy_edge_function.sh
```

### Manual Deployment

1. **Install Supabase CLI**: https://supabase.com/docs/guides/cli
2. **Login**: `supabase login`
3. **Initialize** (if needed): `supabase init`
4. **Link your project**: `supabase link --project-ref ngrbhordabshfvlbqmzu`
5. **Deploy**: `supabase functions deploy cleanup-stories`

### Test the Edge Function
```bash
curl -i --location --request POST \
  'https://ngrbhordabshfvlbqmzu.supabase.co/functions/v1/cleanup-stories' \
  --header 'Authorization: Bearer YOUR_ANON_KEY' \
  --header 'Content-Type: application/json'
```

### Monitor in Android Logs
Open Android Studio Logcat and filter by "MainActivity" to see:
```
// If Edge Function is deployed:
Triggering story cleanup on app start...
Story cleanup successful via Edge Function: {"success":true,"message":"Cleaned up 3 expired stories",...}

// If Edge Function NOT deployed (fallback to RPC):
Triggering story cleanup on app start...
Edge function not found, falling back to RPC method
Story cleanup triggered via RPC
```

## Common Issues

### 404 Error
If you see:
```
Story cleanup failed with status: 404
```

This means the Edge Function isn't deployed yet. The app will automatically fall back to the RPC method, which should work. To fix the 404:
1. Deploy the Edge Function using the scripts above
2. The next time the app opens, it will use the Edge Function

### Edge Function vs RPC Method
- **Edge Function**: More efficient, runs independently, better for production
- **RPC Fallback**: Uses the existing SQL functions, works without deployment

## Troubleshooting Steps

### 1. Check if Stories are Actually Expired
Run this SQL query in Supabase SQL Editor:
```sql
-- Check expired stories
SELECT id, user_id, created_at, expires_at, 
       (expires_at < NOW()) as is_expired
FROM public.stories
ORDER BY expires_at DESC;

-- Count expired stories
SELECT COUNT(*) as expired_count
FROM public.stories
WHERE expires_at < NOW();
```

### 2. Test Manual Cleanup
Run this in Supabase SQL Editor:
```sql
-- Manually trigger cleanup
SELECT public.delete_expired_stories();

-- Check if stories were deleted
SELECT COUNT(*) FROM public.stories WHERE expires_at < NOW();
```

### 3. Check Your Supabase Plan
pg_cron is only available on Pro and Team plans. Check your plan at:
https://app.supabase.com/project/_/settings/billing

## Solutions (In Order of Preference)

### Solution 1: Enable pg_cron (Pro/Team Plans Only)
Run the SQL script in `enable_pg_cron.sql`

### Solution 2: Use GitHub Actions
Create `.github/workflows/cleanup-stories.yml`:
```yaml
name: Cleanup Expired Stories
on:
  schedule:
    - cron: '*/30 * * * *'  # Every 30 minutes
  workflow_dispatch:  # Allow manual trigger

jobs:
  cleanup:
    runs-on: ubuntu-latest
    steps:
      - name: Trigger Story Cleanup
        run: |
          curl -X POST ${{ secrets.SUPABASE_URL }}/rest/v1/rpc/trigger_story_cleanup \
            -H "apikey: ${{ secrets.SUPABASE_ANON_KEY }}" \
            -H "Authorization: Bearer ${{ secrets.SUPABASE_ANON_KEY }}" \
            -H "Content-Type: application/json"
```

### Solution 3: External Cron Service
Use a free service like cron-job.org to call your Edge Function every 30 minutes.

### Solution 4: App-Based Cleanup (Already Implemented)
The app now triggers cleanup at:
- App startup (MainActivity)
- Home screen load
- Story viewing

## Verification
After implementing a solution, verify it's working:
```sql
-- Insert a test story with past expiration
INSERT INTO public.stories (user_id, media_url, media_type, expires_at)
VALUES (
  (SELECT id FROM auth.users LIMIT 1),
  'https://example.com/test.jpg',
  'IMAGE',
  NOW() - INTERVAL '1 hour'
);

-- Wait for your cleanup to run, then check
SELECT * FROM public.stories WHERE media_url = 'https://example.com/test.jpg';
```

## Additional Debugging
Enable logging in your cleanup function:
```sql
CREATE OR REPLACE FUNCTION public.delete_expired_stories()
RETURNS void AS $$
DECLARE
    deleted_count INTEGER;
BEGIN
    -- Delete and count
    WITH deleted AS (
        DELETE FROM public.stories 
        WHERE expires_at < NOW()
        RETURNING *
    )
    SELECT COUNT(*) INTO deleted_count FROM deleted;
    
    -- Log the deletion (optional - creates a log table)
    INSERT INTO public.cleanup_logs (deleted_count, run_at)
    VALUES (deleted_count, NOW());
END;
$$ LANGUAGE plpgsql;

-- Create log table
CREATE TABLE IF NOT EXISTS public.cleanup_logs (
    id SERIAL PRIMARY KEY,
    deleted_count INTEGER,
    run_at TIMESTAMPTZ DEFAULT NOW()
);
``` 