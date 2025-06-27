package com.example.snapconnect.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class Story(
    val id: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("media_url")
    val mediaUrl: String,
    @SerialName("media_type")
    val mediaType: MediaType,
    val caption: String? = null,
    @SerialName("viewer_ids")
    val viewerIds: List<String> = emptyList(),
    @SerialName("is_public")
    val isPublic: Boolean = true,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("expires_at")
    val expiresAt: Instant,
    @SerialName("style_tags")
    val styleTags: List<String> = emptyList(),
    @SerialName("ai_caption")
    val aiCaption: String? = null
)

@Serializable
enum class MediaType {
    IMAGE, VIDEO
} 