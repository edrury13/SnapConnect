package com.example.snapconnect.data.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class Group(
    val id: String,
    val name: String,
    val creatorId: String,
    val memberIds: List<String>,
    val avatarUrl: String? = null,
    val createdAt: Instant
)

@Serializable
data class Message(
    val id: String,
    val groupId: String,
    val senderId: String,
    val content: String,
    val mediaUrl: String? = null,
    val mediaType: MediaType? = null,
    val createdAt: Instant
) 