package com.example.snapconnect.ui.screens.story

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.model.Comment
import com.example.snapconnect.data.model.Story
import com.example.snapconnect.data.model.User
import com.example.snapconnect.data.repository.AuthRepository
import com.example.snapconnect.data.repository.CommentsRepository
import com.example.snapconnect.data.repository.StoryRepository
import com.example.snapconnect.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StoryViewUiState(
    val isLoading: Boolean = false,
    val currentStory: Story? = null,
    val currentUser: User? = null,
    val allUserStories: List<Story> = emptyList(),
    val currentIndex: Int = 0,
    val comments: List<Pair<Comment, User>> = emptyList(),
    val showComments: Boolean = false,
    val isAddingComment: Boolean = false,
    val commentInput: String = "",
    val errorMessage: String? = null
)

@HiltViewModel
class StoryViewViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val commentsRepository: CommentsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val storyId: String = savedStateHandle.get<String>("storyId") ?: ""
    
    private val _uiState = MutableStateFlow(StoryViewUiState())
    val uiState: StateFlow<StoryViewUiState> = _uiState.asStateFlow()
    
    private var commentsJob: Job? = null
    
    init {
        loadStory()
    }
    
    private fun loadStory() {
        viewModelScope.launch {
            _uiState.value = StoryViewUiState(isLoading = true)
            
            // Get all stories to find the current one and its user's other stories
            storyRepository.getFriendsStories()
                .onSuccess { allStories ->
                    val currentStory = allStories.find { it.id == storyId }
                    if (currentStory != null) {
                        // Get user info
                        userRepository.getUser(currentStory.userId)
                            .onSuccess { user ->
                                // Get all stories from this user
                                val userStories = allStories
                                    .filter { it.userId == currentStory.userId }
                                    .sortedBy { it.createdAt }
                                
                                val currentIndex = userStories.indexOfFirst { it.id == storyId }
                                
                                _uiState.value = StoryViewUiState(
                                    isLoading = false,
                                    currentStory = currentStory,
                                    currentUser = user,
                                    allUserStories = userStories,
                                    currentIndex = currentIndex
                                )
                                
                                // Mark story as viewed
                                markAsViewed(currentStory.id)
                            }
                            .onFailure { error ->
                                _uiState.value = StoryViewUiState(
                                    isLoading = false,
                                    errorMessage = error.message
                                )
                            }
                    } else {
                        _uiState.value = StoryViewUiState(
                            isLoading = false,
                            errorMessage = "Story not found"
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.value = StoryViewUiState(
                        isLoading = false,
                        errorMessage = error.message
                    )
                }
        }
    }
    
    fun nextStory() {
        val currentState = _uiState.value
        val nextIndex = currentState.currentIndex + 1
        
        if (nextIndex < currentState.allUserStories.size) {
            val nextStory = currentState.allUserStories[nextIndex]
            _uiState.value = currentState.copy(
                currentStory = nextStory,
                currentIndex = nextIndex,
                comments = emptyList(), // Clear comments when changing story
                showComments = false
            )
            markAsViewed(nextStory.id)
            
            // Cancel comments observation
            commentsJob?.cancel()
            commentsJob = null
        }
    }
    
    fun previousStory() {
        val currentState = _uiState.value
        val previousIndex = currentState.currentIndex - 1
        
        if (previousIndex >= 0) {
            val previousStory = currentState.allUserStories[previousIndex]
            _uiState.value = currentState.copy(
                currentStory = previousStory,
                currentIndex = previousIndex,
                comments = emptyList(), // Clear comments when changing story
                showComments = false
            )
            
            // Cancel comments observation
            commentsJob?.cancel()
            commentsJob = null
        }
    }
    
    fun hasNextStory(): Boolean {
        val currentState = _uiState.value
        return currentState.currentIndex < currentState.allUserStories.size - 1
    }
    
    fun hasPreviousStory(): Boolean {
        return _uiState.value.currentIndex > 0
    }
    
    fun getCurrentUserId(): String? {
        return authRepository.getCurrentUser()?.id
    }
    
    private fun markAsViewed(storyId: String) {
        viewModelScope.launch {
            storyRepository.markStoryAsViewed(storyId)
        }
    }
    
    fun deleteStory(storyId: String) {
        viewModelScope.launch {
            storyRepository.deleteStory(storyId)
                .onSuccess {
                    // If this was the only story, we need to handle navigation
                    val currentState = _uiState.value
                    if (currentState.allUserStories.size == 1) {
                        _uiState.value = currentState.copy(
                            currentStory = null,
                            errorMessage = "Story deleted"
                        )
                    } else {
                        // Move to next or previous story
                        if (hasNextStory()) {
                            nextStory()
                        } else if (hasPreviousStory()) {
                            previousStory()
                        }
                    }
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = error.message
                    )
                }
        }
    }
    
    fun toggleComments() {
        val newShowComments = !_uiState.value.showComments
        _uiState.value = _uiState.value.copy(showComments = newShowComments)
        
        if (newShowComments && _uiState.value.currentStory != null) {
            loadComments(_uiState.value.currentStory!!.id)
        } else {
            // Cancel comments observation when hiding
            commentsJob?.cancel()
            commentsJob = null
        }
    }
    
    private fun loadComments(storyId: String) {
        commentsJob?.cancel()
        commentsJob = viewModelScope.launch {
            commentsRepository.getCommentsRealtime(storyId)
                .collect { comments ->
                    _uiState.value = _uiState.value.copy(comments = comments)
                }
        }
    }
    
    fun updateCommentInput(text: String) {
        _uiState.value = _uiState.value.copy(commentInput = text)
    }
    
    fun sendComment() {
        val comment = _uiState.value.commentInput.trim()
        val storyId = _uiState.value.currentStory?.id ?: return
        
        if (comment.isEmpty()) return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isAddingComment = true,
                commentInput = ""
            )
            
            commentsRepository.addComment(storyId, comment)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isAddingComment = false)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isAddingComment = false,
                        errorMessage = error.message,
                        commentInput = comment // Restore on failure
                    )
                }
        }
    }
    
    fun deleteComment(commentId: String) {
        viewModelScope.launch {
            commentsRepository.deleteComment(commentId)
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = error.message
                    )
                }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        commentsJob?.cancel()
    }
} 