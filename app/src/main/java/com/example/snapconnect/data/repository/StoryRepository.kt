package com.example.snapconnect.data.repository

import com.example.snapconnect.data.model.MediaType
import com.example.snapconnect.data.model.Story
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
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlin.time.Duration.Companion.hours
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepository @Inject constructor(
    private val supabase: SupabaseClient
) {
    
    suspend fun createStory(
        mediaUrl: String,
        mediaType: MediaType,
        caption: String? = null
    ): Result<Story> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            val insertData = buildJsonObject {
                put("user_id", userId)
                put("media_url", mediaUrl)
                put("media_type", mediaType.name)
                caption?.let { put("caption", it) }
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
                Result.success(stories.first())
            } else {
                // If we can't fetch it back, create a dummy story object
                Result.success(
                    Story(
                        id = "",  // Will be empty, but upload was successful
                        userId = userId,
                        mediaUrl = mediaUrl,
                        mediaType = mediaType,
                        caption = caption,
                        viewerIds = emptyList(),
                        createdAt = Clock.System.now(),
                        expiresAt = Clock.System.now() + 24.hours
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
                .select(
                    columns = Columns.list(
                        "id",
                        "user_id",
                        "media_url",
                        "media_type",
                        "caption",
                        "viewer_ids",
                        "created_at",
                        "expires_at"
                    )
                ) {
                    // This will be filtered by RLS policies
                    order(column = "created_at", order = Order.DESCENDING)
                }
                .decodeList<Story>()
            
            Result.success(stories)
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
} 