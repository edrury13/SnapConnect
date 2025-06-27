package com.example.snapconnect.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import java.util.UUID

class LangchainApi(
    private val client: HttpClient,
    private val baseUrl: String,
    private val apiKey: String,
) {
    suspend fun autoCaption(userId: String, tagsCsv: String): AutoCaptionResponse {
        return client.post("$baseUrl/api/v1/langchain/auto-caption") {
            headers { append("X-API-Key", apiKey) }
            contentType(ContentType.Application.Json)
            setBody(AutoCaptionRequest(tags = tagsCsv, user_id = userId))
        }.body()
    }

    suspend fun processPost(
        userId: String, 
        storyId: String, 
        caption: String, 
        tags: List<String>,
        imageUrl: String? = null
    ): ProcessPostResponse {
        return client.post("$baseUrl/api/v1/langchain/process-post") {
            headers { append("X-API-Key", apiKey) }
            contentType(ContentType.Application.Json)
            setBody(ProcessPostRequest(
                user_id = userId, 
                story_id = storyId, 
                caption = caption, 
                tags = tags,
                image_url = imageUrl
            ))
        }.body()
    }
}

@Serializable
data class AutoCaptionRequest(val tags: String, val user_id: String)

@Serializable
data class AutoCaptionResponse(val caption: String)

@Serializable
data class ProcessPostRequest(
    val user_id: String, 
    val story_id: String, 
    val caption: String, 
    val tags: List<String> = emptyList(),
    val image_url: String? = null
)

@Serializable
data class ProcessPostResponse(
    val ai_caption: String, 
    val style: String,
    val style_confidence: Float? = null,
    val technique: String? = null
) 