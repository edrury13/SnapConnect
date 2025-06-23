package com.example.snapconnect.data.repository

import android.content.Context
import android.net.Uri
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageRepository @Inject constructor(
    private val supabase: SupabaseClient,
    private val context: Context
) {
    
    suspend fun uploadStoryMedia(
        fileUri: Uri,
        userId: String,
        isVideo: Boolean
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val file = File(fileUri.path ?: return@withContext Result.failure(Exception("Invalid file URI")))
            val fileName = "${UUID.randomUUID()}.${if (isVideo) "mp4" else "jpg"}"
            val path = "$userId/$fileName"
            
            val bucket = supabase.storage.from("stories")
            bucket.upload(path, file.readBytes(), upsert = false)
            
            val publicUrl = bucket.publicUrl(path)
            Result.success(publicUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun uploadAvatar(
        fileUri: Uri,
        userId: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val file = File(fileUri.path ?: return@withContext Result.failure(Exception("Invalid file URI")))
            val fileName = "avatar_${System.currentTimeMillis()}.jpg"
            val path = "$userId/$fileName"
            
            val bucket = supabase.storage.from("avatars")
            bucket.upload(path, file.readBytes(), upsert = true)
            
            val publicUrl = bucket.publicUrl(path)
            Result.success(publicUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteStoryMedia(mediaUrl: String): Result<Unit> {
        return try {
            // Extract path from URL
            val path = mediaUrl.substringAfter("/storage/v1/object/public/stories/")
            supabase.storage.from("stories").delete(path)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 