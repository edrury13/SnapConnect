package com.example.snapconnect.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class Comment(
    val id: String,
    @SerialName("story_id")
    val storyId: String,
    @SerialName("user_id")
    val userId: String,
    val text: String,
    @SerialName("created_at")
    val createdAt: Instant
) 