package com.example.snapconnect.data.repository

import com.example.snapconnect.data.remote.InspirationApi
import com.example.snapconnect.data.remote.MoodBoardResponse
import com.example.snapconnect.data.remote.StyleAnalysisResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InspirationRepository @Inject constructor(
    private val api: InspirationApi,
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
} 