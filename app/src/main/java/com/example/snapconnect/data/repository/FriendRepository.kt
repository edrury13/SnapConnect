package com.example.snapconnect.data.repository

import com.example.snapconnect.data.model.Friend
import com.example.snapconnect.data.model.FriendStatus
import com.example.snapconnect.data.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendRepository @Inject constructor(
    private val supabase: SupabaseClient
) {
    
    suspend fun sendFriendRequest(friendUsername: String): Result<Unit> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            // First, find the user by username
            val users = supabase.from("users")
                .select() {
                    filter {
                        eq("username", friendUsername)
                    }
                }
                .decodeList<User>()
            
            if (users.isEmpty()) {
                return Result.failure(Exception("User not found"))
            }
            
            val friendId = users.first().id
            
            // Check if we're trying to add ourselves
            if (friendId == userId) {
                return Result.failure(Exception("You cannot add yourself as a friend"))
            }
            
            // Check if friendship already exists
            val existingFriendships = supabase.from("friends")
                .select() {
                    filter {
                        or {
                            and {
                                eq("user_id", userId)
                                eq("friend_id", friendId)
                            }
                            and {
                                eq("user_id", friendId)
                                eq("friend_id", userId)
                            }
                        }
                    }
                }
                .decodeList<Friend>()
            
            if (existingFriendships.isNotEmpty()) {
                return Result.failure(Exception("Friend request already exists"))
            }
            
            // Send friend request
            supabase.from("friends")
                .insert(
                    buildJsonObject {
                        put("user_id", userId)
                        put("friend_id", friendId)
                        put("status", FriendStatus.PENDING.name)
                    }
                )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun acceptFriendRequest(friendshipId: String): Result<Unit> {
        return try {
            supabase.from("friends")
                .update(
                    buildJsonObject {
                        put("status", FriendStatus.ACCEPTED.name)
                    }
                ) {
                    filter {
                        eq("id", friendshipId)
                    }
                }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun rejectFriendRequest(friendshipId: String): Result<Unit> {
        return try {
            supabase.from("friends")
                .delete {
                    filter {
                        eq("id", friendshipId)
                    }
                }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun blockUser(userId: String): Result<Unit> {
        return try {
            val currentUserId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            // Update existing friendship or create new blocked entry
            supabase.from("friends")
                .upsert(
                    buildJsonObject {
                        put("user_id", currentUserId)
                        put("friend_id", userId)
                        put("status", FriendStatus.BLOCKED.name)
                    }
                )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun removeFriend(friendshipId: String): Result<Unit> {
        return try {
            supabase.from("friends")
                .delete {
                    filter {
                        eq("id", friendshipId)
                    }
                }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getMyFriends(): Result<List<Pair<Friend, User>>> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            // Get accepted friendships
            val friendships = supabase.from("friends")
                .select() {
                    filter {
                        and {
                            or {
                                eq("user_id", userId)
                                eq("friend_id", userId)
                            }
                            eq("status", FriendStatus.ACCEPTED.name)
                        }
                    }
                }
                .decodeList<Friend>()
            
            // Get user details for each friend
            val friendsWithUsers = friendships.mapNotNull { friendship ->
                val friendId = if (friendship.userId == userId) friendship.friendId else friendship.userId
                
                val userResult = supabase.from("users")
                    .select() {
                        filter {
                            eq("id", friendId)
                        }
                    }
                    .decodeList<User>()
                
                userResult.firstOrNull()?.let { user ->
                    friendship to user
                }
            }
            
            Result.success(friendsWithUsers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPendingRequests(): Result<List<Pair<Friend, User>>> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            // Get pending requests where current user is the recipient
            val pendingRequests = supabase.from("friends")
                .select() {
                    filter {
                        and {
                            eq("friend_id", userId)
                            eq("status", FriendStatus.PENDING.name)
                        }
                    }
                }
                .decodeList<Friend>()
            
            // Get user details for each requester
            val requestsWithUsers = pendingRequests.mapNotNull { request ->
                val userResult = supabase.from("users")
                    .select() {
                        filter {
                            eq("id", request.userId)
                        }
                    }
                    .decodeList<User>()
                
                userResult.firstOrNull()?.let { user ->
                    request to user
                }
            }
            
            Result.success(requestsWithUsers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getSentRequests(): Result<List<Pair<Friend, User>>> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            // Get pending requests sent by current user
            val sentRequests = supabase.from("friends")
                .select() {
                    filter {
                        and {
                            eq("user_id", userId)
                            eq("status", FriendStatus.PENDING.name)
                        }
                    }
                }
                .decodeList<Friend>()
            
            // Get user details for each recipient
            val requestsWithUsers = sentRequests.mapNotNull { request ->
                val userResult = supabase.from("users")
                    .select() {
                        filter {
                            eq("id", request.friendId)
                        }
                    }
                    .decodeList<User>()
                
                userResult.firstOrNull()?.let { user ->
                    request to user
                }
            }
            
            Result.success(requestsWithUsers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun searchUsers(query: String): Result<List<User>> {
        return try {
            val currentUserId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            val users = supabase.from("users")
                .select() {
                    filter {
                        and {
                            neq("id", currentUserId)
                            or {
                                ilike("username", "%$query%")
                                ilike("display_name", "%$query%")
                            }
                        }
                    }
                    limit(20)
                }
                .decodeList<User>()
            
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 