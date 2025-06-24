package com.example.snapconnect.ui.screens.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.model.Friend
import com.example.snapconnect.data.model.User
import com.example.snapconnect.data.repository.FriendRepository
import com.example.snapconnect.data.repository.MessagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FriendsUiState(
    val isLoading: Boolean = false,
    val friends: List<Pair<Friend, User>> = emptyList(),
    val pendingRequests: List<Pair<Friend, User>> = emptyList(),
    val sentRequests: List<Pair<Friend, User>> = emptyList(),
    val searchResults: List<User> = emptyList(),
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val selectedTab: FriendsTab = FriendsTab.FRIENDS
)

enum class FriendsTab {
    FRIENDS, REQUESTS, ADD_FRIENDS
}

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendRepository: FriendRepository,
    private val messagesRepository: MessagesRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()
    
    init {
        loadFriends()
    }
    
    fun selectTab(tab: FriendsTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
        when (tab) {
            FriendsTab.FRIENDS -> loadFriends()
            FriendsTab.REQUESTS -> loadRequests()
            FriendsTab.ADD_FRIENDS -> clearSearch()
        }
    }
    
    private fun loadFriends() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            friendRepository.getMyFriends()
                .onSuccess { friends ->
                    _uiState.value = _uiState.value.copy(
                        friends = friends,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
        }
    }
    
    private fun loadRequests() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Load both pending and sent requests
            val pendingResult = friendRepository.getPendingRequests()
            val sentResult = friendRepository.getSentRequests()
            
            if (pendingResult.isSuccess && sentResult.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    pendingRequests = pendingResult.getOrDefault(emptyList()),
                    sentRequests = sentResult.getOrDefault(emptyList()),
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load requests"
                )
            }
        }
    }
    
    fun searchUsers(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        
        if (query.isBlank()) {
            clearSearch()
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSearching = true)
            
            friendRepository.searchUsers(query.trim())
                .onSuccess { users ->
                    _uiState.value = _uiState.value.copy(
                        searchResults = users,
                        isSearching = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isSearching = false,
                        errorMessage = exception.message
                    )
                }
        }
    }
    
    fun sendFriendRequest(username: String) {
        viewModelScope.launch {
            friendRepository.sendFriendRequest(username)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        successMessage = "Friend request sent to $username"
                    )
                    // Refresh search to update UI
                    searchUsers(_uiState.value.searchQuery)
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = exception.message
                    )
                }
        }
    }
    
    fun acceptFriendRequest(friendshipId: String) {
        viewModelScope.launch {
            friendRepository.acceptFriendRequest(friendshipId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        successMessage = "Friend request accepted"
                    )
                    loadRequests()
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = exception.message
                    )
                }
        }
    }
    
    fun rejectFriendRequest(friendshipId: String) {
        viewModelScope.launch {
            friendRepository.rejectFriendRequest(friendshipId)
                .onSuccess {
                    loadRequests()
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = exception.message
                    )
                }
        }
    }
    
    fun removeFriend(friendshipId: String) {
        viewModelScope.launch {
            friendRepository.removeFriend(friendshipId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        successMessage = "Friend removed"
                    )
                    loadFriends()
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = exception.message
                    )
                }
        }
    }
    
    fun clearSearch() {
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            searchResults = emptyList()
        )
    }
    
    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
    
    fun startChatWith(friendId: String, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            messagesRepository.createDirectMessageGroup(friendId)
                .onSuccess { group ->
                    onSuccess(group.id)
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = exception.message ?: "Failed to start chat"
                    )
                }
        }
    }
} 