package com.example.snapconnect

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.snapconnect.navigation.Screen
import com.example.snapconnect.navigation.SnapConnectNavigation
import com.example.snapconnect.ui.screens.auth.AuthViewModel
import com.example.snapconnect.ui.theme.SnapConnectTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.snapconnect.data.repository.StoryRepository
import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import com.example.snapconnect.services.StoryCleanupService

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var storyCleanupService: StoryCleanupService
    
    companion object {
        private const val TAG = "MainActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Trigger story cleanup on app start
        lifecycleScope.launch {
            Log.d(TAG, "Triggering story cleanup on app start...")
            storyCleanupService.triggerCleanup()
                .onSuccess { response ->
                    Log.d(TAG, "Story cleanup completed successfully: $response")
                }
                .onFailure { error ->
                    Log.e(TAG, "Story cleanup failed", error)
                }
        }
        
        setContent {
            SnapConnectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SnapConnectApp()
                }
            }
        }
    }
}

@Composable
fun SnapConnectApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.uiState.collectAsStateWithLifecycle()
    
    val startDestination = if (authState.isLoggedIn) {
        Screen.Home.route
    } else {
        Screen.Login.route
    }
    
    SnapConnectNavigation(
        navController = navController,
        startDestination = startDestination
    )
} 