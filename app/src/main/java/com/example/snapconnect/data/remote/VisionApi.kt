package com.example.snapconnect.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class VisionApi(
    private val client: HttpClient,
    private val baseUrl: String,
    private val apiKey: String,
) {
    suspend fun analyzeImage(imageUrl: String): AnalyzeImageResponse {
        return client.post("$baseUrl/api/v1/vision/analyze") {
            headers { append("X-API-Key", apiKey) }
            contentType(ContentType.Application.Json)
            setBody(AnalyzeImageRequest(image_url = imageUrl))
        }.body()
    }
}

@Serializable
data class AnalyzeImageRequest(val image_url: String)

@Serializable
data class AnalyzeImageResponse(val tags: List<String>) 