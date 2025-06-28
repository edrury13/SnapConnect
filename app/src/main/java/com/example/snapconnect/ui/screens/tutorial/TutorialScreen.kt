package com.example.snapconnect.ui.screens.tutorial

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snapconnect.ui.theme.SnapBlue
import com.example.snapconnect.ui.theme.SnapRed
import com.example.snapconnect.ui.theme.SnapYellow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.snapconnect.data.repository.AuthRepository
import javax.inject.Inject

data class TutorialPage(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconTint: Color,
    val highlightText: String? = null
)

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    fun markTutorialAsSeen() {
        authRepository.setTutorialSeen()
    }
}

@Composable
fun TutorialScreen(
    onComplete: () -> Unit,
    viewModel: TutorialViewModel = hiltViewModel()
) {
    val pages = remember {
        listOf(
            TutorialPage(
                title = "Welcome to SnapConnect!",
                description = "Share moments with friends through photos and videos that disappear after 24 hours.",
                icon = Icons.Default.PhotoCamera,
                iconTint = SnapYellow
            ),
            TutorialPage(
                title = "Capture & Share",
                description = "Use the camera to capture photos and videos. Add captions and filters before sharing with friends.",
                icon = Icons.Default.CameraAlt,
                iconTint = SnapBlue
            ),
            TutorialPage(
                title = "AI-Powered Inspiration",
                description = "Get creative inspiration from our AI! Find style references, generate mood boards, and discover new artistic styles.",
                icon = Icons.Default.Lightbulb,
                iconTint = SnapRed,
                highlightText = "✨ Try the Inspiration tab for AI-powered creativity!"
            ),
            TutorialPage(
                title = "Connect with Friends",
                description = "Add friends, view their stories, and send messages. React to stories with likes or dislikes!",
                icon = Icons.Default.People,
                iconTint = SnapBlue
            ),
            TutorialPage(
                title = "Privacy First",
                description = "Control who sees your stories. Set them as public for everyone or private for friends only.",
                icon = Icons.Default.Lock,
                iconTint = SnapYellow
            )
        )
    }
    
    var currentPage by remember { mutableStateOf(0) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Skip button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        onComplete()
                        viewModel.markTutorialAsSeen()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("Skip")
                }
            }
            
            // Content with animated transitions
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(
                    targetState = currentPage,
                    animationSpec = tween(300),
                    label = "page_transition"
                ) { page ->
                    TutorialPageContent(
                        page = pages[page],
                        isActive = true
                    )
                }
            }
            
            // Page indicators
            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pages.indices.forEach { index ->
                    val isSelected = currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                            )
                            .clickable {
                                currentPage = index
                            }
                    )
                }
            }
            
            // Navigation buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Previous button
                TextButton(
                    onClick = {
                        if (currentPage > 0) {
                            currentPage--
                        }
                    },
                    enabled = currentPage > 0
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Previous")
                }
                
                // Next/Get Started button
                Button(
                    onClick = {
                        if (currentPage < pages.size - 1) {
                            currentPage++
                        } else {
                            onComplete()
                            viewModel.markTutorialAsSeen()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentPage == pages.size - 1) 
                            SnapYellow else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        if (currentPage < pages.size - 1) "Next" else "Get Started",
                        color = if (currentPage == pages.size - 1) 
                            Color.Black else MaterialTheme.colorScheme.onPrimary
                    )
                    if (currentPage < pages.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next",
                            tint = if (currentPage == pages.size - 1) 
                                Color.Black else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TutorialPageContent(
    page: TutorialPage,
    isActive: Boolean
) {
    val scale by animateFloatAsState(
        targetValue = if (isActive) 1f else 0.85f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    val iconScale by animateFloatAsState(
        targetValue = if (isActive) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessVeryLow
        ),
        label = "iconScale"
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scale(scale)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .scale(iconScale)
                .clip(CircleShape)
                .background(page.iconTint.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = page.icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = page.iconTint
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Title
        Text(
            text = page.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Description
        Text(
            text = page.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 24.sp
        )
        
        // Highlight text (for inspiration feature)
        page.highlightText?.let { highlightText ->
            Spacer(modifier = Modifier.height(24.dp))
            
            AnimatedVisibility(
                visible = isActive,
                enter = fadeIn(animationSpec = tween(500, delayMillis = 300)),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = SnapRed.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = null,
                            tint = SnapRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = highlightText,
                            fontWeight = FontWeight.Medium,
                            color = SnapRed,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
} 