package com.example.snapconnect.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.model.Story
import com.example.snapconnect.data.model.User
import com.example.snapconnect.data.repository.StoryRepository
import com.example.snapconnect.data.repository.UserRepository
import com.example.snapconnect.data.repository.FriendRepository
import com.example.snapconnect.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val recommendedStories: Map<User, List<Story>> = emptyMap(), // Stories similar to user's interests
    val otherStories: Map<User, List<Story>> = emptyMap(), // All other stories
    val hasRecommendations: Boolean = false,
    val styleFilter: String? = null,
    val availableStyleTags: List<String> = emptyList(),
    val errorMessage: String? = null,
    val friendIds: Set<String> = emptySet(),
    val currentUserId: String? = null
) {
    // Convenience property to get all stories for backward compatibility
    val userStories: Map<User, List<Story>> 
        get() = recommendedStories + otherStories
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    private var friendIds: Set<String> = emptySet()
    
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
            
            try {
                // Fetch stories with recommendations
                val (recommendedStories, otherStories) = storyRepository.getStoriesWithRecommendations()
                    .getOrDefault(Pair(emptyList(), emptyList()))
                
                // Fetch friend IDs
                friendIds = friendRepository.getMyFriends().getOrDefault(emptyList()).map { it.second.id }.toSet()
                
                // Get unique user IDs from both lists
                val allUserIds = (recommendedStories.map { it.userId } + otherStories.map { it.userId }).distinct()
                
                // Fetch user information
                userRepository.getUsersByIds(allUserIds)
                    .onSuccess { users ->
                        val userMap = users.associateBy { it.id }
                        
                        // Group recommended stories by user
                        val recommendedUserStories = mutableMapOf<User, List<Story>>()
                        recommendedStories.groupBy { it.userId }.forEach { (userId, userStoryList) ->
                            userMap[userId]?.let { user ->
                                recommendedUserStories[user] = userStoryList
                            }
                        }
                        
                        // Group other stories by user
                        val otherUserStories = mutableMapOf<User, List<Story>>()
                        otherStories.groupBy { it.userId }.forEach { (userId, userStoryList) ->
                            userMap[userId]?.let { user ->
                                otherUserStories[user] = userStoryList
                            }
                        }
                        
                        val allStories = recommendedStories + otherStories
                        val styleTags = allStories.flatMap { it.styleTags }.distinct()
                        val currentUid = authRepository.getCurrentUser()?.id
                        _uiState.value = HomeUiState(
                            isLoading = false,
                            recommendedStories = recommendedUserStories,
                            otherStories = otherUserStories,
                            hasRecommendations = recommendedUserStories.isNotEmpty(),
                            availableStyleTags = styleTags,
                            friendIds = friendIds,
                            currentUserId = currentUid
                        )
                    }
                    .onFailure { error ->
                        _uiState.value = HomeUiState(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
            } catch (e: Exception) {
                // Fallback to original implementation if recommendations fail
                loadStoriesWithoutRecommendations()
            }
        }
    }
    
    private suspend fun loadStoriesWithoutRecommendations() {
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
                            
                        // Put all stories in "other" category when recommendations are not available
                            val allStories = userStories.values.flatten()
                            val styleTags = allStories.flatMap { it.styleTags }.distinct()
                            val currentUid = authRepository.getCurrentUser()?.id
                            _uiState.value = HomeUiState(
                                isLoading = false,
                                recommendedStories = emptyMap(),
                                otherStories = userStories,
                                hasRecommendations = false,
                                availableStyleTags = styleTags,
                                friendIds = friendIds,
                                currentUserId = currentUid
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
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun setStyleFilter(style: String?) {
        _uiState.value = _uiState.value.copy(styleFilter = style)
    }
} 