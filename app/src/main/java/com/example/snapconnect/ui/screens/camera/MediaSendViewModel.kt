package com.example.snapconnect.ui.screens.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.model.User
import com.example.snapconnect.data.model.MediaType
import com.example.snapconnect.data.repository.AuthRepository
import com.example.snapconnect.data.repository.FriendRepository
import com.example.snapconnect.data.repository.MessagesRepository
import com.example.snapconnect.data.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MediaSendUiState(
    val isLoading: Boolean = false,
    val friends: List<User> = emptyList(),
    val selectedFriends: Set<String> = emptySet(),
    val isSending: Boolean = false,
    val successCount: Int = 0,
    val totalCount: Int = 0,
    val errorMessage: String? = null
)

@HiltViewModel
class MediaSendViewModel @Inject constructor(
    private val friendRepository: FriendRepository,
    private val messagesRepository: MessagesRepository,
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MediaSendUiState())
    val uiState: StateFlow<MediaSendUiState> = _uiState.asStateFlow()
    
    init {
        loadFriends()
    }
    
    private fun loadFriends() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            friendRepository.getMyFriends()
                .onSuccess { friendPairs ->
                    // Extract just the User objects from the pairs
                    val friendsList = friendPairs.map { it.second }
                    _uiState.value = _uiState.value.copy(
                        friends = friendsList,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = error.message,
                        isLoading = false
                    )
                }
        }
    }
    
    fun toggleFriendSelection(friendId: String) {
        val currentSelection = _uiState.value.selectedFriends
        _uiState.value = _uiState.value.copy(
            selectedFriends = if (currentSelection.contains(friendId)) {
                currentSelection - friendId
            } else {
                currentSelection + friendId
            }
        )
    }
    
    fun sendToSelectedFriends(mediaUri: Uri, isVideo: Boolean, caption: String?, filterId: String? = null) {
        viewModelScope.launch {
            val selectedFriends = _uiState.value.selectedFriends
            if (selectedFriends.isEmpty()) return@launch
            
            _uiState.value = _uiState.value.copy(
                isSending = true,
                totalCount = selectedFriends.size,
                successCount = 0
            )
            
            val userId = authRepository.getCurrentUser()?.id
            if (userId == null) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "User not authenticated",
                    isSending = false
                )
                return@launch
            }
            
            // Upload media once
            storageRepository.uploadStoryMedia(mediaUri, userId, isVideo)
                .onSuccess { mediaUrl ->
                    // Send to each selected friend
                    var successCount = 0
                    
                    selectedFriends.forEach { friendId ->
                        // Create or get direct message group
                        messagesRepository.createDirectMessageGroup(friendId)
                            .onSuccess { group ->
                                // Send message
                                messagesRepository.sendMessage(
                                    groupId = group.id,
                                    content = caption ?: "",
                                    mediaUrl = mediaUrl,
                                    mediaType = if (isVideo) MediaType.VIDEO else MediaType.IMAGE
                                )
                                    .onSuccess {
                                        successCount++
                                        _uiState.value = _uiState.value.copy(
                                            successCount = successCount
                                        )
                                    }
                            }
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        isSending = false,
                        selectedFriends = emptySet()
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = error.message ?: "Failed to upload media",
                        isSending = false
                    )
                }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
} 