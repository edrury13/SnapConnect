package com.example.snapconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.remote.MoodBoardItem
import com.example.snapconnect.data.repository.InspirationRepository
import com.example.snapconnect.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspirationViewModel @Inject constructor(
    private val repo: InspirationRepository,
    private val storyRepository: StoryRepository,
) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val moodItems: List<MoodBoardItem> = emptyList(),
        val aiImages: List<String> = emptyList(),
        val loadingAi: Boolean = false,
        val error: String? = null,
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    fun generateMoodboard(prompt: String) {
        if (prompt.isBlank()) return
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null, aiImages = emptyList()) }
            try {
                val resp = repo.moodBoard(prompt)
                // Deduplicate so we show only one item per primary style tag (first tag in list)
                var deduped = resp.items
                    .groupBy { it.style_tags.firstOrNull() ?: it.content_id }
                    .values
                    .map { group ->
                        val withUrl = group.filter { it.content_url.isNotBlank() }
                        (withUrl.maxByOrNull { it.score } ?: withUrl.firstOrNull()) ?: group.first()
                    }

                // If any item lacks image, fetch from stories bucket
                val enriched = mutableListOf<MoodBoardItem>()
                for (item in deduped) {
                    if (item.content_url.isNotBlank()) {
                        enriched.add(item)
                        continue
                    }
                    val style = item.style_tags.firstOrNull()
                    if (style == null) {
                        enriched.add(item)
                        continue
                    }
                    val url = storyRepository.getStoriesByStyle(style).getOrNull()?.firstOrNull()?.mediaUrl ?: ""
                    enriched.add(if (url.isBlank()) item else item.copy(content_url = url))
                }
                deduped = enriched
                _state.update { it.copy(loading = false, moodItems = deduped) }
            } catch (e: Exception) {
                _state.update { it.copy(loading = false, error = e.message) }
            }
        }
    }

    fun generateAiImages(prompt: String, n: Int = 6) {
        if (prompt.isBlank()) return
        viewModelScope.launch {
            _state.update { it.copy(loadingAi = true, error = null, moodItems = emptyList()) }
            try {
                val urls = repo.generateAiImages(prompt, n)
                _state.update { it.copy(loadingAi = false, aiImages = urls) }
            } catch (e: Exception) {
                _state.update { it.copy(loadingAi = false, error = e.message) }
            }
        }
    }

    fun analyseStyle(caption: String) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            try {
                val resp = repo.analyseStyle(caption)
                // For now just feed reference_items into moodItems list
                _state.update { it.copy(loading = false, moodItems = resp.reference_items) }
            } catch (e: Exception) {
                _state.update { it.copy(loading = false, error = e.message) }
            }
        }
    }

    fun generateMoreAiImages(prompt: String, n: Int = 6) {
        if (prompt.isBlank()) return
        viewModelScope.launch {
            _state.update { it.copy(loadingAi = true, error = null) }
            try {
                val urls = repo.generateAiImages(prompt, n)
                _state.update { it.copy(loadingAi = false, aiImages = it.aiImages + urls) }
            } catch (e: Exception) {
                _state.update { it.copy(loadingAi = false, error = e.message) }
            }
        }
    }
} 