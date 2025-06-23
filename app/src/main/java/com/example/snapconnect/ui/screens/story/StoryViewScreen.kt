package com.example.snapconnect.ui.screens.story

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.snapconnect.data.model.MediaType
import com.example.snapconnect.ui.screens.home.getTimeAgo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StoryViewScreen(
    navController: NavController,
    viewModel: StoryViewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    
    // Auto-advance timer
    var progress by remember { mutableStateOf(0f) }
    var isPaused by remember { mutableStateOf(false) }
    
    // Handle story navigation
    LaunchedEffect(uiState.currentStory) {
        val story = uiState.currentStory
        if (story != null && !isPaused) {
            progress = 0f
            val duration = if (story.mediaType == MediaType.VIDEO) 10000L else 5000L
            
            while (progress < 1f && !isPaused) {
                delay(50)
                progress += 50f / duration
                
                if (progress >= 1f) {
                    if (viewModel.hasNextStory()) {
                        viewModel.nextStory()
                    } else {
                        navController.navigateUp()
                    }
                    break
                }
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (uiState.currentStory != null && uiState.currentUser != null) {
            // Story content
            val currentStory = uiState.currentStory!!
            val currentUser = uiState.currentUser!!
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPaused = true
                                tryAwaitRelease()
                                isPaused = false
                            },
                            onTap = { offset ->
                                val screenWidth = size.width
                                when {
                                    offset.x < screenWidth * 0.3f -> {
                                        // Left side - previous story
                                        if (viewModel.hasPreviousStory()) {
                                            viewModel.previousStory()
                                        }
                                    }
                                    offset.x > screenWidth * 0.7f -> {
                                        // Right side - next story
                                        if (viewModel.hasNextStory()) {
                                            viewModel.nextStory()
                                        } else {
                                            navController.navigateUp()
                                        }
                                    }
                                }
                            }
                        )
                    }
            ) {
                // Media display
                if (currentStory.mediaType == MediaType.VIDEO) {
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
                        model = currentStory.mediaUrl,
                        contentDescription = "Story image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
                
                // Gradient overlay for better text visibility
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.7f),
                                    Color.Transparent
                                )
                            )
                        )
                )
                
                // Progress indicators
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    uiState.allUserStories.forEachIndexed { index, _ ->
                        LinearProgressIndicator(
                            progress = when {
                                index < uiState.currentIndex -> 1f
                                index == uiState.currentIndex -> progress
                                else -> 0f
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(2.dp)
                                .clip(RoundedCornerShape(1.dp)),
                            color = Color.White,
                            trackColor = Color.White.copy(alpha = 0.3f)
                        )
                    }
                }
                
                // Header with user info
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 40.dp)
                        .align(Alignment.TopCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // User avatar
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    ) {
                        if (currentUser.avatarUrl != null) {
                            AsyncImage(
                                model = currentUser.avatarUrl,
                                contentDescription = "${currentUser.username}'s avatar",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentUser.username.firstOrNull()?.uppercase() ?: "?",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    // Username and time
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = currentUser.displayName ?: currentUser.username,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = getTimeAgo(currentStory.createdAt),
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                    
                    // Close button
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }
                
                // Caption
                currentStory.caption?.let { caption ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(
                                Color.Black.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            text = caption,
                            color = Color.White,
                            fontSize = 14.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                
                // Options menu (for own stories)
                if (currentStory.userId == viewModel.getCurrentUserId()) {
                    var showMenu by remember { mutableStateOf(false) }
                    
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 80.dp, end = 16.dp)
                    ) {
                        IconButton(
                            onClick = { showMenu = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Options",
                                tint = Color.White
                            )
                        }
                        
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete Story") },
                                onClick = {
                                    showMenu = false
                                    scope.launch {
                                        viewModel.deleteStory(currentStory.id)
                                        if (uiState.currentStory == null) {
                                            navController.navigateUp()
                                        }
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("View Stats") },
                                onClick = {
                                    showMenu = false
                                    // TODO: Show viewer list
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Analytics,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                }
            }
        } else if (uiState.errorMessage != null) {
            // Error state
            val errorMessage = uiState.errorMessage!!
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.navigateUp() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Go Back")
                }
            }
        }
    }
} 