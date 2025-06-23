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

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object Camera : Screen("camera")
    object Friends : Screen("friends")
    object Messages : Screen("messages")
    object Profile : Screen("profile")
    object StoryView : Screen("story/{storyId}") {
        fun createRoute(storyId: String) = "story/$storyId"
    }
    object MediaPreview : Screen("media_preview/{mediaUri}/{isVideo}") {
        fun createRoute(mediaUri: String, isVideo: Boolean) = "media_preview/$mediaUri/$isVideo"
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
        
        composable(Screen.Camera.route) {
            CameraScreen(navController = navController)
        }
        
        composable(Screen.Friends.route) {
            FriendsScreen(navController = navController)
        }
        
        composable(Screen.Messages.route) {
            MessagesScreen(navController = navController)
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
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
                navArgument("isVideo") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val mediaUri = backStackEntry.arguments?.getString("mediaUri") ?: ""
            val isVideo = backStackEntry.arguments?.getBoolean("isVideo") ?: false
            MediaPreviewScreen(
                navController = navController,
                mediaUri = mediaUri,
                isVideo = isVideo
            )
        }
    }
} 