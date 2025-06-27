package com.example.snapconnect.data.repository

import com.example.snapconnect.data.remote.VisionApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VisionRepository @Inject constructor(
    private val api: VisionApi,
) {
    suspend fun analyzeImage(imageUrl: String): Result<List<String>> {
        return try {
            val response = api.analyzeImage(imageUrl)
            Result.success(response.tags)
        } catch (e: Exception) {
            // Return fallback tags on error
            Result.success(listOf("image"))
        }
    }
} 