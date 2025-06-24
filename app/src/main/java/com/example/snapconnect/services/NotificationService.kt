package com.example.snapconnect.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.snapconnect.MainActivity
import com.example.snapconnect.R
import com.example.snapconnect.ui.screens.notifications.NotificationPreferences
import com.example.snapconnect.ui.screens.notifications.notificationDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationService @Inject constructor(
    private val context: Context
) {
    companion object {
        const val CHANNEL_ID_MESSAGES = "snapconnect_messages"
        const val CHANNEL_ID_FRIEND_REQUESTS = "snapconnect_friend_requests"
        const val CHANNEL_ID_STORIES = "snapconnect_stories"
        
        const val NOTIFICATION_ID_MESSAGE = 1
        const val NOTIFICATION_ID_FRIEND_REQUEST = 2
        const val NOTIFICATION_ID_STORY = 3
    }
    
    init {
        createNotificationChannels()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // Messages channel
            val messagesChannel = NotificationChannel(
                CHANNEL_ID_MESSAGES,
                "Messages",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for new messages"
                enableVibration(true)
            }
            
            // Friend requests channel
            val friendRequestsChannel = NotificationChannel(
                CHANNEL_ID_FRIEND_REQUESTS,
                "Friend Requests",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for friend requests"
            }
            
            // Stories channel
            val storiesChannel = NotificationChannel(
                CHANNEL_ID_STORIES,
                "Stories",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for new stories from friends"
            }
            
            notificationManager.createNotificationChannel(messagesChannel)
            notificationManager.createNotificationChannel(friendRequestsChannel)
            notificationManager.createNotificationChannel(storiesChannel)
        }
    }
    
    suspend fun showMessageNotification(
        senderName: String,
        message: String,
        groupId: String
    ) {
        val preferences = context.notificationDataStore.data.first()
        
        if (preferences[NotificationPreferences.MESSAGES_ENABLED] != false) {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("destination", "chat")
                putExtra("groupId", groupId)
            }
            
            val pendingIntent = PendingIntent.getActivity(
                context,
                groupId.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            
            val notification = NotificationCompat.Builder(context, CHANNEL_ID_MESSAGES)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle(senderName)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .apply {
                    if (preferences[NotificationPreferences.SOUND_ENABLED] != false) {
                        setDefaults(NotificationCompat.DEFAULT_SOUND)
                    }
                    if (preferences[NotificationPreferences.VIBRATION_ENABLED] != false) {
                        setVibrate(longArrayOf(0, 250, 250, 250))
                    }
                }
                .build()
            
            with(NotificationManagerCompat.from(context)) {
                notify(groupId.hashCode(), notification)
            }
        }
    }
    
    suspend fun showFriendRequestNotification(
        requesterName: String,
        requesterId: String
    ) {
        val preferences = context.notificationDataStore.data.first()
        
        if (preferences[NotificationPreferences.FRIEND_REQUESTS_ENABLED] != false) {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("destination", "friends")
                putExtra("tab", "requests")
            }
            
            val pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_ID_FRIEND_REQUEST,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            
            val notification = NotificationCompat.Builder(context, CHANNEL_ID_FRIEND_REQUESTS)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("New Friend Request")
                .setContentText("$requesterName wants to be your friend")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .apply {
                    if (preferences[NotificationPreferences.SOUND_ENABLED] != false) {
                        setDefaults(NotificationCompat.DEFAULT_SOUND)
                    }
                    if (preferences[NotificationPreferences.VIBRATION_ENABLED] != false) {
                        setVibrate(longArrayOf(0, 250, 250, 250))
                    }
                }
                .build()
            
            with(NotificationManagerCompat.from(context)) {
                notify(requesterId.hashCode(), notification)
            }
        }
    }
    
    suspend fun showStoryNotification(
        userName: String,
        userId: String
    ) {
        val preferences = context.notificationDataStore.data.first()
        
        if (preferences[NotificationPreferences.STORIES_ENABLED] != false) {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("destination", "home")
            }
            
            val pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_ID_STORY,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            
            val notification = NotificationCompat.Builder(context, CHANNEL_ID_STORIES)
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setContentTitle("New Story")
                .setContentText("$userName posted a new story")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .apply {
                    if (preferences[NotificationPreferences.SOUND_ENABLED] != false) {
                        setDefaults(NotificationCompat.DEFAULT_SOUND)
                    }
                    if (preferences[NotificationPreferences.VIBRATION_ENABLED] != false) {
                        setVibrate(longArrayOf(0, 250, 250, 250))
                    }
                }
                .build()
            
            with(NotificationManagerCompat.from(context)) {
                notify(userId.hashCode(), notification)
            }
        }
    }
} 