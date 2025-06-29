package com.example.snapconnect.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.snapconnect.data.model.Message
import com.example.snapconnect.data.model.User
import com.example.snapconnect.data.model.MediaType
import com.example.snapconnect.ui.theme.SnapYellow
import com.example.snapconnect.navigation.Screen
import com.example.snapconnect.ui.components.VideoPlayer
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    groupId: String,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    
    // Capture state values to avoid smart cast issues
    val otherUser = uiState.otherUser
    val group = uiState.group
    val members = uiState.members
    val messages = uiState.messages
    val currentUserId = uiState.currentUserId
    
    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }
    
    // Launcher for picking image
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.setGroupAvatar(it) }
    }

    var showMembers by remember { mutableStateOf(false) }

    val sheetState: SheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar
                        val avatarModifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable(enabled = members.size > 2) {
                                pickImageLauncher.launch("image/*")
                            }

                        when {
                            otherUser != null -> {
                                Box(avatarModifier) {
                                    UserAvatar(user = otherUser, size = 40.dp)
                                }
                            }
                            group?.avatarUrl != null -> {
                                AsyncImage(
                                    model = group.avatarUrl,
                                    contentDescription = "Group avatar",
                                    modifier = avatarModifier,
                                    contentScale = ContentScale.Crop
                                )
                            }
                            else -> {
                                Box(
                                    modifier = avatarModifier.background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Group,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        // Name
                        Column(
                            modifier = Modifier.clickable(enabled = members.size > 2) { showMembers = true }
                        ) {
                            Text(
                                text = when {
                                    otherUser != null -> {
                                        otherUser.displayName ?: otherUser.username
                                    }
                                    else -> group?.name ?: "Chat"
                                },
                                fontWeight = FontWeight.Medium
                            )
                            if (members.size > 2) {
                                Text(
                                    text = "${members.size} members",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Chat options */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Options"
                        )
                    }
                }
            )
        },
        bottomBar = {
            ChatInput(
                value = uiState.messageInput,
                onValueChange = viewModel::updateMessageInput,
                onSend = viewModel::sendMessage,
                isSending = uiState.isSending,
                navController = navController,
                groupId = groupId
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = SnapYellow)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    reverseLayout = true,
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(
                        items = messages.reversed(),
                        key = { it.id }
                    ) { message ->
                        val member = members.firstOrNull { it.id == message.senderId }
                        MessageItem(
                            message = message,
                            sender = member,
                            isCurrentUser = message.senderId == currentUserId,
                            showAvatar = members.size > 2 // Show avatars in group chats
                        )
                    }
                    
                    if (messages.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Send a message to start the conversation",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
            
            uiState.errorMessage?.let { error ->
                Snackbar(
                    modifier = Modifier.align(Alignment.TopCenter),
                    action = {
                        TextButton(onClick = viewModel::clearError) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text(error)
                }
            }
        }
    }

    if (showMembers) {
        ModalBottomSheet(sheetState = sheetState, onDismissRequest = { showMembers = false }) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                members.forEach { user ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                        UserAvatar(user = user, size = 36.dp)
                        Spacer(Modifier.width(12.dp))
                        Text(user.displayName ?: user.username)
                    }
                }
            }
        }
    }
}

@Composable
fun MessageItem(
    message: Message,
    sender: User?,
    isCurrentUser: Boolean,
    showAvatar: Boolean
) {
    var showVideoPlayer by remember { mutableStateOf(false) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isCurrentUser && showAvatar) {
            sender?.let {
                UserAvatar(user = it, size = 32.dp)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        
        Column(
            modifier = Modifier.widthIn(max = 280.dp),
            horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start
        ) {
            if (!isCurrentUser && showAvatar && sender != null) {
                Text(
                    text = sender.displayName ?: sender.username,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )
            }
            
            Surface(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isCurrentUser) 16.dp else 4.dp,
                    bottomEnd = if (isCurrentUser) 4.dp else 16.dp
                ),
                color = if (isCurrentUser) SnapYellow else MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    // Media content
                    message.mediaUrl?.let { mediaUrl ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 300.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            when (message.mediaType) {
                                MediaType.VIDEO -> {
                                    if (showVideoPlayer) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .heightIn(min = 200.dp, max = 300.dp)
                                        ) {
                                            VideoPlayer(
                                                videoUrl = mediaUrl,
                                                modifier = Modifier.fillMaxSize(),
                                                shouldPlay = true,
                                                shouldLoop = true
                                            )
                                            // Close button
                                            IconButton(
                                                onClick = { showVideoPlayer = false },
                                                modifier = Modifier
                                                    .align(Alignment.TopEnd)
                                                    .padding(4.dp)
                                                    .size(32.dp)
                                                    .background(
                                                        Color.Black.copy(alpha = 0.5f),
                                                        CircleShape
                                                    )
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "Close video",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    } else {
                                        // Show video thumbnail with play button overlay
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .heightIn(min = 150.dp, max = 300.dp)
                                                .background(Color.Black)
                                                .clickable { showVideoPlayer = true }
                                        ) {
                                            // Video placeholder
                                            Icon(
                                                imageVector = Icons.Default.Videocam,
                                                contentDescription = "Video",
                                                modifier = Modifier
                                                    .size(64.dp)
                                                    .align(Alignment.Center),
                                                tint = Color.White.copy(alpha = 0.3f)
                                            )
                                            
                                            // Play button
                                            Surface(
                                                modifier = Modifier
                                                    .size(48.dp)
                                                    .align(Alignment.Center),
                                                shape = CircleShape,
                                                color = Color.White.copy(alpha = 0.9f)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.PlayArrow,
                                                    contentDescription = "Play video",
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(8.dp),
                                                    tint = Color.Black
                                                )
                                            }
                                        }
                                    }
                                }
                                MediaType.IMAGE -> {
                                    AsyncImage(
                                        model = mediaUrl,
                                        contentDescription = "Image message",
                                        modifier = Modifier.fillMaxWidth(),
                                        contentScale = ContentScale.FillWidth
                                    )
                                }
                                null -> {
                                    // Fallback for unknown media type
                                    AsyncImage(
                                        model = mediaUrl,
                                        contentDescription = "Media message",
                                        modifier = Modifier.fillMaxWidth(),
                                        contentScale = ContentScale.FillWidth
                                    )
                                }
                            }
                        }
                        if (message.content.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    
                    if (message.content.isNotEmpty()) {
                        Text(
                            text = message.content,
                            color = if (isCurrentUser) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = formatTime(message.createdAt),
                        fontSize = 11.sp,
                        color = if (isCurrentUser) 
                            Color.Black.copy(alpha = 0.6f) 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    isSending: Boolean,
    navController: NavController,
    groupId: String
) {
    Surface(
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = { onSend() }),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = SnapYellow,
                    cursorColor = SnapYellow
                ),
                maxLines = 4,
                trailingIcon = {
                    Row {
                        IconButton(onClick = { /* TODO: Attach media */ }) {
                            Icon(
                                imageVector = Icons.Default.AttachFile,
                                contentDescription = "Attach",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(onClick = { 
                            navController.navigate(Screen.Camera.createRoute(groupId))
                        }) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Camera",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FilledIconButton(
                onClick = onSend,
                enabled = value.isNotBlank() && !isSending,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = SnapYellow,
                    contentColor = Color.Black
                )
            ) {
                if (isSending) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }
    }
}

@Composable
fun UserAvatar(
    user: User,
    size: androidx.compose.ui.unit.Dp
) {
    if (user.avatarUrl != null) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = "${user.username}'s avatar",
            modifier = Modifier
                .size(size)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(SnapYellow),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.username.firstOrNull()?.uppercase() ?: "?",
                fontSize = (size.value / 2.5).sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

fun formatTime(instant: Instant): String {
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val hour = localDateTime.hour
    val minute = localDateTime.minute
    val amPm = if (hour >= 12) "PM" else "AM"
    val displayHour = when (hour) {
        0 -> 12
        in 13..23 -> hour - 12
        else -> hour
    }
    return String.format("%d:%02d %s", displayHour, minute, amPm)
} 