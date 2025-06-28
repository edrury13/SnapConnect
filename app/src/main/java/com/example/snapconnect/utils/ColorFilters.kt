package com.example.snapconnect.utils

object ColorFilters {
    /**
     * Black and White filter - converts image to grayscale
     * Uses equal weights for R, G, B channels
     */
    val BLACK_AND_WHITE = floatArrayOf(
        0.33f, 0.33f, 0.33f, 0f, 0f,
        0.33f, 0.33f, 0.33f, 0f, 0f,
        0.33f, 0.33f, 0.33f, 0f, 0f,
        0f, 0f, 0f, 1f, 0f
    )
    
    /**
     * Vintage/Retro filter - warm tones with slight fading
     * Enhances reds/yellows and adds a slight brightness
     */
    val VINTAGE = floatArrayOf(
        0.5f, 0.5f, 0.1f, 0f, 30f,
        0.4f, 0.5f, 0.1f, 0f, 20f,
        0.3f, 0.4f, 0.1f, 0f, 10f,
        0f, 0f, 0f, 1f, 0f
    )
    
    /**
     * Posterize effect - reduces the number of colors
     * This is a simplified version that increases contrast
     * For true posterization, we'll need additional processing
     */
    val POSTERIZE_BASE = floatArrayOf(
        2.0f, 0f, 0f, 0f, -128f,
        0f, 2.0f, 0f, 0f, -128f,
        0f, 0f, 2.0f, 0f, -128f,
        0f, 0f, 0f, 1f, 0f
    )
} 