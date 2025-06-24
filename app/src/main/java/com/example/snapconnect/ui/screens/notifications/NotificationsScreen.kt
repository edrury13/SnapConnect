package com.example.snapconnect.ui.screens.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.snapconnect.ui.theme.SnapYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = SnapYellow)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // Notification Types Section
                SectionHeader(title = "Notification Types")
                
                NotificationSwitch(
                    title = "Messages",
                    subtitle = "Get notified when you receive new messages",
                    icon = Icons.Default.Message,
                    checked = uiState.messagesEnabled,
                    onCheckedChange = viewModel::toggleMessagesNotifications
                )
                
                NotificationSwitch(
                    title = "Friend Requests",
                    subtitle = "Get notified when someone adds you as a friend",
                    icon = Icons.Default.PersonAdd,
                    checked = uiState.friendRequestsEnabled,
                    onCheckedChange = viewModel::toggleFriendRequestsNotifications
                )
                
                NotificationSwitch(
                    title = "Stories",
                    subtitle = "Get notified when friends post new stories",
                    icon = Icons.Default.CollectionsBookmark,
                    checked = uiState.storiesEnabled,
                    onCheckedChange = viewModel::toggleStoriesNotifications
                )
                
                Divider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                
                // Notification Preferences Section
                SectionHeader(title = "Notification Preferences")
                
                NotificationSwitch(
                    title = "Sound",
                    subtitle = "Play sound for notifications",
                    icon = Icons.Default.VolumeUp,
                    checked = uiState.soundEnabled,
                    onCheckedChange = viewModel::toggleSound
                )
                
                NotificationSwitch(
                    title = "Vibration",
                    subtitle = "Vibrate for notifications",
                    icon = Icons.Default.Vibration,
                    checked = uiState.vibrationEnabled,
                    onCheckedChange = viewModel::toggleVibration
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Info Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = SnapYellow
                        )
                        Column {
                            Text(
                                text = "System Settings",
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "You may need to enable notifications for SnapConnect in your device settings for these preferences to work.",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun NotificationSwitch(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onCheckedChange(!checked) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SnapYellow,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = SnapYellow,
                    checkedTrackColor = SnapYellow.copy(alpha = 0.5f)
                )
            )
        }
    }
} 