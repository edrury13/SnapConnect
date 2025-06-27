package com.example.snapconnect.ui.screens.inspiration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.model.Story
import com.example.snapconnect.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StyleGalleryUiState(
    val isLoading: Boolean = false,
    val stories: List<Story> = emptyList(),
    val styleTag: String = "",
    val error: String? = null
)

@HiltViewModel
class StyleGalleryViewModel @Inject constructor(
    private val storyRepository: StoryRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(StyleGalleryUiState())
    val uiState: StateFlow<StyleGalleryUiState> = _uiState.asStateFlow()
    
    fun loadStoriesByStyle(styleTag: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                styleTag = styleTag,
                error = null
            )
            
            storyRepository.getStoriesByStyle(styleTag)
                .onSuccess { stories ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        stories = stories
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message
                    )
                }
        }
    }
} 