# Debug Steps for Likes/Dislikes Persistence Issue

## Issue Summary
The like/dislike button highlights briefly then disappears. This suggests the optimistic update works, but the reaction state is lost when the real data is fetched.

## Current Flow
1. User clicks reaction button
2. Optimistic update applies immediately (button highlights)
3. API call is made to toggle the reaction
4. On success, we update with the reaction result
5. We then fetch the full story data (with delay)
6. The fetched data might not include the user's reaction

## Debug Steps

### 1. Check Console Logs
Look for these debug messages in order:
```
DEBUG: Toggling reaction - Story: {id}, Type: LIKE/DISLIKE
DEBUG: Toggle result - Action: {action}, New reaction: {type}
DEBUG: Updated story {id} with reaction: {type}, likes: {count}, dislikes: {count}
```

### 2. Verify in Supabase
After clicking a reaction, immediately check:
```sql
-- Check if reaction was saved
SELECT * FROM story_reactions 
WHERE story_id = 'your-story-id' 
AND user_id = 'your-user-id';

-- Check the counts
SELECT id, likes_count, dislikes_count 
FROM stories 
WHERE id = 'your-story-id';
```

### 3. Test Scenarios

#### Scenario A: Fresh Reaction
1. Find a story with no reaction
2. Click Like
3. Should see:
   - Button highlights immediately
   - Count increases by 1
   - Button stays highlighted

#### Scenario B: Toggle Same Reaction
1. On a liked story
2. Click Like again
3. Should see:
   - Button unhighlights
   - Count decreases by 1
   - Button stays unhighlighted

#### Scenario C: Switch Reaction
1. On a liked story
2. Click Dislike
3. Should see:
   - Like unhighlights, count -1
   - Dislike highlights, count +1
   - Dislike stays highlighted

### 4. Potential Issues

#### Issue 1: Reaction Not Loading
The `getStoryWithReaction` might not be loading the user's reaction properly.

**Fix**: Ensure the story_reactions query includes the current user's reaction.

#### Issue 2: Timing Issue
The database trigger might not have updated counts when we fetch.

**Current Fix**: Added 500ms delay before fetching updated story.

#### Issue 3: State Override
The fetched story might have `userReaction: null` even though a reaction exists.

**Debug**: Check what `getStoryWithReaction` returns vs what's in the database.

### 5. Quick Test
To verify the reaction system works:
1. Like a story
2. Refresh the app completely
3. Go back to the same story
4. Check if the like is still highlighted

If it's not highlighted after refresh, the issue is in the initial data loading, not the toggle mechanism. 