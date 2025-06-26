package com.example.snapconnect.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import io.ktor.client.request.forms.formData
import io.ktor.http.HttpHeaders
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.Headers

/**
 * Lightweight Ktor wrapper around the RAG /embed/text endpoint.
 */
class EmbeddingApi(
    private val client: HttpClient,
    private val baseUrl: String,
    private val apiKey: String,
) {

    suspend fun embedText(request: EmbedRequestDto): EmbedResponseDto {
        return client.post("$baseUrl/api/v1/embed/text") {
            headers {
                append("X-API-Key", apiKey)
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun embedImage(
        imageBytes: ByteArray,
        fileName: String,
        userId: String,
        tags: List<String> = emptyList(),
    ): EmbedResponseDto {
        val formData = io.ktor.client.request.forms.formData {
            append("file", imageBytes, Headers.build {
                append(HttpHeaders.ContentType, "image/${fileName.substringAfterLast('.', "jpeg")}")
                append(HttpHeaders.ContentDisposition, "filename=$fileName")
            })
            append("user_id", userId)
            if (tags.isNotEmpty()) append("tags", tags.joinToString(","))
        }
        return client.post("$baseUrl/api/v1/embed/image") {
            headers { append("X-API-Key", apiKey) }
            setBody(MultiPartFormDataContent(formData))
        }.body()
    }
}

@Serializable
data class EmbedRequestDto(
    val content: String,
    @SerialName("user_id") val userId: String,
    val tags: List<String> = emptyList(),
    val metadata: Map<String, String> = emptyMap(),
)

@Serializable
data class EmbedResponseDto(
    @SerialName("content_id") val contentId: String = "",
    val status: String = "",
    val message: String = "",
) 