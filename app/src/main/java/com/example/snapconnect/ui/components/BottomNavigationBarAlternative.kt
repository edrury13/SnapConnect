package com.example.snapconnect.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.snapconnect.navigation.Screen
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.scale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnapConnectBottomBarWithFAB(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            title = "Messages",
            selectedIcon = Icons.Filled.Message,
            unselectedIcon = Icons.Outlined.Message,
            route = Screen.Messages.route
        ),
        BottomNavItem(
            title = "Stories", 
            selectedIcon = Icons.Filled.CollectionsBookmark,
            unselectedIcon = Icons.Outlined.CollectionsBookmark,
            route = Screen.Home.route
        ),
        null, // Placeholder for FAB space
        BottomNavItem(
            title = "Friends",
            selectedIcon = Icons.Filled.People,
            unselectedIcon = Icons.Outlined.People,
            route = Screen.Friends.route
        ),
        BottomNavItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = Screen.Profile.route
        )
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    Box(modifier = modifier) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(24.dp))
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(24.dp),
                    spotColor = com.example.snapconnect.ui.theme.SnapBlack.copy(alpha = 0.15f)
                ),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 0.dp  // Remove default elevation since we're using custom shadow
        ) {
            bottomNavItems.forEach { item ->
                if (item != null) {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                } else {
                    // Empty space for FAB
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        
        // Floating Action Button for Camera with animation
        val infiniteTransition = rememberInfiniteTransition(label = "camera_pulse")
        val cameraScale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.02f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "cameraScale"
        )
        
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.Camera.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-36).dp)  // Adjusted for rounded navigation bar
                .scale(cameraScale)
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape,
                    spotColor = com.example.snapconnect.ui.theme.SnapYellow.copy(alpha = 0.25f)
                ),
            shape = CircleShape,
            containerColor = com.example.snapconnect.ui.theme.SnapYellow,
            contentColor = com.example.snapconnect.ui.theme.SnapBlack,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp  // Using custom shadow instead
            )
        ) {
            Icon(
                Icons.Filled.CameraAlt,
                contentDescription = "Camera",
                modifier = Modifier.size(28.dp)
            )
        }
        
        // Enhanced Inspiration FAB - Bottom right with animation (only on Stories page)
        if (currentRoute == Screen.Home.route) {
            val inspirationTransition = rememberInfiniteTransition(label = "inspiration_pulse")
            val scale by inspirationTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.05f,  // Reduced from 1.1f for subtler animation
                animationSpec = infiniteRepeatable(
                    animation = tween(1500),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "scale"
            )
            
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(Screen.Inspiration.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-16).dp, y = (-80).dp)  // Adjusted for rounded navigation bar
                    .scale(scale)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = com.example.snapconnect.ui.theme.SnapRed.copy(alpha = 0.25f)
                    ),
                containerColor = com.example.snapconnect.ui.theme.SnapRed,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(24.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp  // Using custom shadow instead
                ),
                icon = {
                    Icon(
                        Icons.Filled.Lightbulb,
                        contentDescription = "AI Inspiration",
                        modifier = Modifier.size(20.dp)  // Reduced from 24.dp
                    )
                },
                text = { 
                    Text(
                        "AI Inspire", 
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        fontSize = 14.sp  // Explicitly set smaller font size
                    ) 
                }
            )
        }
    }
} 