package com.example.snapconnect.ui.screens.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.model.Group
import com.example.snapconnect.data.model.Message
import com.example.snapconnect.data.model.User
import com.example.snapconnect.data.repository.AuthRepository
import com.example.snapconnect.data.repository.MessagesRepository
import com.example.snapconnect.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConversationItem(
    val group: Group,
    val lastMessage: Message?,
    val otherUser: User?, // For direct messages
    val memberCount: Int
)

data class MessagesUiState(
    val isLoading: Boolean = false,
    val conversations: List<ConversationItem> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val messagesRepository: MessagesRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MessagesUiState())
    val uiState: StateFlow<MessagesUiState> = _uiState.asStateFlow()
    
    init {
        loadConversations()
        observeRealtimeUpdates()
    }
    
    private fun loadConversations() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val currentUserId = authRepository.getCurrentUser()?.id ?: return@launch
            
            messagesRepository.getMyGroups()
                .onSuccess { groupsWithMessages ->
                    val conversationItems = groupsWithMessages.map { (group, lastMessage) ->
                        // Get group members
                        val membersResult = messagesRepository.getGroupMembers(group)
                        val members = membersResult.getOrDefault(emptyList())
                        
                        // For direct messages, find the other user
                        val otherUser = if (group.memberIds.size == 2) {
                            members.firstOrNull { it.id != currentUserId }
                        } else null
                        
                        ConversationItem(
                            group = group,
                            lastMessage = lastMessage,
                            otherUser = otherUser,
                            memberCount = members.size
                        )
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        conversations = conversationItems,
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
    
    private fun observeRealtimeUpdates() {
        viewModelScope.launch {
            messagesRepository.getGroupsRealtime()
                .collect { groupsWithMessages ->
                    val currentUserId = authRepository.getCurrentUser()?.id ?: return@collect
                    
                    val conversationItems = groupsWithMessages.map { (group, lastMessage) ->
                        // Get group members
                        val membersResult = messagesRepository.getGroupMembers(group)
                        val members = membersResult.getOrDefault(emptyList())
                        
                        // For direct messages, find the other user
                        val otherUser = if (group.memberIds.size == 2) {
                            members.firstOrNull { it.id != currentUserId }
                        } else null
                        
                        ConversationItem(
                            group = group,
                            lastMessage = lastMessage,
                            otherUser = otherUser,
                            memberCount = members.size
                        )
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        conversations = conversationItems
                    )
                }
        }
    }
    
    fun refresh() {
        loadConversations()
    }
} 