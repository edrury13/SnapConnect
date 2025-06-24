package com.example.snapconnect.data.repository

import com.example.snapconnect.data.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
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
    
    suspend fun updateProfile(displayName: String?, username: String?): Result<Unit> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id
                ?: return Result.failure(Exception("User not authenticated"))
            
            val updateData = buildJsonObject {
                displayName?.let { put("display_name", it) }
                username?.let { put("username", it) }
            }
            
            supabase.from("users")
                .update(updateData) {
                    filter {
                        eq("id", userId)
                    }
                }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateAvatar(avatarUrl: String): Result<Unit> {
        return try {
            val userId = supabase.auth.currentUserOrNull()?.id
                ?: return Result.failure(Exception("User not authenticated"))
            
            supabase.from("users")
                .update(
                    buildJsonObject {
                        put("avatar_url", avatarUrl)
                    }
                ) {
                    filter {
                        eq("id", userId)
                    }
                }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 