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
    val aiCaption: String? = null,
    @SerialName("likes_count")
    val likesCount: Int = 0,
    @SerialName("dislikes_count")
    val dislikesCount: Int = 0,
    @SerialName("user_reaction")
    val userReaction: ReactionType? = null // This will be populated from a JOIN query or separate query
)

@Serializable
enum class MediaType {
    IMAGE, VIDEO
}

@Serializable
enum class ReactionType {
    LIKE, DISLIKE
}

@Serializable
data class StoryReaction(
    val id: String,
    @SerialName("story_id")
    val storyId: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("reaction_type")
    val reactionType: ReactionType,
    @SerialName("created_at")
    val createdAt: Instant
) 