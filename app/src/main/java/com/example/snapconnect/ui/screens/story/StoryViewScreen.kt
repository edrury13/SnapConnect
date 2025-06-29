package com.example.snapconnect.ui.screens.story

import androidx.compose.animation.core.*
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ThumbDownOffAlt
import androidx.compose.material.icons.outlined.ThumbUpOffAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.LocalTextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.snapconnect.data.model.MediaType
import com.example.snapconnect.data.model.ReactionType
import com.example.snapconnect.ui.screens.home.getTimeAgo
import com.example.snapconnect.ui.components.VideoPlayer
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
    var isVideoPlaying by remember { mutableStateOf(true) }
    var autoAdvanceEnabled by remember { mutableStateOf(true) }
    
    // Toggle for AI caption
    var showAiCaption by remember { mutableStateOf(false) }
    
    // Friend dialog state
    var showFriendDialog by remember { mutableStateOf(false) }
    
    // Handle story navigation
    LaunchedEffect(uiState.currentStory, autoAdvanceEnabled) {
        val story = uiState.currentStory
        if (story != null && !isPaused && autoAdvanceEnabled) {
            progress = 0f
            // For videos, we'll wait for the video to end instead of using a timer
            if (story.mediaType == MediaType.IMAGE) {
                val duration = 8000L // Increased from 5 seconds to 8 seconds
                
                while (progress < 1f && !isPaused && autoAdvanceEnabled) {
                    delay(50)
                    progress += 50f / duration
                    
                    if (progress >= 1f && autoAdvanceEnabled) {
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
                                isVideoPlaying = false
                                tryAwaitRelease()
                                isPaused = false
                                isVideoPlaying = true
                            },
                            onTap = { offset ->
                                val screenWidth = size.width
                                when {
                                    offset.x < screenWidth * 0.3f -> {
                                        // Left side - previous story
                                        if (viewModel.hasPreviousStory()) {
                                            isVideoPlaying = true
                                            viewModel.previousStory()
                                        }
                                    }
                                    offset.x > screenWidth * 0.7f -> {
                                        // Right side - next story
                                        if (viewModel.hasNextStory()) {
                                            isVideoPlaying = true
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
                    VideoPlayer(
                        videoUrl = currentStory.mediaUrl,
                        modifier = Modifier.fillMaxSize(),
                        shouldPlay = !isPaused && isVideoPlaying,
                        onVideoEnd = {
                            if (autoAdvanceEnabled) {
                                if (viewModel.hasNextStory()) {
                                    viewModel.nextStory()
                                } else {
                                    navController.navigateUp()
                                }
                            }
                        }
                    )
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
                    uiState.allUserStories.forEachIndexed { index, story ->
                        LinearProgressIndicator(
                            progress = when {
                                index < uiState.currentIndex -> 1f
                                index == uiState.currentIndex -> {
                                    // For videos, show a simple loading state instead of progress
                                    if (story.mediaType == MediaType.VIDEO || !autoAdvanceEnabled) {
                                        0f
                                    } else {
                                        progress
                                    }
                                }
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
                    // User avatar (clickable for friend request)
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .then(
                                if (currentStory.userId != viewModel.getCurrentUserId()) {
                                    Modifier
                                        .border(2.dp, Color.White, CircleShape)
                                        .clickable { showFriendDialog = true }
                                } else {
                                    Modifier
                                }
                            )
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
                    
                    // Auto-advance toggle
                    IconButton(
                        onClick = { autoAdvanceEnabled = !autoAdvanceEnabled }
                    ) {
                        Icon(
                            imageVector = if (autoAdvanceEnabled) Icons.Default.PlayArrow else Icons.Default.Pause,
                            contentDescription = if (autoAdvanceEnabled) "Auto-advance on" else "Auto-advance off",
                            tint = if (autoAdvanceEnabled) Color.White else Color.White.copy(alpha = 0.6f)
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
                
                // Caption and Comments Button
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    // Comments preview (show first 2-3 comments)
                    if (uiState.comments.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.Black.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            // Show up to 3 most recent comments
                            val previewComments = uiState.comments.take(3)
                            
                            previewComments.forEach { (comment, user) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Small user avatar
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(CircleShape)
                                    ) {
                                        if (user.avatarUrl != null) {
                                            AsyncImage(
                                                model = user.avatarUrl,
                                                contentDescription = "${user.username}'s avatar",
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
                                                    text = user.username.firstOrNull()?.uppercase() ?: "?",
                                                    fontSize = 8.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                                )
                                            }
                                        }
                                    }
                                    
                                    // Comment text
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = user.displayName ?: user.username,
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = comment.text,
                                            color = Color.White.copy(alpha = 0.9f),
                                            fontSize = 12.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                            
                            // Show "View all X comments" if there are more
                            if (uiState.comments.size > 3) {
                                Text(
                                    text = "View all ${uiState.comments.size} comments",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .clickable { viewModel.toggleComments() }
                                )
                            }
                        }
                    }
                    
                    // Reaction and Comment buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Debug log reaction state
                        LaunchedEffect(currentStory.userReaction) {
                            println("DEBUG UI: Story ${currentStory.id} reaction changed to: ${currentStory.userReaction}")
                        }
                        
                        // Like button
                        val isLiked = remember(currentStory.userReaction) { 
                            currentStory.userReaction == ReactionType.LIKE 
                        }
                        val likeScale by animateFloatAsState(
                            targetValue = if (isLiked) 1.1f else 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "likeScale"
                        )
                        
                        Box(
                            modifier = Modifier
                                .scale(likeScale)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (isLiked)
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    else
                                        Color.Black.copy(alpha = 0.5f)
                                )
                                .clickable(enabled = !uiState.isProcessingReaction) { 
                                    viewModel.toggleReaction(ReactionType.LIKE) 
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = if (isLiked) 
                                        Icons.Filled.ThumbUp 
                                    else 
                                        Icons.Outlined.ThumbUpOffAlt,
                                    contentDescription = "Like",
                                    modifier = Modifier.size(20.dp),
                                    tint = if (isLiked)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        Color.White
                                )
                                if (currentStory.likesCount > 0) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${currentStory.likesCount}",
                                        color = if (isLiked)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = if (isLiked)
                                            FontWeight.Bold
                                        else
                                            FontWeight.Normal
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Dislike button
                        val isDisliked = remember(currentStory.userReaction) { 
                            currentStory.userReaction == ReactionType.DISLIKE 
                        }
                        val dislikeScale by animateFloatAsState(
                            targetValue = if (isDisliked) 1.1f else 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "dislikeScale"
                        )
                        
                        Box(
                            modifier = Modifier
                                .scale(dislikeScale)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (isDisliked)
                                        MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
                                    else
                                        Color.Black.copy(alpha = 0.5f)
                                )
                                .clickable(enabled = !uiState.isProcessingReaction) { 
                                    viewModel.toggleReaction(ReactionType.DISLIKE) 
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = if (isDisliked) 
                                        Icons.Filled.ThumbDown 
                                    else 
                                        Icons.Outlined.ThumbDownOffAlt,
                                    contentDescription = "Dislike",
                                    modifier = Modifier.size(20.dp),
                                    tint = if (isDisliked)
                                        MaterialTheme.colorScheme.error
                                    else
                                        Color.White
                                )
                                if (currentStory.dislikesCount > 0) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${currentStory.dislikesCount}",
                                        color = if (isDisliked)
                                            MaterialTheme.colorScheme.error
                                        else
                                            Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = if (isDisliked)
                                            FontWeight.Bold
                                        else
                                            FontWeight.Normal
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Comment button with count
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.Black.copy(alpha = 0.5f))
                                .clickable { viewModel.toggleComments() }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ModeComment,
                                    contentDescription = "Comments",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.White
                                )
                                if (uiState.comments.isNotEmpty()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${uiState.comments.size}",
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                        
                        // See similar art button (only if story has style tags)
                        if (currentStory.styleTags.isNotEmpty()) {
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .clickable { 
                                        // Navigate to style gallery with the first style tag
                                        navController.navigate("style_gallery/${currentStory.styleTags.first()}")
                                    }
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Palette,
                                        contentDescription = "See similar art",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Similar",
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                    
                    // Caption (user or AI)
                    val captionToShow = if (showAiCaption && currentStory.aiCaption != null) {
                        currentStory.aiCaption
                    } else {
                        currentStory.caption
                    }

                    if (currentStory.caption != null || currentStory.aiCaption != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.Black.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                )
                                .padding(16.dp)
                        ) {
                            if (captionToShow != null) {
                                Text(
                                    text = captionToShow,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            // Toggle button (if AI caption exists)
                            if (currentStory.aiCaption != null) {
                                IconButton(
                                    onClick = { showAiCaption = !showAiCaption },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        imageVector = if (showAiCaption) Icons.Default.Translate else Icons.Default.GTranslate,
                                        contentDescription = if (showAiCaption) "Show user caption" else "Show AI caption",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
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
                
                // Comments Bottom Sheet
                if (uiState.showComments) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable(
                                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                                indication = null
                            ) {
                                viewModel.toggleComments()
                            }
                    )
                    
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.7f)
                            .align(Alignment.BottomCenter)
                            .background(
                                MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                            )
                            .clickable(
                                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                                indication = null
                            ) {
                                // Prevent closing when clicking on the sheet
                            }
                    ) {
                        // Comments header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Comments",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = { viewModel.toggleComments() }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close comments"
                                )
                            }
                        }
                        
                        // Replace Divider with Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                        
                        // Comments list
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(uiState.comments) { (comment, user) ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // User avatar
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                    ) {
                                        if (user.avatarUrl != null) {
                                            AsyncImage(
                                                model = user.avatarUrl,
                                                contentDescription = "${user.username}'s avatar",
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
                                                    text = user.username.firstOrNull()?.uppercase() ?: "?",
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                                )
                                            }
                                        }
                                    }
                                    
                                    // Comment content
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Text(
                                                text = user.displayName ?: user.username,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 14.sp
                                            )
                                            Text(
                                                text = getTimeAgo(comment.createdAt),
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Text(
                                            text = comment.text,
                                            fontSize = 14.sp,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                    
                                    // Delete button (for own comments)
                                    if (comment.userId == viewModel.getCurrentUserId()) {
                                        IconButton(
                                            onClick = { viewModel.deleteComment(comment.id) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete comment",
                                                modifier = Modifier.size(16.dp),
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                            
                            if (uiState.comments.isEmpty()) {
                                item {
                                    Text(
                                        text = "No comments yet. Be the first to comment!",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(vertical = 32.dp)
                                    )
                                }
                            }
                        }
                        
                        // Replace Divider with Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                        
                        // Comment input
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Custom text field to avoid experimental APIs
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .background(
                                        MaterialTheme.colorScheme.surface,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                BasicTextField(
                                    value = uiState.commentInput,
                                    onValueChange = viewModel::updateCommentInput,
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    textStyle = LocalTextStyle.current.copy(
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                                if (uiState.commentInput.isEmpty()) {
                                    Text(
                                        text = "Add a comment...",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            
                            IconButton(
                                onClick = {
                                    viewModel.sendComment()
                                },
                                enabled = uiState.commentInput.isNotBlank() && !uiState.isAddingComment,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (uiState.commentInput.isNotBlank() && !uiState.isAddingComment)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.surfaceVariant
                                    )
                            ) {
                                if (uiState.isAddingComment) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        contentDescription = "Send comment",
                                        tint = if (uiState.commentInput.isNotBlank())
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Friend Request Dialog
                if (showFriendDialog) {
                    AlertDialog(
                        onDismissRequest = { showFriendDialog = false },
                        title = { 
                            Text(
                                text = "Add Friend",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        },
                        text = { 
                            Text(
                                text = "Send friend request to ${currentUser.displayName ?: currentUser.username}?",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    viewModel.sendFriendRequest(currentStory.userId)
                                    showFriendDialog = false
                                }
                            ) {
                                Text("Send Request")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showFriendDialog = false }
                            ) {
                                Text("Cancel")
                            }
                        }
                    )
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
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { navController.navigateUp() }
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Text(
                        "Go Back",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
} 