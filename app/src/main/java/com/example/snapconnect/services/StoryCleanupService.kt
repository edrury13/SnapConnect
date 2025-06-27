package com.example.snapconnect.services

import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc

@Singleton
class StoryCleanupService @Inject constructor(
    private val httpClient: HttpClient,
    private val supabaseClient: SupabaseClient
) {
    companion object {
        // Using the same values from AppModule
        private const val SUPABASE_URL = "https://ngrbhordabshfvlbqmzu.supabase.co"
        private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5ncmJob3JkYWJzaGZ2bGJxbXp1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTA3MDM3MzAsImV4cCI6MjA2NjI3OTczMH0.0Nse10raADtK8pU3V45hUKdQT4tyeYmvMLMw4P8MYvI"
    }
    
    suspend fun triggerCleanup(): Result<String> = withContext(Dispatchers.IO) {
        try {
            // First try the edge function
            val response = httpClient.post("$SUPABASE_URL/functions/v1/cleanup-stories") {
                headers {
                    append("apikey", SUPABASE_ANON_KEY)
                    append("Authorization", "Bearer $SUPABASE_ANON_KEY")
                    append("Content-Type", "application/json")
                }
                setBody("{}")
            }
            
            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody = response.bodyAsText()
                    println("Story cleanup successful via Edge Function: $responseBody")
                    Result.success(responseBody)
                }
                HttpStatusCode.NotFound -> {
                    // Edge function not deployed, fall back to RPC
                    println("Edge function not found, falling back to RPC method")
                    triggerCleanupViaRpc()
                }
                else -> {
                    val error = "Story cleanup failed with status: ${response.status.value}"
                    println(error)
                    Result.failure(Exception(error))
                }
            }
        } catch (e: Exception) {
            println("Failed to trigger story cleanup via Edge Function: ${e.message}")
            // Try fallback RPC method
            triggerCleanupViaRpc()
        }
    }
    
    private suspend fun triggerCleanupViaRpc(): Result<String> {
        return try {
            // Call the cleanup function via RPC
            supabaseClient.postgrest.rpc("trigger_story_cleanup")
            
            // Also try to call delete_expired_stories directly
            supabaseClient.postgrest.rpc("delete_expired_stories")
            
            val message = "Story cleanup triggered via RPC"
            println(message)
            Result.success(message)
        } catch (e: Exception) {
            println("Failed to trigger story cleanup via RPC: ${e.message}")
            Result.failure(e)
        }
    }
} 