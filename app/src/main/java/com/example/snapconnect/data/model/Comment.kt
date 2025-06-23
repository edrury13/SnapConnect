package com.example.snapconnect.data.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class Comment(
    val id: String,
    val storyId: String,
    val userId: String,
    val text: String,
    val createdAt: Instant
) 