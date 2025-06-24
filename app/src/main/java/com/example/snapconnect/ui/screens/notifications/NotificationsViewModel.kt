package com.example.snapconnect.ui.screens.notifications

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

// DataStore extension
val Context.notificationDataStore: DataStore<Preferences> by preferencesDataStore(name = "notification_settings")

// Preference keys
object NotificationPreferences {
    val MESSAGES_ENABLED = booleanPreferencesKey("messages_notifications_enabled")
    val FRIEND_REQUESTS_ENABLED = booleanPreferencesKey("friend_requests_notifications_enabled")
    val STORIES_ENABLED = booleanPreferencesKey("stories_notifications_enabled")
    val SOUND_ENABLED = booleanPreferencesKey("notification_sound_enabled")
    val VIBRATION_ENABLED = booleanPreferencesKey("notification_vibration_enabled")
}

data class NotificationsUiState(
    val isLoading: Boolean = true,
    val messagesEnabled: Boolean = true,
    val friendRequestsEnabled: Boolean = true,
    val storiesEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
)

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()
    
    init {
        loadNotificationSettings()
    }
    
    private fun loadNotificationSettings() {
        viewModelScope.launch {
            context.notificationDataStore.data
                .map { preferences ->
                    NotificationsUiState(
                        isLoading = false,
                        messagesEnabled = preferences[NotificationPreferences.MESSAGES_ENABLED] ?: true,
                        friendRequestsEnabled = preferences[NotificationPreferences.FRIEND_REQUESTS_ENABLED] ?: true,
                        storiesEnabled = preferences[NotificationPreferences.STORIES_ENABLED] ?: true,
                        soundEnabled = preferences[NotificationPreferences.SOUND_ENABLED] ?: true,
                        vibrationEnabled = preferences[NotificationPreferences.VIBRATION_ENABLED] ?: true
                    )
                }
                .collect { state ->
                    _uiState.value = state
                }
        }
    }
    
    fun toggleMessagesNotifications(enabled: Boolean) {
        viewModelScope.launch {
            context.notificationDataStore.edit { preferences ->
                preferences[NotificationPreferences.MESSAGES_ENABLED] = enabled
            }
        }
    }
    
    fun toggleFriendRequestsNotifications(enabled: Boolean) {
        viewModelScope.launch {
            context.notificationDataStore.edit { preferences ->
                preferences[NotificationPreferences.FRIEND_REQUESTS_ENABLED] = enabled
            }
        }
    }
    
    fun toggleStoriesNotifications(enabled: Boolean) {
        viewModelScope.launch {
            context.notificationDataStore.edit { preferences ->
                preferences[NotificationPreferences.STORIES_ENABLED] = enabled
            }
        }
    }
    
    fun toggleSound(enabled: Boolean) {
        viewModelScope.launch {
            context.notificationDataStore.edit { preferences ->
                preferences[NotificationPreferences.SOUND_ENABLED] = enabled
            }
        }
    }
    
    fun toggleVibration(enabled: Boolean) {
        viewModelScope.launch {
            context.notificationDataStore.edit { preferences ->
                preferences[NotificationPreferences.VIBRATION_ENABLED] = enabled
            }
        }
    }
} 