package com.example.snapconnect

import android.os.Bundle
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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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