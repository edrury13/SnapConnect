package com.example.snapconnect.data.repository

import com.example.snapconnect.data.remote.EmbeddingApi
import com.example.snapconnect.data.remote.EmbedRequestDto
import com.example.snapconnect.data.remote.EmbedResponseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmbeddingRepository @Inject constructor(
    private val api: EmbeddingApi,
) {
    suspend fun embedText(request: EmbedRequestDto): EmbedResponseDto = api.embedText(request)

    suspend fun embedImage(bytes: ByteArray, fileName: String, userId: String, tags: List<String> = emptyList()) =
        api.embedImage(bytes, fileName, userId, tags)
} 