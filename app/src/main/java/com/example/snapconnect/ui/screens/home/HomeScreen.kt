package com.example.snapconnect.ui.screens.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.snapconnect.data.model.MediaType
import com.example.snapconnect.data.model.Story
import com.example.snapconnect.data.model.User
import com.example.snapconnect.navigation.Screen
import com.example.snapconnect.ui.components.SnapConnectBottomBarWithFAB
import com.example.snapconnect.ui.theme.SnapBlue
import com.example.snapconnect.ui.theme.SnapRed
import com.example.snapconnect.ui.theme.SnapYellow
import com.example.snapconnect.ui.theme.SnapBlueAccent
import com.example.snapconnect.ui.theme.SnapPurple
import com.example.snapconnect.ui.theme.SnapPink
import com.example.snapconnect.ui.theme.WarmGray400
import com.example.snapconnect.ui.theme.StoryBorderGradient
import com.example.snapconnect.ui.theme.SnapBlack
import com.example.snapconnect.ui.theme.WarmGray300
import com.example.snapconnect.ui.theme.GlassWhite
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Refresh stories every time the screen is navigated to
    LaunchedEffect(Unit) {
        viewModel.loadStories()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "SnapConnect",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SnapYellow,
                    titleContentColor = SnapBlack
                ),
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screen.Inspiration.route) }
                    ) {
                        Box {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "AI Inspiration",
                                tint = SnapRed,
                                modifier = Modifier.size(28.dp)
                            )
                            // Small "AI" badge
                            Badge(
                                modifier = Modifier.align(Alignment.TopEnd),
                                containerColor = SnapRed,
                                contentColor = Color.White
                            ) {
                                Text("AI", fontSize = 8.sp)
                            }
                        }
                    }
                    IconButton(onClick = { viewModel.loadStories() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = SnapBlack,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier.shadow(
                    elevation = 8.dp,
                    spotColor = SnapYellow.copy(alpha = 0.25f)
                )
            )
        },
        bottomBar = {
            SnapConnectBottomBarWithFAB(navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Stories content
            StoriesContent(
                navController = navController,
                uiState = uiState,
                onRefresh = { viewModel.loadStories() },
                onFilterChange = { viewModel.setStyleFilter(it) }
            )
            
            // Error handling
            uiState.errorMessage?.let { error ->
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    action = {
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text(error)
                }
            }
        }
    }
}

@Composable
fun StoriesContent(
    navController: NavController,
    uiState: HomeUiState,
    onRefresh: () -> Unit,
    onFilterChange: (String?) -> Unit = {}
) {
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = SnapYellow)
        }
    } else if (uiState.recommendedStories.isEmpty() && uiState.otherStories.isEmpty()) {
        EmptyStoriesState(
            onAddStory = { navController.navigate(Screen.Camera.route) }
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // Style filter chips row
            if (uiState.availableStyleTags.isNotEmpty()) {
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            val selected = uiState.styleFilter == null
                            AssistChip(
                                onClick = { onFilterChange(null) },
                                label = { Text("All") },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = if (selected) SnapYellow else MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }
                        items(uiState.availableStyleTags) { tag ->
                            val selected = uiState.styleFilter == tag
                            AssistChip(
                                onClick = { onFilterChange(tag) },
                                label = { Text(tag) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = if (selected) SnapYellow else MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }
                    }
                }
            }
            
            // Story circles at the top - combine both recommended and other stories
            val circleMap = uiState.userStories.filterKeys { u ->
                uiState.currentUserId?.let { it == u.id } == true || uiState.friendIds.contains(u.id)
            }
            item {
                StoryCircles(
                    userStories = circleMap,
                    onStoryClick = { user, stories ->
                        // Navigate to story viewer with the first story
                        stories.firstOrNull()?.let { story ->
                            navController.navigate(Screen.StoryView.createRoute(story.id))
                        }
                    },
                    onAddStory = { navController.navigate(Screen.Camera.route) }
                )
            }
            
            // Prepare filtered maps
            val recommendedFiltered = if (uiState.styleFilter == null) uiState.recommendedStories else uiState.recommendedStories.mapValues { list -> list.value.filter { it.styleTags.contains(uiState.styleFilter) } }.filter { it.value.isNotEmpty() }
            val otherFiltered = if (uiState.styleFilter == null) uiState.otherStories else uiState.otherStories.mapValues { list -> list.value.filter { it.styleTags.contains(uiState.styleFilter) } }.filter { it.value.isNotEmpty() }
            
            // Recommended Stories Section
            if (uiState.hasRecommendations) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Recommended",
                            tint = SnapYellow,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Recommended for You",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Based on your interests",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
                
                // Show recommended story cards
                recommendedFiltered.forEach { (user, stories) ->
                    items(stories) { story ->
                        RecommendedStoryCard(
                            user = user,
                            story = story,
                            onStoryClick = {
                                navController.navigate(Screen.StoryView.createRoute(story.id))
                            }
                        )
                    }
                }
                
                // Divider between sections
                if (otherFiltered.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(
                            modifier = Modifier.padding(horizontal = 32.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }
            
            // Other Stories Section
            if (otherFiltered.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = if (uiState.hasRecommendations) "More Stories" else "Recent Stories",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                
                // Show other story cards
                otherFiltered.forEach { (user, stories) ->
                    items(stories) { story ->
                        StoryCard(
                            user = user,
                            story = story,
                            onStoryClick = {
                                navController.navigate(Screen.StoryView.createRoute(story.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StoryCircles(
    userStories: Map<User, List<Story>>,
    onStoryClick: (User, List<Story>) -> Unit,
    onAddStory: () -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Add story button
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(72.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { onAddStory() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Story",
                        modifier = Modifier.size(32.dp),
                        tint = SnapYellow
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Add Story",
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
        // User stories
        items(userStories.toList()) { (user, stories) ->
            StoryCircle(
                user = user,
                hasUnseenStory = stories.any { !hasUserSeenStory(it, user.id) },
                onClick = { onStoryClick(user, stories) }
            )
        }
    }
}

@Composable
fun StoryCircle(
    user: User,
    hasUnseenStory: Boolean,
    onClick: () -> Unit
) {
    // Scale animation on press
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(72.dp)
            .scale(scale)
            .clickable {
                isPressed = true
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .padding(2.dp)
                .then(
                    if (hasUnseenStory) {
                        Modifier.border(
                            width = 3.dp,
                            brush = StoryBorderGradient,
                            shape = CircleShape
                        )
                    } else {
                        Modifier.border(
                            width = 2.dp,
                            color = WarmGray400,
                            shape = CircleShape
                        )
                    }
                )
                .padding(4.dp)
                .clip(CircleShape)
                .shadow(
                    elevation = if (hasUnseenStory) 4.dp else 0.dp,
                    shape = CircleShape,
                    spotColor = SnapPurple.copy(alpha = 0.25f)
                )
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
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(SnapPurple, SnapPink)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.username.firstOrNull()?.uppercase() ?: "?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = user.displayName ?: user.username,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
    
    // Reset pressed state
    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryCard(
    user: User,
    story: Story,
    onStoryClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cardScale"
    )
    
    Card(
        onClick = {
            isPressed = true
            onStoryClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .scale(cardScale)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = SnapBlack.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User avatar with gradient border
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(SnapPurple, SnapPink)
                        ),
                        shape = CircleShape
                    )
                    .padding(3.dp)
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
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(SnapPurple, SnapPink)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.username.firstOrNull()?.uppercase() ?: "?",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Story info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.displayName ?: user.username,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                // Caption if available
                if (!story.caption.isNullOrBlank()) {
                    Text(
                        text = story.caption,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = getTimeAgo(story.createdAt),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    // Show reaction counts if any
                    if (story.likesCount > 0 || story.dislikesCount > 0) {
                        Text(
                            text = "•",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        if (story.likesCount > 0) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ThumbUp,
                                    contentDescription = "Likes",
                                    modifier = Modifier.size(14.dp),
                                    tint = SnapBlue
                                )
                                Text(
                                    text = "${story.likesCount}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        
                        if (story.dislikesCount > 0) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ThumbDown,
                                    contentDescription = "Dislikes",
                                    modifier = Modifier.size(14.dp),
                                    tint = SnapRed
                                )
                                Text(
                                    text = "${story.dislikesCount}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
            
            // Media preview with rounded corners
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(WarmGray300)
            ) {
                AsyncImage(
                    model = story.mediaUrl,
                    contentDescription = "Story preview",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Video indicator overlay with glass effect
                if (story.mediaType == MediaType.VIDEO) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SnapBlack.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(GlassWhite),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Video",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
    
    // Reset pressed state
    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedStoryCard(
    user: User,
    story: Story,
    onStoryClick: () -> Unit
) {
    Card(
        onClick = onStoryClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = SnapYellow.copy(alpha = 0.3f)
        )
    ) {
        Column {
            // Recommendation indicator
            if (story.styleTags.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SnapYellow.copy(alpha = 0.1f))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalOffer,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = SnapYellow
                        )
                        Text(
                            text = story.styleTags.firstOrNull() ?: "Similar style",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
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
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Story info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = user.displayName ?: user.username,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    
                    // Caption if available
                    if (!story.caption.isNullOrBlank()) {
                        Text(
                            text = story.caption,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = getTimeAgo(story.createdAt),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        // Show reaction counts if any
                        if (story.likesCount > 0 || story.dislikesCount > 0) {
                            Text(
                                text = "•",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            if (story.likesCount > 0) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ThumbUp,
                                        contentDescription = "Likes",
                                        modifier = Modifier.size(12.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "${story.likesCount}",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            
                            if (story.dislikesCount > 0) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ThumbDown,
                                        contentDescription = "Dislikes",
                                        modifier = Modifier.size(12.dp),
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                    Text(
                                        text = "${story.dislikesCount}",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Media preview with glow effect for recommended
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(
                            width = 2.dp,
                            color = SnapYellow.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    AsyncImage(
                        model = story.mediaUrl,
                        contentDescription = "Story preview",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Video indicator overlay
                    if (story.mediaType == MediaType.VIDEO) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Video",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStoriesState(
    onAddStory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CollectionsBookmark,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No Stories Yet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Be the first to share a moment!",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onAddStory,
            colors = ButtonDefaults.buttonColors(
                containerColor = SnapYellow,
                contentColor = Color.Black
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Story")
        }
    }
}

// Helper functions
fun hasUserSeenStory(story: Story, userId: String): Boolean {
    return story.viewerIds.contains(userId)
}

fun getTimeAgo(instant: Instant): String {
    val now = Clock.System.now()
    val duration = now - instant
    
    return when {
        duration.inWholeMinutes < 1 -> "Just now"
        duration.inWholeMinutes < 60 -> "${duration.inWholeMinutes}m ago"
        duration.inWholeHours < 24 -> "${duration.inWholeHours}h ago"
        else -> "${duration.inWholeDays}d ago"
    }
} 