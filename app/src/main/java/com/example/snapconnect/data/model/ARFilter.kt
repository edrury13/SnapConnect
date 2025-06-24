package com.example.snapconnect.data.model

import androidx.annotation.DrawableRes

data class ARFilter(
    val id: String,
    val name: String,
    @DrawableRes val iconRes: Int,
    val overlays: List<FilterOverlay>
)

data class FilterOverlay(
    @DrawableRes val imageRes: Int,
    val landmark: FaceLandmarkType,
    val widthRatio: Float = 1.0f,  // Relative to face width
    val offsetX: Float = 0f,        // Offset from landmark in pixels
    val offsetY: Float = 0f,        // Offset from landmark in pixels
    val rotation: Float = 0f        // Rotation in degrees
)

enum class FaceLandmarkType {
    LEFT_EYE,
    RIGHT_EYE,
    NOSE_BASE,
    MOUTH_BOTTOM,
    LEFT_EAR,
    RIGHT_EAR,
    FOREHEAD,
    BETWEEN_EYES,
    FULL_SCREEN  // For overlays that cover the entire image
} 