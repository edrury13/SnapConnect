package com.example.snapconnect.data.repository

import com.example.snapconnect.R
import com.example.snapconnect.data.model.ARFilter
import com.example.snapconnect.data.model.FaceLandmarkType
import com.example.snapconnect.data.model.FilterOverlay
import com.example.snapconnect.utils.ColorFilters
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FiltersRepository @Inject constructor() {
    
    fun getAvailableFilters(): List<ARFilter> = listOf(
        ARFilter(
            id = "none",
            name = "No Filter",
            iconRes = R.drawable.ic_launcher,  // Will be replaced with actual icon
            overlays = emptyList()
        ),
        ARFilter(
            id = "black_white",
            name = "B&W",
            iconRes = R.drawable.ic_launcher,  // TODO: Add B&W icon
            overlays = emptyList(),
            colorMatrix = ColorFilters.BLACK_AND_WHITE
        ),
        ARFilter(
            id = "vintage",
            name = "Vintage",
            iconRes = R.drawable.ic_launcher,  // TODO: Add vintage icon
            overlays = emptyList(),
            colorMatrix = ColorFilters.VINTAGE
        ),
        ARFilter(
            id = "posterize",
            name = "Posterize",
            iconRes = R.drawable.ic_launcher,  // TODO: Add posterize icon
            overlays = emptyList(),
            colorMatrix = ColorFilters.POSTERIZE_BASE
        ),
        ARFilter(
            id = "sunglasses",
            name = "Cool Shades",
            iconRes = R.drawable.sunglasses,
            overlays = listOf(
                FilterOverlay(
                    imageRes = R.drawable.sunglasses,
                    landmark = FaceLandmarkType.BETWEEN_EYES,
                    widthRatio = 1.2f,
                    offsetX = 0f,
                    offsetY = 0f  // Reset to 0 for captured image
                )
            )
        ),
        ARFilter(
            id = "frame",
            name = "Frame",
            iconRes = R.drawable.frame,
            overlays = listOf(
                FilterOverlay(
                    imageRes = R.drawable.frame,
                    landmark = FaceLandmarkType.FULL_SCREEN,
                    widthRatio = 0.5f,  // 50% of image width for a skinnier frame
                    offsetX = 0f,  // Adjust this to move frame left/right
                    offsetY = 0f   // Adjust this to move frame up/down
                )
            )
        )
    )
} 