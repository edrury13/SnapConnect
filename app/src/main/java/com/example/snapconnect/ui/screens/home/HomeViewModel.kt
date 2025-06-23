package com.example.snapconnect.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.model.Story
import com.example.snapconnect.data.model.User
import com.example.snapconnect.data.repository.StoryRepository
import com.example.snapconnect.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val userStories: Map<User, List<Story>> = emptyMap(),
    val errorMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadStories()
        // Trigger cleanup of expired stories
        viewModelScope.launch {
            storyRepository.triggerStoryCleanup()
        }
    }
    
    fun loadStories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            storyRepository.getFriendsStories()
                .onSuccess { stories ->
                    // Group stories by user
                    val groupedStories = stories.groupBy { it.userId }
                    val userIds = groupedStories.keys.toList()
                    
                    // Fetch user information
                    userRepository.getUsersByIds(userIds)
                        .onSuccess { users ->
                            val userMap = users.associateBy { it.id }
                            val userStories = mutableMapOf<User, List<Story>>()
                            
                            groupedStories.forEach { (userId, userStoryList) ->
                                userMap[userId]?.let { user ->
                                    userStories[user] = userStoryList
                                }
                            }
                            
                            _uiState.value = HomeUiState(
                                isLoading = false,
                                userStories = userStories
                            )
                        }
                        .onFailure { error ->
                            _uiState.value = HomeUiState(
                                isLoading = false,
                                errorMessage = error.message
                            )
                        }
                }
                .onFailure { error ->
                    _uiState.value = HomeUiState(
                        isLoading = false,
                        errorMessage = error.message
                    )
                }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
} 