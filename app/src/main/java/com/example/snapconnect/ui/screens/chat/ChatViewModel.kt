package com.example.snapconnect.ui.screens.chat

import androidx.lifecycle.SavedStateHandle
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
import kotlinx.datetime.Clock
import java.util.UUID
import javax.inject.Inject

data class ChatUiState(
    val isLoading: Boolean = false,
    val isSending: Boolean = false,
    val messages: List<Message> = emptyList(),
    val members: List<User> = emptyList(),
    val group: Group? = null,
    val otherUser: User? = null, // For direct messages
    val currentUserId: String = "",
    val messageInput: String = "",
    val errorMessage: String? = null
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messagesRepository: MessagesRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val groupId: String = savedStateHandle.get<String>("groupId") ?: ""
    
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    
    init {
        loadChatData()
        observeMessages()
    }
    
    private fun loadChatData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                currentUserId = authRepository.getCurrentUser()?.id ?: ""
            )
            
            // Load group info
            loadGroupInfo()
            
            // Load initial messages
            messagesRepository.getMessages(groupId)
                .onSuccess { messages ->
                    _uiState.value = _uiState.value.copy(
                        messages = messages,
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
    
    private suspend fun loadGroupInfo() {
        // Get group details from cache or fetch
        messagesRepository.getMyGroups()
            .onSuccess { groups ->
                val group = groups.firstOrNull { it.first.id == groupId }?.first
                if (group != null) {
                    _uiState.value = _uiState.value.copy(group = group)
                    
                    // Load members
                    messagesRepository.getGroupMembers(group)
                        .onSuccess { members ->
                            _uiState.value = _uiState.value.copy(members = members)
                            
                            // For direct messages, identify the other user
                            if (group.memberIds.size == 2) {
                                val otherUser = members.firstOrNull { 
                                    it.id != _uiState.value.currentUserId 
                                }
                                _uiState.value = _uiState.value.copy(otherUser = otherUser)
                            }
                        }
                }
            }
    }
    
    private fun observeMessages() {
        viewModelScope.launch {
            messagesRepository.getMessagesRealtime(groupId)
                .collect { messages ->
                    // Filter out any temporary messages that might have been replaced
                    val currentMessages = _uiState.value.messages
                    val tempMessageIds = currentMessages
                        .filter { it.id.startsWith("temp_") }
                        .map { it.id }
                    
                    // Keep temp messages that don't have a corresponding real message yet
                    val tempMessagesToKeep = currentMessages.filter { tempMsg ->
                        tempMsg.id.startsWith("temp_") && 
                        !messages.any { realMsg -> 
                            realMsg.senderId == tempMsg.senderId && 
                            realMsg.content == tempMsg.content &&
                            realMsg.createdAt.epochSeconds - tempMsg.createdAt.epochSeconds < 5
                        }
                    }
                    
                    // Combine real messages with any remaining temp messages
                    val combinedMessages = messages + tempMessagesToKeep
                    
                    _uiState.value = _uiState.value.copy(
                        messages = combinedMessages.sortedBy { it.createdAt }
                    )
                }
        }
    }
    
    fun updateMessageInput(text: String) {
        _uiState.value = _uiState.value.copy(messageInput = text)
    }
    
    fun sendMessage() {
        val content = _uiState.value.messageInput.trim()
        if (content.isEmpty()) return
        
        val currentUserId = _uiState.value.currentUserId
        if (currentUserId.isEmpty()) return
        
        // Create a temporary message with a temp ID
        val tempId = "temp_${UUID.randomUUID()}"
        val tempMessage = Message(
            id = tempId,
            groupId = groupId,
            senderId = currentUserId,
            content = content,
            mediaUrl = null,
            mediaType = null,
            createdAt = Clock.System.now()
        )
        
        // Add the temporary message to the UI immediately (optimistic update)
        _uiState.value = _uiState.value.copy(
            messages = _uiState.value.messages + tempMessage,
            messageInput = "", // Clear input immediately
            isSending = true
        )
        
        viewModelScope.launch {
            messagesRepository.sendMessage(
                groupId = groupId,
                content = content
            )
                .onSuccess { sentMessage ->
                    // Replace temporary message with the real one
                    _uiState.value = _uiState.value.copy(
                        messages = _uiState.value.messages.map { msg ->
                            if (msg.id == tempId) sentMessage else msg
                        },
                        isSending = false
                    )
                }
                .onFailure { exception ->
                    // Remove the temporary message on failure
                    _uiState.value = _uiState.value.copy(
                        messages = _uiState.value.messages.filter { it.id != tempId },
                        isSending = false,
                        errorMessage = exception.message,
                        messageInput = content // Restore input on failure
                    )
                }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
} 