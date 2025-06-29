package com.example.snapconnect.data.repository

import com.example.snapconnect.data.remote.InspirationApi
import com.example.snapconnect.data.remote.MoodBoardResponse
import com.example.snapconnect.data.remote.StyleAnalysisResponse
import com.example.snapconnect.data.remote.OpenAiImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.Serializable

@Singleton
class InspirationRepository @Inject constructor(
    private val api: InspirationApi,
    private val httpClient: HttpClient
) {
    private val BACKEND_BASE_URL = "https://snapconnect-backend.onrender.com"
    private val API_KEY = "RT5PrU6c8dnk5UUaP1dyDIqQjY6KuV2IXXKZvKvkMiz8N2AwrZhHJwYm8GZlCjQWPqaAB9UAMqwYkzPx67DQnx6muHfXscKpmmJVVVp9FWoyWIudwx35Qrv5H3TqwCnm"

    suspend fun moodBoard(prompt: String, limit: Int = 8): MoodBoardResponse {
        if (prompt.isBlank()) {
            // Backend requires description; return empty response
            return MoodBoardResponse(items = emptyList())
        }
        return api.generateMoodBoard(prompt, limit)
    }

    suspend fun analyseStyle(caption: String, limit: Int = 10): StyleAnalysisResponse {
        if (caption.isBlank()) {
            // Backend requires description; respond with defaults
            return StyleAnalysisResponse(
                dominant_style = "unknown",
                similar_creators = emptyList(),
                reference_items = emptyList()
            )
        }
        return api.analyseStyle(caption, limit)
    }

    suspend fun generateAiImages(prompt: String, n: Int = 4): List<String> {
        if (prompt.isBlank()) return emptyList()

        val response: GenerateImagesBackendResponse = httpClient.post("$BACKEND_BASE_URL/api/v1/ai/generate") {
            setBody(buildJsonObject {
                put("prompt", prompt)
                put("n", n)
                put("size", "1024x1024")
            })
            headers {
                append("X-API-Key", API_KEY)
                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
        }.body()
        return response.urls
    }

    @Serializable
    private data class GenerateImagesBackendResponse(val urls: List<String>)
} 