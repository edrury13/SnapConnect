package com.example.snapconnect.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.snapconnect.ui.screens.auth.LoginScreen
import com.example.snapconnect.ui.screens.auth.SignUpScreen
import com.example.snapconnect.ui.screens.camera.CameraScreen
import com.example.snapconnect.ui.screens.camera.MediaPreviewScreen
import com.example.snapconnect.ui.screens.friends.FriendsScreen
import com.example.snapconnect.ui.screens.home.HomeScreen
import com.example.snapconnect.ui.screens.messages.MessagesScreen
import com.example.snapconnect.ui.screens.profile.ProfileScreen
import com.example.snapconnect.ui.screens.story.StoryViewScreen
import com.example.snapconnect.ui.screens.chat.ChatScreen
import com.example.snapconnect.ui.screens.notifications.NotificationsScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object Camera : Screen("camera?groupId={groupId}") {
        fun createRoute(groupId: String? = null) = 
            if (groupId != null) "camera?groupId=$groupId" else "camera"
    }
    object Friends : Screen("friends")
    object Messages : Screen("messages")
    object Profile : Screen("profile")
    object StoryView : Screen("story/{storyId}") {
        fun createRoute(storyId: String) = "story/$storyId"
    }
    object MediaPreview : Screen("media_preview/{mediaUri}/{isVideo}?groupId={groupId}&filterId={filterId}") {
        fun createRoute(mediaUri: String, isVideo: Boolean, groupId: String? = null, filterId: String? = null): String {
            val baseRoute = "media_preview/$mediaUri/$isVideo"
            val params = mutableListOf<String>()
            
            groupId?.let { if (it.isNotEmpty()) params.add("groupId=$it") }
            filterId?.let { if (it.isNotEmpty()) params.add("filterId=$it") }
            
            return if (params.isNotEmpty()) {
                "$baseRoute?${params.joinToString("&")}"
            } else {
                baseRoute
            }
        }
    }
    object Chat : Screen("chat/{groupId}") {
        fun createRoute(groupId: String) = "chat/$groupId"
    }
    object Notifications : Screen("notifications")
    object Inspiration : Screen("inspiration")
    object CreateGroup : Screen("create_group")
    object StyleGallery : Screen("style_gallery/{styleTag}") {
        fun createRoute(styleTag: String) = "style_gallery/$styleTag"
    }
}

@Composable
fun SnapConnectNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable(
            route = Screen.Camera.route,
            arguments = listOf(
                navArgument("groupId") { 
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            CameraScreen(
                navController = navController,
                groupId = groupId
            )
        }
        
        composable(Screen.Friends.route) {
            FriendsScreen(navController = navController)
        }
        
        composable(Screen.Messages.route) {
            MessagesScreen(navController = navController)
        }
        
        composable(Screen.CreateGroup.route) {
            com.example.snapconnect.ui.screens.messages.CreateGroupScreen(navController = navController)
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        
        composable(Screen.Inspiration.route) {
            com.example.snapconnect.ui.screens.inspiration.InspirationScreen(navController = navController)
        }
        
        composable(
            route = Screen.StyleGallery.route,
            arguments = listOf(navArgument("styleTag") { type = NavType.StringType })
        ) { backStackEntry ->
            val styleTag = backStackEntry.arguments?.getString("styleTag") ?: ""
            com.example.snapconnect.ui.screens.inspiration.StyleGalleryScreen(
                styleTag = styleTag,
                navController = navController
            )
        }
        
        composable(Screen.Notifications.route) {
            NotificationsScreen(navController = navController)
        }
        
        composable(
            route = Screen.StoryView.route,
            arguments = listOf(navArgument("storyId") { type = NavType.StringType })
        ) { backStackEntry ->
            StoryViewScreen(navController = navController)
        }
        
        composable(
            route = Screen.MediaPreview.route,
            arguments = listOf(
                navArgument("mediaUri") { type = NavType.StringType },
                navArgument("isVideo") { type = NavType.BoolType },
                navArgument("groupId") { 
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("filterId") { 
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val mediaUri = backStackEntry.arguments?.getString("mediaUri") ?: ""
            val isVideo = backStackEntry.arguments?.getBoolean("isVideo") ?: false
            val groupId = backStackEntry.arguments?.getString("groupId")
            val filterId = backStackEntry.arguments?.getString("filterId")
            MediaPreviewScreen(
                navController = navController,
                mediaUri = mediaUri,
                isVideo = isVideo,
                groupId = groupId,
                filterId = filterId
            )
        }
        
        composable(
            route = Screen.Chat.route,
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId") ?: ""
            ChatScreen(
                navController = navController,
                groupId = groupId
            )
        }
    }
} 