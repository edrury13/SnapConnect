package com.example.snapconnect.data.repository

import com.example.snapconnect.R
import com.example.snapconnect.data.model.ARFilter
import com.example.snapconnect.data.model.FaceLandmarkType
import com.example.snapconnect.data.model.FilterOverlay
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
            id = "cat",
            name = "Cat Face",
            iconRes = R.drawable.ic_launcher,  // Will be replaced with cat icon
            overlays = listOf(
                FilterOverlay(
                    imageRes = R.drawable.ic_launcher, // Will be replaced with cat_ears.png
                    landmark = FaceLandmarkType.FOREHEAD,
                    widthRatio = 1.3f,
                    offsetY = -50f
                ),
                FilterOverlay(
                    imageRes = R.drawable.ic_launcher, // Will be replaced with cat_nose.png
                    landmark = FaceLandmarkType.NOSE_BASE,
                    widthRatio = 0.3f,
                    offsetY = -10f
                ),
                FilterOverlay(
                    imageRes = R.drawable.ic_launcher, // Will be replaced with cat_whiskers.png
                    landmark = FaceLandmarkType.NOSE_BASE,
                    widthRatio = 1.0f,
                    offsetY = 0f
                )
            )
        ),
        ARFilter(
            id = "party",
            name = "Party Time",
            iconRes = R.drawable.ic_launcher,  // Will be replaced with party icon
            overlays = listOf(
                FilterOverlay(
                    imageRes = R.drawable.ic_launcher, // Will be replaced with party_hat.png
                    landmark = FaceLandmarkType.FOREHEAD,
                    widthRatio = 1.0f,
                    offsetY = -80f
                ),
                FilterOverlay(
                    imageRes = R.drawable.ic_launcher, // Will be replaced with party_glasses.png
                    landmark = FaceLandmarkType.BETWEEN_EYES,
                    widthRatio = 0.9f,
                    offsetY = -20f
                )
            )
        )
    )
} 