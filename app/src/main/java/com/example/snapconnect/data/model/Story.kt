package com.example.snapconnect.data.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class Story(
    val id: String,
    val userId: String,
    val mediaUrl: String,
    val mediaType: MediaType,
    val caption: String? = null,
    val viewerIds: List<String> = emptyList(),
    val createdAt: Instant,
    val expiresAt: Instant
)

@Serializable
enum class MediaType {
    IMAGE, VIDEO
} 