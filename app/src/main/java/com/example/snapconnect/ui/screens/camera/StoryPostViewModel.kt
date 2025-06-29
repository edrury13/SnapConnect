package com.example.snapconnect.ui.screens.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.model.MediaType
import com.example.snapconnect.data.repository.AuthRepository
import com.example.snapconnect.data.repository.StorageRepository
import com.example.snapconnect.data.repository.StoryRepository
import com.example.snapconnect.data.repository.MessagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StoryPostUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class StoryPostViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository,
    private val messagesRepository: MessagesRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(StoryPostUiState())
    val uiState: StateFlow<StoryPostUiState> = _uiState.asStateFlow()
    
    fun postStory(
        mediaUri: Uri,
        isVideo: Boolean,
        caption: String?,
        isPublic: Boolean = true,
        filterId: String? = null
    ) {
        viewModelScope.launch {
            _uiState.value = StoryPostUiState(isLoading = true)
            
            val userId = authRepository.getCurrentUser()?.id
            if (userId == null) {
                _uiState.value = StoryPostUiState(errorMessage = "User not authenticated")
                return@launch
            }
            
            // Upload media to storage
            storageRepository.uploadStoryMedia(mediaUri, userId, isVideo)
                .onSuccess { mediaUrl ->
                    // Create story in database
                    storyRepository.createStory(
                        mediaUrl = mediaUrl,
                        mediaType = if (isVideo) MediaType.VIDEO else MediaType.IMAGE,
                        caption = caption?.takeIf { it.isNotBlank() },
                        isPublic = isPublic
                    )
                        .onSuccess {
                            _uiState.value = StoryPostUiState(isSuccess = true)
                        }
                        .onFailure { error ->
                            _uiState.value = StoryPostUiState(
                                errorMessage = error.message ?: "Failed to post story"
                            )
                        }
                }
                .onFailure { error ->
                    _uiState.value = StoryPostUiState(
                        errorMessage = error.message ?: "Failed to upload media"
                    )
                }
        }
    }
    
    fun sendToChat(mediaUri: Uri, isVideo: Boolean, caption: String?, groupId: String, filterId: String? = null) {
        viewModelScope.launch {
            _uiState.value = StoryPostUiState(isLoading = true)
            
            val userId = authRepository.getCurrentUser()?.id
            if (userId == null) {
                _uiState.value = StoryPostUiState(errorMessage = "User not authenticated")
                return@launch
            }
            
            // Upload media to storage
            storageRepository.uploadStoryMedia(mediaUri, userId, isVideo)
                .onSuccess { mediaUrl ->
                    // Send as message
                    messagesRepository.sendMessage(
                        groupId = groupId,
                        content = caption ?: "",
                        mediaUrl = mediaUrl,
                        mediaType = if (isVideo) MediaType.VIDEO else MediaType.IMAGE
                    )
                        .onSuccess {
                            _uiState.value = StoryPostUiState(isSuccess = true)
                        }
                        .onFailure { error ->
                            _uiState.value = StoryPostUiState(
                                errorMessage = error.message ?: "Failed to send message"
                            )
                        }
                }
                .onFailure { error ->
                    _uiState.value = StoryPostUiState(
                        errorMessage = error.message ?: "Failed to upload media"
                    )
                }
        }
    }
    
    fun saveMemory(
        mediaUri: Uri,
        isVideo: Boolean,
        caption: String?,
        filterId: String? = null
    ) {
        viewModelScope.launch {
            _uiState.value = StoryPostUiState(isLoading = true)

            val userId = authRepository.getCurrentUser()?.id
            if (userId == null) {
                _uiState.value = StoryPostUiState(errorMessage = "User not authenticated")
                return@launch
            }

            storageRepository.uploadStoryMedia(mediaUri, userId, isVideo)
                .onSuccess { mediaUrl ->
                    storyRepository.createStory(
                        mediaUrl = mediaUrl,
                        mediaType = if (isVideo) MediaType.VIDEO else MediaType.IMAGE,
                        caption = caption?.takeIf { it.isNotBlank() },
                        isPublic = false,
                        keepForever = true
                    )
                        .onSuccess {
                            _uiState.value = StoryPostUiState(isSuccess = true)
                        }
                        .onFailure { error ->
                            _uiState.value = StoryPostUiState(errorMessage = error.message ?: "Failed to save memory")
                        }
                }
                .onFailure { error ->
                    _uiState.value = StoryPostUiState(errorMessage = error.message ?: "Failed to upload media")
                }
        }
    }
    
    fun clearState() {
        _uiState.value = StoryPostUiState()
    }
} 