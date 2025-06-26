package com.example.snapconnect.data.repository

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
    private val embeddingRepo: EmbeddingRepository,
) {
    
    suspend fun uploadStoryMedia(uri: Uri, userId: String, isVideo: Boolean): Result<String> {
        return try {
            withContext(Dispatchers.IO) {
                val fileName = "${System.currentTimeMillis()}.${if (isVideo) "mp4" else "jpg"}"
                val path = "$userId/$fileName"
                
                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(uri)
                    ?: return@withContext Result.failure(Exception("Failed to open file"))
                
                val bytes = inputStream.use { it.readBytes() }
                
                // Fire-and-forget: embed image if not video
                if (!isVideo) {
                    kotlin.runCatching {
                        embeddingRepo.embedImage(
                            bytes = bytes,
                            fileName = fileName,
                            userId = userId,
                            tags = emptyList()
                        )
                    }
                }
                
                supabase.storage
                    .from("stories")
                    .upload(path, bytes, upsert = false)
                
                val publicUrl = supabase.storage
                    .from("stories")
                    .publicUrl(path)
                
                Result.success(publicUrl)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun uploadAvatar(uri: Uri, userId: String): Result<String> {
        return try {
            withContext(Dispatchers.IO) {
                val fileName = "avatar_${System.currentTimeMillis()}.jpg"
                val path = "$userId/$fileName"
                
                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(uri)
                    ?: return@withContext Result.failure(Exception("Failed to open file"))
                
                val bytes = inputStream.use { it.readBytes() }
                
                supabase.storage
                    .from("avatars")
                    .upload(path, bytes, upsert = true)
                
                val publicUrl = supabase.storage
                    .from("avatars")
                    .publicUrl(path)
                
                Result.success(publicUrl)
            }
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