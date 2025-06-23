package com.example.snapconnect.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val supabase: SupabaseClient
) {
    
    suspend fun signUp(email: String, password: String, username: String): Result<Unit> {
        return try {
            supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                data = buildJsonObject {
                    put("username", username)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun signOut(): Result<Unit> {
        return try {
            supabase.auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun isUserLoggedIn(): Boolean {
        return supabase.auth.currentUserOrNull() != null
    }
    
    fun getCurrentUser() = supabase.auth.currentUserOrNull()
    
    fun sessionFlow(): Flow<UserSession?> = flow {
        emit(supabase.auth.currentSessionOrNull())
        supabase.auth.sessionStatus.collect { status ->
            when (status) {
                is io.github.jan.supabase.gotrue.SessionStatus.Authenticated -> emit(status.session)
                else -> emit(null)
            }
        }
    }
} 