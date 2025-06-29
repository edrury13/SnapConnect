package com.example.snapconnect.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenAiImageResponse(
    val created: Long,
    val data: List<ImageData>
)

@Serializable
data class ImageData(
    @SerialName("url") val url: String
) 