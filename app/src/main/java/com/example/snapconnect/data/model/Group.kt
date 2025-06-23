package com.example.snapconnect.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class Group(
    val id: String,
    val name: String,
    @SerialName("creator_id")
    val creatorId: String,
    @SerialName("member_ids")
    val memberIds: List<String>,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("created_at")
    val createdAt: Instant
)

@Serializable
data class Message(
    val id: String,
    @SerialName("group_id")
    val groupId: String,
    @SerialName("sender_id")
    val senderId: String,
    val content: String,
    @SerialName("media_url")
    val mediaUrl: String? = null,
    @SerialName("media_type")
    val mediaType: MediaType? = null,
    @SerialName("created_at")
    val createdAt: Instant
) 