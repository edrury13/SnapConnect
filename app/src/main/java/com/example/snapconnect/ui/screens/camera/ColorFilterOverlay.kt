package com.example.snapconnect.ui.screens.camera

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import com.example.snapconnect.data.model.ARFilter

@Composable
fun ColorFilterOverlay(
    selectedFilter: ARFilter?,
    modifier: Modifier = Modifier
) {
    selectedFilter?.colorMatrix?.let { matrix ->
        Canvas(modifier = modifier.fillMaxSize()) {
            drawIntoCanvas { canvas ->
                val paint = Paint().apply {
                    // Apply color filter
                    colorFilter = ColorFilter.colorMatrix(
                        ColorMatrix(matrix)
                    )
                    // Set blend mode for overlay effect
                    blendMode = when (selectedFilter.id) {
                        "black_white" -> BlendMode.Saturation
                        "vintage" -> BlendMode.ColorBurn
                        "posterize" -> BlendMode.Difference
                        else -> BlendMode.SrcOver
                    }
                    alpha = when (selectedFilter.id) {
                        "black_white" -> 0.9f
                        "vintage" -> 0.3f
                        "posterize" -> 0.2f
                        else -> 1f
                    }
                }
                
                // Draw a full-screen rectangle with the filter
                canvas.drawRect(
                    0f,
                    0f,
                    size.width,
                    size.height,
                    paint
                )
            }
        }
    }
} 