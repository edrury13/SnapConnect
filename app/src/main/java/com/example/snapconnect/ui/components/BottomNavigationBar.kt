package com.example.snapconnect.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.snapconnect.navigation.Screen

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

@Composable
fun SnapConnectBottomBar(
    navController: NavController
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            title = "Messages",
            selectedIcon = Icons.Filled.Message,
            unselectedIcon = Icons.Outlined.Message,
            route = Screen.Messages.route
        ),
        BottomNavItem(
            title = "Camera",
            selectedIcon = Icons.Filled.CameraAlt,
            unselectedIcon = Icons.Outlined.CameraAlt,
            route = Screen.Camera.route
        ),
        BottomNavItem(
            title = "Stories",
            selectedIcon = Icons.Filled.CollectionsBookmark,
            unselectedIcon = Icons.Outlined.CollectionsBookmark,
            route = Screen.Home.route
        ),
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
    
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
} 