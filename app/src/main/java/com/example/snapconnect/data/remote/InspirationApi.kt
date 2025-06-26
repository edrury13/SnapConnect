package com.example.snapconnect.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

/**
 * Retrofit-style helper using Ktor client for the RAG backend.
 */
class InspirationApi(
    private val client: HttpClient,
    private val baseUrl: String,
    private val apiKey: String,
) {

    suspend fun generateMoodBoard(description: String, limit: Int = 8): MoodBoardResponse {
        return client.post("$baseUrl/api/v1/inspiration/moodboard") {
            headers {
                append("X-API-Key", apiKey)
            }
            contentType(ContentType.Application.Json)
            setBody(MoodBoardRequest(description = description, limit = limit))
        }.body()
    }

    suspend fun analyseStyle(description: String, limit: Int = 10): StyleAnalysisResponse {
        return client.post("$baseUrl/api/v1/inspiration/style-analysis") {
            headers {
                append("X-API-Key", apiKey)
            }
            contentType(ContentType.Application.Json)
            setBody(StyleAnalysisRequest(description = description, limit = limit))
        }.body()
    }
}

@Serializable
data class MoodBoardRequest(val description: String, val limit: Int = 8)

@Serializable
data class MoodBoardItem(
    val content_id: String,
    val score: Double,
    val content_url: String = "",
    val style_tags: List<String> = emptyList(),
    val creator_id: String = "",
)

@Serializable
data class MoodBoardResponse(val items: List<MoodBoardItem> = emptyList())

@Serializable
data class StyleAnalysisRequest(val description: String, val limit: Int = 10)

@Serializable
data class SimilarCreator(val creator_id: String, val score: Double)

@Serializable
data class StyleAnalysisResponse(
    val dominant_style: String = "unknown",
    val similar_creators: List<SimilarCreator> = emptyList(),
    val reference_items: List<MoodBoardItem> = emptyList(),
) 