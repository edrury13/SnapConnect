package com.example.snapconnect.data.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class User(
    val id: String,
    val email: String,
    val username: String,
    val displayName: String? = null,
    val avatarUrl: String? = null,
    val createdAt: Instant
) 