package com.example.snapconnect.ui.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.snapconnect.data.model.Friend
import com.example.snapconnect.data.model.User
import com.example.snapconnect.navigation.Screen
import com.example.snapconnect.ui.components.SnapConnectBottomBarWithFAB
import com.example.snapconnect.ui.theme.SnapYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    navController: NavController,
    viewModel: FriendsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.clearMessages()
        }
    }
    
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.clearMessages()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Friends") }
            )
        },
        bottomBar = {
            SnapConnectBottomBarWithFAB(navController = navController)
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = if (uiState.errorMessage != null) 
                            MaterialTheme.colorScheme.errorContainer 
                        else 
                            MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (uiState.errorMessage != null)
                            MaterialTheme.colorScheme.onErrorContainer
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tab Row
            TabRow(
                selectedTabIndex = uiState.selectedTab.ordinal,
                containerColor = MaterialTheme.colorScheme.surface,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[uiState.selectedTab.ordinal]),
                        color = SnapYellow
                    )
                }
            ) {
                Tab(
                    selected = uiState.selectedTab == FriendsTab.FRIENDS,
                    onClick = { viewModel.selectTab(FriendsTab.FRIENDS) },
                    text = { Text("Friends") }
                )
                Tab(
                    selected = uiState.selectedTab == FriendsTab.REQUESTS,
                    onClick = { viewModel.selectTab(FriendsTab.REQUESTS) },
                    text = { 
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Requests")
                            if (uiState.pendingRequests.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Badge(
                                    containerColor = SnapYellow,
                                    contentColor = Color.Black
                                ) {
                                    Text(uiState.pendingRequests.size.toString())
                                }
                            }
                        }
                    }
                )
                Tab(
                    selected = uiState.selectedTab == FriendsTab.ADD_FRIENDS,
                    onClick = { viewModel.selectTab(FriendsTab.ADD_FRIENDS) },
                    text = { Text("Add Friends") }
                )
            }
            
            // Content based on selected tab
            when (uiState.selectedTab) {
                FriendsTab.FRIENDS -> FriendsListContent(
                    friends = uiState.friends,
                    isLoading = uiState.isLoading,
                    onRemoveFriend = viewModel::removeFriend,
                    onChatWithFriend = { user ->
                        viewModel.startChatWith(user.id) { groupId ->
                            navController.navigate(Screen.Chat.createRoute(groupId))
                        }
                    }
                )
                FriendsTab.REQUESTS -> RequestsContent(
                    pendingRequests = uiState.pendingRequests,
                    sentRequests = uiState.sentRequests,
                    isLoading = uiState.isLoading,
                    onAccept = viewModel::acceptFriendRequest,
                    onReject = viewModel::rejectFriendRequest
                )
                FriendsTab.ADD_FRIENDS -> AddFriendsContent(
                    searchQuery = uiState.searchQuery,
                    searchResults = uiState.searchResults,
                    isSearching = uiState.isSearching,
                    onSearchQueryChange = viewModel::searchUsers,
                    onSendRequest = viewModel::sendFriendRequest
                )
            }
        }
    }
}

@Composable
fun FriendsListContent(
    friends: List<Pair<Friend, User>>,
    isLoading: Boolean,
    onRemoveFriend: (String) -> Unit,
    onChatWithFriend: (User) -> Unit
) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = SnapYellow)
        }
    } else if (friends.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No friends yet",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Add friends to start sharing snaps!",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(friends) { (friendship, user) ->
                FriendItem(
                    user = user,
                    onRemove = { onRemoveFriend(friendship.id) },
                    onChat = onChatWithFriend
                )
            }
        }
    }
}

@Composable
fun RequestsContent(
    pendingRequests: List<Pair<Friend, User>>,
    sentRequests: List<Pair<Friend, User>>,
    isLoading: Boolean,
    onAccept: (String) -> Unit,
    onReject: (String) -> Unit
) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = SnapYellow)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (pendingRequests.isNotEmpty()) {
                item {
                    Text(
                        text = "Pending Requests",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(pendingRequests) { (friendship, user) ->
                    PendingRequestItem(
                        user = user,
                        onAccept = { onAccept(friendship.id) },
                        onReject = { onReject(friendship.id) }
                    )
                }
            }
            
            if (sentRequests.isNotEmpty()) {
                item {
                    Text(
                        text = "Sent Requests",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }
                items(sentRequests) { (_, user) ->
                    SentRequestItem(user = user)
                }
            }
            
            if (pendingRequests.isEmpty() && sentRequests.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No pending requests",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriendsContent(
    searchQuery: String,
    searchResults: List<User>,
    isSearching: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onSendRequest: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search by username") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { /* Search is real-time */ }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = SnapYellow,
                cursorColor = SnapYellow
            ),
            shape = RoundedCornerShape(12.dp)
        )
        
        // Search Results
        if (isSearching) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = SnapYellow)
            }
        } else if (searchResults.isEmpty() && searchQuery.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No users found",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(searchResults) { user ->
                    UserSearchItem(
                        user = user,
                        onSendRequest = { onSendRequest(user.username) }
                    )
                }
            }
        }
    }
}

@Composable
fun FriendItem(
    user: User,
    onRemove: () -> Unit,
    onChat: (User) -> Unit
) {
    var showRemoveDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            UserAvatar(user = user, size = 48.dp)
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // User info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.displayName ?: user.username,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = "@${user.username}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
            
            // Action buttons
            Row {
                // Chat button
                IconButton(onClick = { onChat(user) }) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "Chat with ${user.username}",
                        tint = SnapYellow
                    )
                }
                
                // Remove button
                IconButton(onClick = { showRemoveDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.PersonRemove,
                        contentDescription = "Remove friend",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
    
    if (showRemoveDialog) {
        AlertDialog(
            onDismissRequest = { showRemoveDialog = false },
            title = { Text("Remove Friend") },
            text = { Text("Are you sure you want to remove ${user.displayName ?: user.username} from your friends?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRemove()
                        showRemoveDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Remove")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun PendingRequestItem(
    user: User,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            UserAvatar(user = user, size = 48.dp)
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // User info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.displayName ?: user.username,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = "@${user.username}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
            
            // Action buttons
            Row {
                IconButton(onClick = onAccept) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Accept",
                        tint = Color.Green
                    )
                }
                IconButton(onClick = onReject) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Reject",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun SentRequestItem(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            UserAvatar(user = user, size = 48.dp)
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // User info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.displayName ?: user.username,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = "@${user.username}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
            
            // Pending indicator
            Text(
                text = "Pending",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun UserSearchItem(
    user: User,
    onSendRequest: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            UserAvatar(user = user, size = 48.dp)
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // User info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.displayName ?: user.username,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = "@${user.username}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
            
            // Add button
            Button(
                onClick = onSendRequest,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SnapYellow,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add")
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
                fontSize = (size.value / 2).sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
} 