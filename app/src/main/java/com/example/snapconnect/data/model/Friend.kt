package com.example.snapconnect.data.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class Friend(
    val id: String,
    val userId: String,
    val friendId: String,
    val status: FriendStatus,
    val createdAt: Instant
)

@Serializable
enum class FriendStatus {
    PENDING, ACCEPTED, BLOCKED
} 