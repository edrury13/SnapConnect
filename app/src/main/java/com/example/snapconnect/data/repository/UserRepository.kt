package com.example.snapconnect.data.repository

import com.example.snapconnect.data.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val supabase: SupabaseClient
) {
    
    suspend fun getCurrentUser(): Result<User?> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id
            if (userId != null) {
                val user = supabase.from("users")
                    .select() {
                        filter {
                            eq("id", userId)
                        }
                    }
                    .decodeSingleOrNull<User>()
                Result.success(user)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUser(userId: String): Result<User?> {
        return try {
            val user = supabase.from("users")
                .select() {
                    filter {
                        eq("id", userId)
                    }
                }
                .decodeSingleOrNull<User>()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUsersByIds(userIds: List<String>): Result<List<User>> {
        return try {
            if (userIds.isEmpty()) {
                return Result.success(emptyList())
            }
            
            val users = supabase.from("users")
                .select() {
                    filter {
                        isIn("id", userIds)
                    }
                }
                .decodeList<User>()
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun searchUsers(query: String): Result<List<User>> {
        return try {
            val users = supabase.from("users")
                .select() {
                    filter {
                        ilike("username", "%$query%")
                    }
                    limit(20)
                }
                .decodeList<User>()
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateProfile(
        displayName: String? = null,
        avatarUrl: String? = null
    ): Result<User> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id 
                ?: return Result.failure(Exception("User not authenticated"))
            
            val updates = mutableMapOf<String, Any?>()
            displayName?.let { updates["display_name"] = it }
            avatarUrl?.let { updates["avatar_url"] = it }
            
            val updatedUser = supabase.from("users")
                .update(updates) {
                    filter {
                        eq("id", userId)
                    }
                }
                .decodeSingle<User>()
            
            Result.success(updatedUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 