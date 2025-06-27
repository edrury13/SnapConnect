# Likes/Dislikes Feature Troubleshooting Guide

## Current Issue
The like/dislike buttons are not showing the user's reaction state in the UI, even though the data is being sent to Supabase.

## Debugging Steps

### 1. Verify Database Migration
First, ensure the `supabase_likes_migration.sql` has been run in your Supabase SQL editor. Check:

```sql
-- Verify the columns exist
SELECT column_name 
FROM information_schema.columns 
WHERE table_name = 'stories' 
AND column_name IN ('likes_count', 'dislikes_count');

-- Verify the story_reactions table exists
SELECT * FROM information_schema.tables 
WHERE table_name = 'story_reactions';

-- Verify the toggle function exists
SELECT proname FROM pg_proc 
WHERE proname = 'toggle_story_reaction';
```

### 2. Check Debug Logs
The code has been updated with debug logging. Run the app and check the logs for:

1. When loading a story:
   - `DEBUG: User {id} has {count} reactions`
   - `DEBUG: Reaction - Story: {id}, Type: {LIKE/DISLIKE}`
   - `DEBUG: Story {id} - Reaction: {type}, Likes: {count}, Dislikes: {count}`
   - `DEBUG: Loaded story {id} with reaction: {type}, likes: {count}, dislikes: {count}`

2. When toggling a reaction:
   - `DEBUG: Toggling reaction - Story: {id}, Type: {LIKE/DISLIKE}`
   - `DEBUG: Toggle result - Action: {created/updated/removed}, New reaction: {type}`
   - `DEBUG: Updated story {id} with reaction: {type}, likes: {count}, dislikes: {count}`

### 3. Verify Data in Supabase
Run these queries in Supabase to check the data:

```sql
-- Check if reactions are being stored
SELECT * FROM story_reactions 
WHERE user_id = 'your-user-id';

-- Check story counts
SELECT id, likes_count, dislikes_count 
FROM stories 
WHERE id = 'story-id';

-- Test the toggle function manually
SELECT toggle_story_reaction('story-id', 'LIKE');
```

### 4. Common Issues & Solutions

#### Issue: Reactions not persisting
- **Cause**: RLS policies might be blocking the operation
- **Solution**: Check that the user is authenticated and the RLS policies allow the operation

#### Issue: Counts not updating
- **Cause**: Trigger might not be working
- **Solution**: Verify the trigger exists and is enabled:
```sql
SELECT tgname FROM pg_trigger 
WHERE tgname = 'update_story_reaction_counts_trigger';
```

#### Issue: UI not updating after reaction
- **Cause**: State might not be updating properly
- **Solution**: Check that `loadUpdatedStory` is being called and the state is being updated

### 5. Testing Steps

1. **Clean Test**:
   - Clear app data/cache
   - Login fresh
   - Navigate to a story
   - Check if existing reactions show
   - Toggle a reaction
   - Check if UI updates

2. **Verify Persistence**:
   - Like a story
   - Navigate away
   - Come back to the same story
   - Verify the like is still shown

3. **Test Toggle Behavior**:
   - Like a story (should highlight like button)
   - Like again (should remove the like)
   - Dislike the story (should highlight dislike button)
   - Like the story (should switch from dislike to like)

### 6. Expected Behavior

- **First visit**: No buttons highlighted
- **After like**: Like button highlighted in primary color, count increased
- **After dislike**: Dislike button highlighted in error color, count increased
- **Toggle same**: Removes the reaction, button returns to normal
- **Toggle different**: Switches reaction, updates both counts

### 7. If Still Not Working

1. Check browser console for any errors
2. Verify Supabase connection is working
3. Check that the Story model has the new fields (likesCount, dislikesCount, userReaction)
4. Ensure the latest code is deployed/built
5. Try with a different user account to rule out user-specific issues

### 8. Clean Build
Sometimes a clean build helps:
```bash
# Android Studio
./gradlew clean
./gradlew build

# Or from Android Studio menu:
Build -> Clean Project
Build -> Rebuild Project
``` 