package com.example.snapconnect.ui.screens.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.model.User
import com.example.snapconnect.data.repository.AuthRepository
import com.example.snapconnect.data.repository.UserRepository
import com.example.snapconnect.data.repository.StoryRepository
import com.example.snapconnect.data.repository.FriendRepository
import com.example.snapconnect.data.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val isLoading: Boolean = false,
    val currentUser: User? = null,
    val storyCount: Int = 0,
    val friendCount: Int = 0,
    val snapCount: Int = 0,
    val isEditingProfile: Boolean = false,
    val isUploadingAvatar: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository,
    private val friendRepository: FriendRepository,
    private val storageRepository: StorageRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    
    init {
        loadProfile()
    }
    
    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val currentAuthUser = authRepository.getCurrentUser()
            if (currentAuthUser != null) {
                // Load user profile
                userRepository.getUser(currentAuthUser.id)
                    .onSuccess { user ->
                        _uiState.value = _uiState.value.copy(
                            currentUser = user
                        )
                    }
                    .onFailure { exception ->
                        _uiState.value = _uiState.value.copy(
                            errorMessage = exception.message
                        )
                    }
                
                // Load stats in parallel
                launch { loadStoryCount() }
                launch { loadFriendCount() }
                
                _uiState.value = _uiState.value.copy(isLoading = false)
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "No user logged in"
                )
            }
        }
    }
    
    private suspend fun loadStoryCount() {
        storyRepository.getMyStoriesCount()
            .onSuccess { count ->
                _uiState.value = _uiState.value.copy(storyCount = count)
            }
    }
    
    private suspend fun loadFriendCount() {
        friendRepository.getMyFriends()
            .onSuccess { friends ->
                _uiState.value = _uiState.value.copy(friendCount = friends.size)
            }
    }
    
    fun toggleEditProfile() {
        _uiState.value = _uiState.value.copy(
            isEditingProfile = !_uiState.value.isEditingProfile
        )
    }
    
    fun updateProfile(displayName: String?, username: String?) {
        viewModelScope.launch {
            val currentUser = _uiState.value.currentUser ?: return@launch
            
            userRepository.updateProfile(
                displayName = displayName,
                username = username
            )
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        currentUser = currentUser.copy(
                            displayName = displayName,
                            username = username ?: currentUser.username
                        ),
                        isEditingProfile = false,
                        successMessage = "Profile updated successfully"
                    )
                    // Reload profile to get fresh data
                    loadProfile()
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = error.message
                    )
                }
        }
    }
    
    fun uploadAvatar(uri: Uri) {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUser()?.id ?: return@launch
            
            _uiState.value = _uiState.value.copy(isUploadingAvatar = true)
            
            storageRepository.uploadAvatar(uri, userId)
                .onSuccess { avatarUrl ->
                    userRepository.updateAvatar(avatarUrl)
                        .onSuccess {
                            _uiState.value = _uiState.value.copy(
                                currentUser = _uiState.value.currentUser?.copy(
                                    avatarUrl = avatarUrl
                                ),
                                isUploadingAvatar = false,
                                successMessage = "Avatar updated successfully"
                            )
                        }
                        .onFailure { error ->
                            _uiState.value = _uiState.value.copy(
                                isUploadingAvatar = false,
                                errorMessage = error.message
                            )
                        }
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isUploadingAvatar = false,
                        errorMessage = error.message
                    )
                }
        }
    }
    
    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
} 