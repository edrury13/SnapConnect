package com.example.snapconnect.data.repository

import com.example.snapconnect.data.model.Comment
import com.example.snapconnect.data.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentsRepository @Inject constructor(
    private val supabase: SupabaseClient
) {
    
    suspend fun getComments(storyId: String): Result<List<Comment>> {
        return try {
            val comments = supabase.from("comments")
                .select() {
                    filter {
                        eq("story_id", storyId)
                    }
                    order(column = "created_at", order = Order.ASCENDING)
                }
                .decodeList<Comment>()
            
            Result.success(comments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCommentsWithUsers(storyId: String): Result<List<Pair<Comment, User>>> {
        return try {
            // Get comments
            val comments = supabase.from("comments")
                .select() {
                    filter {
                        eq("story_id", storyId)
                    }
                    order(column = "created_at", order = Order.ASCENDING)
                }
                .decodeList<Comment>()
            
            // Get unique user IDs
            val userIds = comments.map { it.userId }.distinct()
            
            // Fetch users
            val users = if (userIds.isNotEmpty()) {
                userIds.mapNotNull { userId ->
                    supabase.from("users")
                        .select() {
                            filter {
                                eq("id", userId)
                            }
                        }
                        .decodeList<User>()
                        .firstOrNull()
                }
            } else {
                emptyList()
            }
            
            // Map comments to users
            val commentsWithUsers = comments.map { comment ->
                val user = users.find { it.id == comment.userId }
                    ?: User(
                        id = comment.userId,
                        email = "",
                        username = "Unknown",
                        displayName = null,
                        avatarUrl = null,
                        createdAt = comment.createdAt
                    )
                comment to user
            }
            
            Result.success(commentsWithUsers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addComment(storyId: String, text: String): Result<Comment> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            val commentData = buildJsonObject {
                put("story_id", storyId)
                put("user_id", userId)
                put("text", text)
            }
            
            val comment = supabase.from("comments")
                .insert(commentData) {
                    select()
                }
                .decodeSingle<Comment>()
            
            Result.success(comment)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteComment(commentId: String): Result<Unit> {
        return try {
            supabase.from("comments")
                .delete {
                    filter {
                        eq("id", commentId)
                    }
                }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getCommentsRealtime(storyId: String): Flow<List<Pair<Comment, User>>> {
        return flow {
            // Initial load
            val initialComments = getCommentsWithUsers(storyId)
            if (initialComments.isSuccess) {
                emit(initialComments.getOrDefault(emptyList()))
            }
            
            // Create and subscribe to channel
            val channel = supabase.realtime.channel("comments_$storyId")
            
            try {
                // Set up the postgres change listener
                val changeFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public") {
                    table = "comments"
                    filter = "story_id=eq.$storyId"
                }
                
                // Subscribe to the channel
                channel.subscribe()
                
                // Listen for changes
                changeFlow.collect { change ->
                    // Refresh comments when any change occurs
                    val result = getCommentsWithUsers(storyId)
                    if (result.isSuccess) {
                        emit(result.getOrDefault(emptyList()))
                    }
                }
            } finally {
                // Unsubscribe from channel when flow is cancelled
                channel.unsubscribe()
                supabase.realtime.removeChannel(channel)
            }
        }
    }
} 