package com.example.snapconnect.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class Friend(
    val id: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("friend_id")
    val friendId: String,
    val status: FriendStatus,
    @SerialName("created_at")
    val createdAt: Instant
)

@Serializable
enum class FriendStatus {
    PENDING, ACCEPTED, BLOCKED
} 