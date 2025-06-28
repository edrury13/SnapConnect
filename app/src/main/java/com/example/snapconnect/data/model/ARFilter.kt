package com.example.snapconnect.data.model

import androidx.annotation.DrawableRes

data class ARFilter(
    val id: String,
    val name: String,
    @DrawableRes val iconRes: Int,
    val overlays: List<FilterOverlay>,
    val colorMatrix: FloatArray? = null  // For color transformation filters
) {
    // Override equals and hashCode because of FloatArray
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ARFilter

        if (id != other.id) return false
        if (name != other.name) return false
        if (iconRes != other.iconRes) return false
        if (overlays != other.overlays) return false
        if (colorMatrix != null) {
            if (other.colorMatrix == null) return false
            if (!colorMatrix.contentEquals(other.colorMatrix)) return false
        } else if (other.colorMatrix != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + iconRes
        result = 31 * result + overlays.hashCode()
        result = 31 * result + (colorMatrix?.contentHashCode() ?: 0)
        return result
    }
}

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