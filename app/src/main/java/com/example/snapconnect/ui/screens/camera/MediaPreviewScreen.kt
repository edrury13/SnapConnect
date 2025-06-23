package com.example.snapconnect.ui.screens.camera

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaPreviewScreen(
    navController: NavController,
    mediaUri: String,
    isVideo: Boolean = false,
    viewModel: StoryPostViewModel = hiltViewModel()
) {
    var caption by remember { mutableStateOf("") }
    var showSendOptions by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val uri = remember { Uri.parse(mediaUri) }
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    
    // Handle navigation when story is posted successfully
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Camera.route) { inclusive = true }
            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Media preview
        if (isVideo) {
            // TODO: Implement video player
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Video",
                    modifier = Modifier.size(80.dp),
                    tint = Color.White
                )
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
                        viewModel.postStory(uri, isVideo, caption)
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
                    onSendToFriends = { selectedFriends ->
                        // TODO: Send to selected friends
                        showSendOptions = false
                    },
                    onSendToGroup = { selectedGroup ->
                        // TODO: Send to group
                        showSendOptions = false
                    },
                    onCancel = { showSendOptions = false }
                )
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
    onSendToFriends: (List<String>) -> Unit,
    onSendToGroup: (String) -> Unit,
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
        
        // TODO: Implement friend list and group list
        Text(
            text = "Friend and group selection coming soon!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(vertical = 32.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onSendToFriends(emptyList()) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SnapYellow,
                    contentColor = Color.Black
                )
            ) {
                Text("Send")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
} 