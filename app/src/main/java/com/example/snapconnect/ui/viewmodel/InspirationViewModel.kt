package com.example.snapconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.remote.MoodBoardItem
import com.example.snapconnect.data.repository.InspirationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspirationViewModel @Inject constructor(
    private val repo: InspirationRepository,
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
            _state.update { it.copy(loading = true, error = null) }
            try {
                val resp = repo.moodBoard(prompt)
                _state.update { it.copy(loading = false, moodItems = resp.items) }
            } catch (e: Exception) {
                _state.update { it.copy(loading = false, error = e.message) }
            }
        }
    }

    fun generateAiImages(prompt: String) {
        if (prompt.isBlank()) return
        viewModelScope.launch {
            _state.update { it.copy(loadingAi = true, error = null) }
            try {
                val urls = repo.generateAiImages(prompt)
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
} 