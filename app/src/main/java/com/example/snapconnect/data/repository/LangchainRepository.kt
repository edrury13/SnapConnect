package com.example.snapconnect.data.repository

import com.example.snapconnect.data.remote.LangchainApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LangchainRepository @Inject constructor(
    private val api: LangchainApi,
) {
    suspend fun autoCaption(userId: String, tagsCsv: String) = api.autoCaption(userId, tagsCsv)

    suspend fun processPost(userId: String, storyId: String, caption: String, tags: List<String>, imageUrl: String? = null) =
        api.processPost(userId, storyId, caption, tags, imageUrl)
} 