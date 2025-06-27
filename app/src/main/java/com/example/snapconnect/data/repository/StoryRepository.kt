package com.example.snapconnect.data.repository

import com.example.snapconnect.data.model.MediaType
import com.example.snapconnect.data.model.Story
import com.example.snapconnect.data.model.ReactionType
import com.example.snapconnect.data.model.StoryReaction
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.rpc
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlin.time.Duration.Companion.hours
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import com.example.snapconnect.data.repository.EmbeddingRepository
import com.example.snapconnect.data.repository.LangchainRepository
import com.example.snapconnect.data.repository.VisionRepository

@Singleton
class StoryRepository @Inject constructor(
    private val supabase: SupabaseClient,
    private val embeddingRepo: EmbeddingRepository,
    private val langchainRepo: LangchainRepository,
    private val visionRepo: VisionRepository,
) {
    
    suspend fun createStory(
        mediaUrl: String,
        mediaType: MediaType,
        caption: String? = null,
        isPublic: Boolean = true
    ): Result<Story> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            val insertData = buildJsonObject {
                put("user_id", userId)
                put("media_url", mediaUrl)
                put("media_type", mediaType.name)
                caption?.let { put("caption", it) }
                put("is_public", isPublic)
            }
            
            // Insert without immediately decoding the response
            supabase.from("stories")
                .insert(insertData)
            
            // Fetch the created story separately to avoid parsing issues
            val stories = supabase.from("stories")
                .select() {
                    filter {
                        eq("user_id", userId)
                        eq("media_url", mediaUrl)
                    }
                    order(column = "created_at", order = Order.DESCENDING)
                    limit(1)
                }
                .decodeList<Story>()
            
            if (stories.isNotEmpty()) {
                val story = stories.first()
                
                // Analyze image for tags if it's an image
                val tags = if (mediaType == MediaType.IMAGE) {
                    visionRepo.analyzeImage(mediaUrl).getOrDefault(listOf("image"))
                } else {
                    listOf("video")
                }
                
                // Always process with vision-based tags
                try {
                    val response = 
                        langchainRepo.processPost(
                            userId = userId,
                            storyId = story.id,
                            caption = caption ?: "",
                            tags = tags,
                            imageUrl = mediaUrl
                        )
                    // Log the response for debugging
                    println("ProcessPost response - AI Caption: ${response.ai_caption}, Style: ${response.style}")
                } catch (e: Exception) {
                    // Log the error but don't fail story creation
                    e.printStackTrace()
                }
                Result.success(story)
            } else {
                // No story returned; skip embedding because ID is unknown
                // If we can't fetch it back, create a dummy story object
                Result.success(
                    Story(
                        id = "",  // Will be empty, but upload was successful
                        userId = userId,
                        mediaUrl = mediaUrl,
                        mediaType = mediaType,
                        caption = caption,
                        viewerIds = emptyList(),
                        styleTags = emptyList(),
                        aiCaption = null,
                        createdAt = Clock.System.now(),
                        expiresAt = Clock.System.now() + 24.hours,
                        isPublic = isPublic,
                        likesCount = 0,
                        dislikesCount = 0,
                        userReaction = null
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getFriendsStories(): Result<List<Story>> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            // Get stories from friends and self
            val stories = supabase.from("stories")
                .select() {
                    // This will be filtered by RLS policies
                    order(column = "created_at", order = Order.DESCENDING)
                }
                .decodeList<Story>()
            
            // Get user's reactions separately
            val userReactions = supabase.from("story_reactions")
                .select() {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<StoryReaction>()
            
            // Debug log
            println("DEBUG: User $userId has ${userReactions.size} reactions")
            userReactions.forEach { reaction ->
                println("DEBUG: Reaction - Story: ${reaction.storyId}, Type: ${reaction.reactionType}")
            }
            
            // Create a map of story ID to reaction type
            val reactionMap = userReactions.associateBy(
                { it.storyId }, 
                { it.reactionType }
            )
            
            // Map stories with user reactions
            val storiesWithReactions = stories.map { story ->
                val withReaction = story.copy(userReaction = reactionMap[story.id])
                println("DEBUG: Story ${story.id} - Reaction: ${withReaction.userReaction}, Likes: ${withReaction.likesCount}, Dislikes: ${withReaction.dislikesCount}")
                withReaction
            }
            
            Result.success(storiesWithReactions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getMyStories(): Result<List<Story>> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id
                ?: return Result.failure(Exception("User not authenticated"))
            
            val stories = supabase.from("stories")
                .select() {
                    filter {
                        eq("user_id", userId)
                        gt("expires_at", Clock.System.now().toString())
                    }
                    order("created_at", Order.DESCENDING)
                }
                .decodeList<Story>()
            
            Result.success(stories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getMyStoriesCount(): Result<Int> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id
                ?: return Result.failure(Exception("User not authenticated"))
            
            val stories = supabase.from("stories")
                .select(columns = Columns.list("id")) {
                    filter {
                        eq("user_id", userId)
                        gt("expires_at", Clock.System.now().toString())
                    }
                }
                .decodeList<JsonObject>()
            
            Result.success(stories.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun markStoryAsViewed(storyId: String): Result<Unit> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            // Get current story
            val story = supabase.from("stories")
                .select() {
                    filter {
                        eq("id", storyId)
                    }
                }
                .decodeSingle<Story>()
            
            // Add viewer if not already viewed
            if (!story.viewerIds.contains(userId)) {
                val updatedViewerIds = story.viewerIds + userId
                
                supabase.from("stories")
                    .update(
                        buildJsonObject {
                            putJsonArray("viewer_ids") {
                                updatedViewerIds.forEach { add(JsonPrimitive(it)) }
                            }
                        }
                    ) {
                        filter {
                            eq("id", storyId)
                        }
                    }
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteStory(storyId: String): Result<Unit> {
        return try {
            supabase.from("stories")
                .delete {
                    filter {
                        eq("id", storyId)
                    }
                }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getStoriesRealtime(): Flow<List<Story>> {
        val channel = supabase.realtime.channel("stories_changes")
        
        return channel
            .postgresChangeFlow<PostgresAction>(schema = "public") {
                table = "stories"
            }
            .map {
                // Whenever there's a change, fetch fresh data
                getFriendsStories().getOrNull() ?: emptyList()
            }
    }
    
    suspend fun triggerStoryCleanup() {
        try {
            // Call the cleanup function we created in SQL
            supabase.postgrest.rpc("trigger_story_cleanup")
        } catch (e: Exception) {
            // Ignore errors, this is just a cleanup trigger
        }
    }
    
    suspend fun getStoriesByStyle(styleTag: String): Result<List<Story>> {
        return try {
            val stories = supabase.from("stories")
                .select() {
                    filter {
                        contains("style_tags", listOf(styleTag))
                        gt("expires_at", Clock.System.now().toString())
                    }
                    order("created_at", Order.DESCENDING)
                }
                .decodeList<Story>()
            
            Result.success(stories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun toggleReaction(storyId: String, reactionType: ReactionType): Result<ReactionToggleResult> {
        return try {
            println("DEBUG: Toggling reaction - Story: $storyId, Type: $reactionType")
            
            val result = supabase.postgrest
                .rpc(
                    function = "toggle_story_reaction",
                    parameters = buildJsonObject {
                        put("p_story_id", storyId)
                        put("p_reaction_type", reactionType.name)
                    }
                )
                .decodeSingle<JsonObject>()
            
            val action = result["action"]?.toString()?.trim('"') ?: "unknown"
            val newReactionType = result["reaction_type"]?.toString()?.trim('"')?.let { 
                if (it == "null") null else ReactionType.valueOf(it)
            }
            
            println("DEBUG: Toggle result - Action: $action, New reaction: $newReactionType")
            
            Result.success(ReactionToggleResult(action, newReactionType))
        } catch (e: Exception) {
            println("DEBUG: Toggle reaction failed - ${e.message}")
            Result.failure(e)
        }
    }
    
    suspend fun getUserReactions(): Result<List<StoryReaction>> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id
                ?: return Result.failure(Exception("User not authenticated"))
            
            val reactions = supabase.from("story_reactions")
                .select() {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<StoryReaction>()
            
            Result.success(reactions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getStoryWithReaction(storyId: String): Result<Story> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id
                ?: return Result.failure(Exception("User not authenticated"))
            
            // Get the story
            val story = supabase.from("stories")
                .select() {
                    filter {
                        eq("id", storyId)
                    }
                }
                .decodeSingle<Story>()
            
            // Get user's reaction for this story
            val userReaction = supabase.from("story_reactions")
                .select() {
                    filter {
                        eq("story_id", storyId)
                        eq("user_id", userId)
                    }
                }
                .decodeList<StoryReaction>()
                .firstOrNull()
            
            // Return story with user reaction
            val storyWithReaction = story.copy(
                userReaction = userReaction?.reactionType
            )
            
            Result.success(storyWithReaction)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

data class ReactionToggleResult(
    val action: String, // "created", "updated", or "removed"
    val reactionType: ReactionType?
) 