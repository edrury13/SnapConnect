package com.example.snapconnect.ui.screens.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapconnect.data.model.User
import com.example.snapconnect.data.repository.FriendRepository
import com.example.snapconnect.data.repository.MessagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val friendRepository: FriendRepository,
    private val messagesRepository: MessagesRepository
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val friends: List<User> = emptyList(),
        val selectedFriends: Set<String> = emptySet(),
        val errorMessage: String? = null,
        val groupCreatedId: String? = null
    )

    private val _uiState = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadFriends()
    }

    fun toggleFriendSelection(userId: String) {
        val current = _uiState.value.selectedFriends.toMutableSet()
        if (current.contains(userId)) current.remove(userId) else current.add(userId)
        _uiState.value = _uiState.value.copy(selectedFriends = current)
    }

    private fun loadFriends() {
        viewModelScope.launch {
            friendRepository.getMyFriends()
                .onSuccess { pairs ->
                    val users = pairs.map { it.second }
                    _uiState.value = _uiState.value.copy(isLoading = false, friends = users)
                }
                .onFailure { err ->
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = err.message)
                }
        }
    }

    fun createGroup(name: String) {
        if (name.isBlank() || _uiState.value.selectedFriends.isEmpty()) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            messagesRepository.createGroup(name, _uiState.value.selectedFriends.toList())
                .onSuccess { group ->
                    _uiState.value = _uiState.value.copy(isLoading = false, groupCreatedId = group.id)
                }
                .onFailure { err ->
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = err.message)
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
} 