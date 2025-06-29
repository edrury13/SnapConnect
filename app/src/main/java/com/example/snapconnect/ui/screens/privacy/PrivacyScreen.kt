package com.example.snapconnect.ui.screens.privacy

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.snapconnect.ui.theme.SnapYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var permissions by remember { mutableStateOf(getPermissionStates(context)) }
    
    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionResults ->
        permissions = getPermissionStates(context)
    }
    
    // Settings launcher
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        permissions = getPermissionStates(context)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy & Permissions") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "Manage App Permissions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "Control what data and features SplatChat can access on your device. You can change these permissions anytime.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Permission Items
            PermissionItem(
                icon = Icons.Default.CameraAlt,
                title = "Camera",
                description = "Take photos and videos for your stories",
                isGranted = permissions.camera,
                onToggle = {
                    if (!permissions.camera) {
                        permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
                    } else {
                        openAppSettings(context, settingsLauncher)
                    }
                }
            )
            
            PermissionItem(
                icon = Icons.Default.Mic,
                title = "Microphone",
                description = "Record audio for your videos",
                isGranted = permissions.microphone,
                onToggle = {
                    if (!permissions.microphone) {
                        permissionLauncher.launch(arrayOf(Manifest.permission.RECORD_AUDIO))
                    } else {
                        openAppSettings(context, settingsLauncher)
                    }
                }
            )
            
            PermissionItem(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                description = "Receive notifications about messages and friend activity",
                isGranted = permissions.notifications,
                onToggle = {
                    if (!permissions.notifications && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
                    } else {
                        openAppSettings(context, settingsLauncher)
                    }
                }
            )
            
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                PermissionItem(
                    icon = Icons.Default.PhotoLibrary,
                    title = "Storage",
                    description = "Access photos and videos from your device",
                    isGranted = permissions.storage,
                    onToggle = {
                        if (!permissions.storage) {
                            permissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                        } else {
                            openAppSettings(context, settingsLauncher)
                        }
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Additional Privacy Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = SnapYellow,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Privacy Information",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "• Internet: Required for uploading stories and messaging\n" +
                              "• Camera & Microphone: Only used when you create content\n" +
                              "• Storage: Only accessed when you choose to upload media\n" +
                              "• Notifications: You control when you receive them",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // App Settings Button
            OutlinedButton(
                onClick = { openAppSettings(context, settingsLauncher) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = SnapYellow
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Open App Settings")
            }
        }
    }
}

@Composable
fun PermissionItem(
    icon: ImageVector,
    title: String,
    description: String,
    isGranted: Boolean,
    onToggle: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
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
                tint = if (isGranted) SnapYellow else MaterialTheme.colorScheme.onSurfaceVariant,
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
                    text = description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Switch(
                    checked = isGranted,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = SnapYellow,
                        checkedTrackColor = SnapYellow.copy(alpha = 0.6f)
                    )
                )
                Text(
                    text = if (isGranted) "Granted" else "Denied",
                    fontSize = 12.sp,
                    color = if (isGranted) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

data class PermissionStates(
    val camera: Boolean = false,
    val microphone: Boolean = false,
    val notifications: Boolean = false,
    val storage: Boolean = false
)

fun getPermissionStates(context: Context): PermissionStates {
    return PermissionStates(
        camera = ContextCompat.checkSelfPermission(
            context, 
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED,
        
        microphone = ContextCompat.checkSelfPermission(
            context, 
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED,
        
        notifications = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context, 
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Pre-Android 13 doesn't need runtime permission for notifications
        },
        
        storage = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            ContextCompat.checkSelfPermission(
                context, 
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Android 13+ uses different storage permissions
        }
    )
}

fun openAppSettings(
    context: Context,
    settingsLauncher: androidx.activity.result.ActivityResultLauncher<Intent>
) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    settingsLauncher.launch(intent)
} 