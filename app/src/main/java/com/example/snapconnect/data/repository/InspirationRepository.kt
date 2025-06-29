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

@Singleton
class InspirationRepository @Inject constructor(
    private val api: InspirationApi,
    private val httpClient: HttpClient
) {
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

        val bodyJson = buildJsonObject {
            put("model", "dall-e-3")
            put("prompt", prompt)
            put("n", n)
            put("size", "1024x1024")
        }

        val httpResponse: HttpResponse = httpClient.post("https://api.openai.com/v1/images/generations") {
            setBody(bodyJson)
            headers {
                val key = System.getenv("OPENAI_KEY") ?: ""
                append(HttpHeaders.Authorization, "Bearer $key")
                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
        }

        if (httpResponse.status.value in 200..299) {
            val success = httpResponse.body<OpenAiImageResponse>()
            return success.data.map { it.url }
        } else {
            val errorJson = httpResponse.bodyAsText()
            throw Exception("OpenAI error: ${httpResponse.status.value} $errorJson")
        }
    }
} 