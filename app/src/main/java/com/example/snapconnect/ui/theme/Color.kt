package com.example.snapconnect.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

// Legacy Material You Colors (keeping for compatibility)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// SnapConnect Brand Colors - Refined Artistic Palette
val SnapYellow = Color(0xFF5BA3F5)  // Changed to Sky Blue (was yellow)
val SnapBlue = Color(0xFF4A90E2)    // Sophisticated blue
val SnapBlack = Color(0xFF1A1A1A)   // Softer black
val SnapGray = Color(0xFF262626)
val SnapLightGray = Color(0xFFF5F5F5)
val SnapRed = Color(0xFFE74C3C)     // Refined red
val SnapBlueAccent = Color(0xFF2E86AB)  // Deep ocean blue
val SnapGreen = Color(0xFF27AE60)   // Balanced green

// New Artistic Colors
val SnapPurple = Color(0xFF9B59B6)  // Royal purple
val SnapOrange = Color(0xFFFF6B6B)  // Coral orange
val SnapTeal = Color(0xFF1ABC9C)    // Turquoise
val SnapPink = Color(0xFFFD79A8)    // Soft pink
val SnapSkyBlue = Color(0xFF5BA3F5) // Sky blue (same as new primary)

// Neutral Palette
val WarmGray100 = Color(0xFFFAFAFA)
val WarmGray200 = Color(0xFFF5F5F5)
val WarmGray300 = Color(0xFFE8E8E8)
val WarmGray400 = Color(0xFFD6D6D6)
val WarmGray500 = Color(0xFFB8B8B8)
val WarmGray600 = Color(0xFF8E8E8E)
val WarmGray700 = Color(0xFF5E5E5E)
val WarmGray800 = Color(0xFF3E3E3E)
val WarmGray900 = Color(0xFF1E1E1E)

// Dark Theme Colors
val DarkSurface = Color(0xFF121212)
val DarkSurfaceVariant = Color(0xFF1E1E1E)
val DarkBackground = Color(0xFF0A0A0A)

// Transparency Variations
val SnapYellowAlpha30 = SnapYellow.copy(alpha = 0.3f)
val SnapBlueAlpha30 = SnapBlue.copy(alpha = 0.3f)
val SnapRedAlpha30 = SnapRed.copy(alpha = 0.3f)

// Gradient Definitions
val ArtisticGradient1 = Brush.linearGradient(
    colors = listOf(SnapYellow, SnapOrange)
)

val ArtisticGradient2 = Brush.linearGradient(
    colors = listOf(SnapBlue, SnapTeal)
)

val ArtisticGradient3 = Brush.linearGradient(
    colors = listOf(SnapPurple, SnapPink)
)

val InspirationGradient = Brush.linearGradient(
    colors = listOf(SnapRed, SnapOrange, SnapYellow)
)

val StoryBorderGradient = Brush.sweepGradient(
    colors = listOf(
        SnapYellow,
        SnapOrange,
        SnapRed,
        SnapPurple,
        SnapBlue,
        SnapTeal,
        SnapYellow
    )
)

// Glass effect colors
val GlassWhite = Color(0x33FFFFFF)
val GlassBlack = Color(0x1A000000) 