package com.example.snapconnect.ui.screens.profile

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.snapconnect.navigation.Screen
import com.example.snapconnect.ui.components.SnapConnectBottomBar
import com.example.snapconnect.ui.screens.auth.AuthViewModel
import com.example.snapconnect.ui.theme.SnapYellow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val authState by authViewModel.uiState.collectAsState()
    val profileState by profileViewModel.uiState.collectAsState()
    
    var displayNameInput by remember { mutableStateOf("") }
    var usernameInput by remember { mutableStateOf("") }
    
    // Initialize inputs when user data loads
    LaunchedEffect(profileState.currentUser) {
        profileState.currentUser?.let { user ->
            displayNameInput = user.displayName ?: ""
            usernameInput = user.username
        }
    }
    
    // Gallery launcher for avatar
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { profileViewModel.uploadAvatar(it) }
    }
    
    // For Android 13+ we don't need READ_EXTERNAL_STORAGE for photo picker
    val shouldRequestPermission = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
    
    // Storage permission for older Android versions
    val storagePermission = if (shouldRequestPermission) {
        rememberPermissionState(
            permission = Manifest.permission.READ_EXTERNAL_STORAGE
        )
    } else null
    
    LaunchedEffect(authState.isLoggedIn) {
        if (!authState.isLoggedIn) {
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    if (profileState.isEditingProfile) {
                        IconButton(onClick = { profileViewModel.toggleEditProfile() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cancel"
                            )
                        }
                    }
                },
                actions = {
                    if (profileState.isEditingProfile) {
                        TextButton(
                            onClick = {
                                profileViewModel.updateProfile(
                                    displayName = displayNameInput.ifBlank { null },
                                    username = if (usernameInput != profileState.currentUser?.username) 
                                        usernameInput else null
                                )
                            }
                        ) {
                            Text("Save", color = SnapYellow)
                        }
                    } else {
                        IconButton(onClick = { profileViewModel.toggleEditProfile() }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile"
                            )
                        }
                        IconButton(onClick = { /* TODO: Settings */ }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            SnapConnectBottomBar(navController = navController)
        },
        snackbarHost = {
            SnackbarHost(
                hostState = remember { SnackbarHostState() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Profile Picture with edit overlay
            Box {
                val currentUser = profileState.currentUser
                if (currentUser?.avatarUrl != null) {
                    AsyncImage(
                        model = currentUser.avatarUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .clickable(enabled = profileState.isEditingProfile) {
                                if (storagePermission == null || storagePermission.status.isGranted) {
                                    galleryLauncher.launch("image/*")
                                } else {
                                    storagePermission.launchPermissionRequest()
                                }
                            },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Surface(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .clickable(enabled = profileState.isEditingProfile) {
                                if (storagePermission == null || storagePermission.status.isGranted) {
                                    galleryLauncher.launch("image/*")
                                } else {
                                    storagePermission.launchPermissionRequest()
                                }
                            },
                        color = SnapYellow
                    ) {
                        if (currentUser != null) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = currentUser.username.firstOrNull()?.uppercase() ?: "?",
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                tint = Color.Black
                            )
                        }
                    }
                }
                
                // Upload indicator or edit icon
                if (profileState.isUploadingAvatar) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = SnapYellow,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else if (profileState.isEditingProfile) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Change avatar",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SnapYellow)
                            .padding(8.dp),
                        tint = Color.Black
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (profileState.isLoading) {
                CircularProgressIndicator(color = SnapYellow)
            } else if (profileState.currentUser != null) {
                val user = profileState.currentUser!!
                
                if (profileState.isEditingProfile) {
                    // Edit mode
                    OutlinedTextField(
                        value = displayNameInput,
                        onValueChange = { displayNameInput = it },
                        label = { Text("Display Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = SnapYellow,
                            cursorColor = SnapYellow
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedTextField(
                        value = usernameInput,
                        onValueChange = { usernameInput = it },
                        label = { Text("Username") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = SnapYellow,
                            cursorColor = SnapYellow
                        )
                    )
                } else {
                    // View mode
                    Text(
                        text = user.displayName ?: user.username,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = "@${user.username}",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Snapcode button
                    OutlinedButton(
                        onClick = { /* TODO: Show snapcode */ },
                        modifier = Modifier.padding(horizontal = 32.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = SnapYellow
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(SnapYellow)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("My Snapcode")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Profile Stats
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileStat(
                        label = "Stories", 
                        value = profileState.storyCount.toString(),
                        onClick = { /* TODO: Show my stories */ }
                    )
                    ProfileStat(
                        label = "Friends", 
                        value = profileState.friendCount.toString(),
                        onClick = { navController.navigate(Screen.Friends.route) }
                    )
                    ProfileStat(
                        label = "Score", 
                        value = profileState.snapCount.toString(),
                        onClick = null
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Menu Options
            if (!profileState.isEditingProfile) {
                ProfileMenuItem(
                    icon = Icons.Default.Person,
                    title = "Add Friends",
                    onClick = { navController.navigate(Screen.Friends.route) }
                )
                
                ProfileMenuItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    onClick = { navController.navigate(Screen.Notifications.route) }
                )
                
                ProfileMenuItem(
                    icon = Icons.Default.Lock,
                    title = "Privacy",
                    onClick = { /* TODO */ }
                )
                
                ProfileMenuItem(
                    icon = Icons.Default.Help,
                    title = "Support",
                    onClick = { /* TODO */ }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Logout Button
                OutlinedButton(
                    onClick = { authViewModel.signOut() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Log Out")
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        
        // Show messages
        LaunchedEffect(profileState.errorMessage, profileState.successMessage) {
            profileState.errorMessage?.let { message ->
                // Show error snackbar
                profileViewModel.clearMessages()
            }
            profileState.successMessage?.let { message ->
                // Show success snackbar
                profileViewModel.clearMessages()
            }
        }
    }
}

@Composable
fun ProfileStat(
    label: String, 
    value: String,
    onClick: (() -> Unit)?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = if (onClick != null) {
            Modifier.clickable { onClick() }
        } else Modifier
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = if (onClick != null) SnapYellow else MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SnapYellow,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
    }
} 