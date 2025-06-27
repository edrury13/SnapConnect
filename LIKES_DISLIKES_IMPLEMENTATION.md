# Likes/Dislikes Feature Implementation Guide

## Overview
This guide explains how to implement the like/dislike feature for stories in SnapConnect. The feature allows users to react to stories with likes or dislikes, and tracks these reactions both per story and per user.

## Database Changes

### 1. Run the Migration SQL
Execute the `supabase_likes_migration.sql` file in your Supabase SQL editor:

```sql
-- This will:
-- 1. Add likes_count and dislikes_count columns to stories table
-- 2. Create story_reactions table to track user reactions
-- 3. Set up automatic triggers to maintain accurate counts
-- 4. Create a function to toggle reactions (like/dislike/remove)
-- 5. Enable realtime updates for reactions
```

### 2. Database Schema
The implementation adds:
- `stories.likes_count` - Total number of likes
- `stories.dislikes_count` - Total number of dislikes  
- `story_reactions` table - Tracks which users liked/disliked which stories

## Code Implementation

### 1. Models
The `Story` data class has been updated with:
```kotlin
@SerialName("likes_count")
val likesCount: Int = 0,

@SerialName("dislikes_count") 
val dislikesCount: Int = 0,

@SerialName("user_reaction")
val userReaction: ReactionType? = null
```

New enum and data class added:
```kotlin
enum class ReactionType {
    LIKE, DISLIKE
}

data class StoryReaction(
    val id: String,
    val storyId: String,
    val userId: String,
    val reactionType: ReactionType,
    val createdAt: Instant
)
```

### 2. Repository Methods
`StoryRepository` has been enhanced with:
- `toggleReaction(storyId, reactionType)` - Toggle like/dislike
- `getUserReactions()` - Get all reactions by current user
- `getStoryWithReaction(storyId)` - Get story with user's reaction
- Updated `getFriendsStories()` to include user reactions via JOIN

### 3. ViewModel Updates
`StoryViewViewModel` now includes:
- `toggleReaction(reactionType)` - Handle reaction toggling
- `loadUpdatedStory(storyId)` - Refresh story after reaction
- `isProcessingReaction` state to prevent double-clicks

### 4. UI Components
The `StoryViewScreen` now shows:
- Like button with count (thumb up icon)
- Dislike button with count (thumb down icon)
- Visual feedback when user has liked/disliked
- Disabled state while processing reactions

## Usage

### For Users
1. Tap the thumbs up icon to like a story
2. Tap the thumbs down icon to dislike a story
3. Tap again to remove your reaction
4. Can only have one reaction per story (like OR dislike)

### For Developers
To get stories with reactions:
```kotlin
val stories = storyRepository.getFriendsStories()
// Each story will have likesCount, dislikesCount, and userReaction
```

To toggle a reaction:
```kotlin
storyRepository.toggleReaction(storyId, ReactionType.LIKE)
```

## Features

### Automatic Count Updates
Database triggers automatically maintain accurate like/dislike counts when reactions are added, changed, or removed.

### Real-time Updates
Reactions support Supabase realtime, so counts update live across all connected clients.

### Security
- Users can only manage their own reactions
- Users can only react to stories they can view (friends' stories)
- All operations are protected by Row Level Security

## Testing

1. Create a story
2. Have another user like/dislike it
3. Verify counts update correctly
4. Toggle reactions and verify counts
5. Check that only one reaction per user is allowed
6. Verify real-time updates work across devices

## Future Enhancements

Consider adding:
- Reaction animations
- Notification when someone likes your story  
- Analytics on most liked/disliked stories
- Reaction history/timeline
- Additional reaction types (love, wow, etc.) 