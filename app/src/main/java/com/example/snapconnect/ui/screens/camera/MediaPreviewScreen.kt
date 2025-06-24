package com.example.snapconnect.ui.screens.camera

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.snapconnect.navigation.Screen
import com.example.snapconnect.ui.theme.SnapYellow
import com.example.snapconnect.ui.components.VideoPlayer
import com.example.snapconnect.data.model.User
import com.example.snapconnect.data.repository.FriendRepository
import com.example.snapconnect.data.repository.FiltersRepository
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.example.snapconnect.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaPreviewScreen(
    navController: NavController,
    mediaUri: String,
    isVideo: Boolean = false,
    groupId: String? = null,
    filterId: String? = null,
    viewModel: StoryPostViewModel = hiltViewModel(),
    sendViewModel: MediaSendViewModel = hiltViewModel()
) {
    var caption by remember { mutableStateOf("") }
    var showSendOptions by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val uri = remember { Uri.parse(mediaUri) }
    val uiState by viewModel.uiState.collectAsState()
    val sendUiState by sendViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    
    // Get the frame overlay if filterId is "frame"
    val frameOverlay = remember(filterId) {
        if (filterId == "frame" && isVideo) {
            try {
                ImageBitmap.imageResource(context.resources, R.drawable.frame)
            } catch (e: Exception) {
                null
            }
        } else null
    }
    
    // If we have a groupId, we're sending to a specific chat
    val isChatMode = !groupId.isNullOrEmpty()
    
    // Handle navigation when story is posted successfully
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            if (isChatMode && !groupId.isNullOrEmpty()) {
                navController.navigate(Screen.Chat.createRoute(groupId)) {
                    popUpTo(Screen.Camera.route) { inclusive = true }
                }
            } else {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Camera.route) { inclusive = true }
                }
            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Media preview
        if (isVideo) {
            Box(modifier = Modifier.fillMaxSize()) {
                VideoPlayer(
                    videoUrl = uri.toString(),
                    modifier = Modifier.fillMaxSize(),
                    shouldPlay = true,
                    shouldLoop = true
                )
                
                // Frame overlay for videos
                frameOverlay?.let { frame ->
                    Canvas(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        drawImage(
                            image = frame,
                            dstSize = androidx.compose.ui.unit.IntSize(
                                size.width.toInt(),
                                size.height.toInt()
                            )
                        )
                    }
                }
            }
        } else {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(uri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Captured photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        
        // Loading overlay
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = SnapYellow,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f)),
                enabled = !uiState.isLoading
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
            
            Row {
                // Download button
                IconButton(
                    onClick = { /* TODO: Save to device */ },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f)),
                    enabled = !uiState.isLoading
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download",
                        tint = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Add text button
                IconButton(
                    onClick = { /* TODO: Add text overlay */ },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f)),
                    enabled = !uiState.isLoading
                ) {
                    Icon(
                        imageVector = Icons.Default.TextFields,
                        contentDescription = "Add Text",
                        tint = Color.White
                    )
                }
            }
        }
        
        // Bottom controls
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(16.dp)
        ) {
            // Caption input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.9f))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = caption,
                    onValueChange = { caption = it },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    decorationBox = { innerTextField ->
                        if (caption.isEmpty()) {
                            Text(
                                text = "Add a caption...",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    },
                    cursorBrush = SolidColor(SnapYellow),
                    enabled = !uiState.isLoading
                )
                
                if (caption.isNotEmpty() && !uiState.isLoading) {
                    IconButton(
                        onClick = { caption = "" },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Send options
            if (isChatMode && !groupId.isNullOrEmpty()) {
                // In chat mode, show only send button
                Button(
                    onClick = {
                        viewModel.sendToChat(uri, isVideo, caption, groupId, filterId)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SnapYellow,
                        contentColor = Color.Black
                    )
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.Black,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Send")
                    }
                }
            } else {
                // Normal mode - show all options
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Send to friends
                    SendOptionButton(
                        icon = Icons.Default.Send,
                        label = "Send to",
                        onClick = { showSendOptions = true },
                        enabled = !uiState.isLoading
                    )
                    
                    // Add to story
                    SendOptionButton(
                        icon = Icons.Default.AddCircle,
                        label = "My Story",
                        onClick = { 
                            viewModel.postStory(uri, isVideo, caption, filterId)
                        },
                        enabled = !uiState.isLoading
                    )
                    
                    // Save to memories (future feature)
                    SendOptionButton(
                        icon = Icons.Default.BookmarkBorder,
                        label = "Memories",
                        onClick = { /* TODO: Save to memories */ },
                        enabled = !uiState.isLoading
                    )
                }
            }
        }
        
        // Error handling
        uiState.errorMessage?.let { error ->
            Snackbar(
                modifier = Modifier.align(Alignment.BottomCenter),
                action = {
                    TextButton(onClick = { viewModel.clearState() }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text(error)
            }
        }
        
        // Send options bottom sheet
        if (showSendOptions) {
            ModalBottomSheet(
                onDismissRequest = { showSendOptions = false },
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                SendOptionsContent(
                    uiState = sendUiState,
                    onToggleFriend = sendViewModel::toggleFriendSelection,
                    onSend = {
                        sendViewModel.sendToSelectedFriends(uri, isVideo, caption, filterId)
                        showSendOptions = false
                    },
                    onCancel = { showSendOptions = false }
                )
            }
        }
        
        // Handle send success
        LaunchedEffect(sendUiState.successCount, sendUiState.totalCount, sendUiState.isSending) {
            if (!sendUiState.isSending && sendUiState.successCount > 0 && sendUiState.successCount == sendUiState.totalCount) {
                navController.navigate(Screen.Messages.route) {
                    popUpTo(Screen.Camera.route) { inclusive = true }
                }
            }
        }
    }
}

@Composable
private fun SendOptionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledIconButton(
            onClick = onClick,
            modifier = Modifier.size(56.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = if (enabled) SnapYellow else SnapYellow.copy(alpha = 0.5f)
            ),
            enabled = enabled
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (enabled) Color.Black else Color.Black.copy(alpha = 0.5f)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (enabled) Color.White else Color.White.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun SendOptionsContent(
    uiState: MediaSendUiState,
    onToggleFriend: (String) -> Unit,
    onSend: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Send To",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = SnapYellow)
                }
            }
            uiState.friends.isEmpty() -> {
                Text(
                    text = "No friends to send to. Add friends first!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.friends) { friend ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onToggleFriend(friend.id)
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Checkbox
                            Checkbox(
                                checked = uiState.selectedFriends.contains(friend.id),
                                onCheckedChange = { _ ->
                                    onToggleFriend(friend.id)
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = SnapYellow,
                                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            // Avatar
                            if (friend.avatarUrl != null) {
                                AsyncImage(
                                    model = friend.avatarUrl,
                                    contentDescription = "${friend.username}'s avatar",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(SnapYellow),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = friend.username.firstOrNull()?.uppercase() ?: "?",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            // Name
                            Text(
                                text = friend.displayName ?: friend.username,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onSend,
                enabled = uiState.selectedFriends.isNotEmpty() && !uiState.isSending,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SnapYellow,
                    contentColor = Color.Black
                )
            ) {
                if (uiState.isSending) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Send (${uiState.selectedFriends.size})")
                }
            }
        }
        
        // Show progress
        if (uiState.isSending && uiState.totalCount > 0) {
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = uiState.successCount.toFloat() / uiState.totalCount,
                modifier = Modifier.fillMaxWidth(),
                color = SnapYellow
            )
            Text(
                text = "Sending... ${uiState.successCount}/${uiState.totalCount}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
} 