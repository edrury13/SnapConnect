package com.example.snapconnect.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.snapconnect.data.model.User
import com.example.snapconnect.navigation.Screen
import com.example.snapconnect.ui.theme.SnapYellow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(
    navController: NavController,
    viewModel: CreateGroupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var groupName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Group") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            val createEnabled = !uiState.isLoading && uiState.selectedFriends.isNotEmpty() && groupName.isNotBlank()
            ExtendedFloatingActionButton(
                onClick = { if (createEnabled) viewModel.createGroup(groupName) },
                icon = { Icon(imageVector = Icons.Default.GroupAdd, contentDescription = null) },
                text = { Text("Create") },
                containerColor = if (createEnabled) SnapYellow else SnapYellow.copy(alpha = 0.5f),
                contentColor = Color.Black
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = groupName,
                onValueChange = { groupName = it },
                label = { Text("Group Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { viewModel.createGroup(groupName) }),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = SnapYellow)
                    }
                }
                uiState.friends.isEmpty() -> {
                    Text("No friends to add", fontSize = 16.sp)
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(uiState.friends) { friend ->
                            FriendSelectableRow(friend, uiState.selectedFriends.contains(friend.id)) {
                                viewModel.toggleFriendSelection(friend.id)
                            }
                        }
                    }
                }
            }
        }

        // Navigate when created
        uiState.groupCreatedId?.let { gid ->
            LaunchedEffect(gid) {
                navController.navigate(Screen.Chat.createRoute(gid)) {
                    popUpTo(Screen.Messages.route) { inclusive = false }
                }
            }
        }

        // Show error
        uiState.errorMessage?.let { err ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = viewModel::clearError) { Text("Dismiss") }
                }
            ) {
                Text(err)
            }
        }
    }
}

@Composable
private fun FriendSelectableRow(friend: User, selected: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar or placeholder
        if (friend.avatarUrl != null) {
            AsyncImage(
                model = friend.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SnapYellow),
                contentAlignment = Alignment.Center
            ) {
                Text(friend.username.firstOrNull()?.uppercase() ?: "?", fontSize = 16.sp, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = friend.displayName ?: friend.username, fontSize = 16.sp)
        }

        Checkbox(
            checked = selected,
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(checkedColor = SnapYellow)
        )
    }
} 