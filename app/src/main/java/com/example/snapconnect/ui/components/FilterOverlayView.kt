package com.example.snapconnect.ui.components

import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import com.example.snapconnect.data.model.ARFilter
import com.example.snapconnect.data.model.FaceLandmarkType
import com.example.snapconnect.data.model.FilterOverlay
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceLandmark
import kotlin.math.abs

@Composable
fun FilterOverlayView(
    faces: List<Face>,
    selectedFilter: ARFilter?,
    viewSize: Size,
    imageSize: Size,
    isFrontCamera: Boolean,
    modifier: Modifier = Modifier
) {
    if (selectedFilter == null || selectedFilter.overlays.isEmpty()) return
    
    val context = LocalContext.current
    val density = LocalDensity.current
    
    // Pre-load overlay images
    val overlayImages = remember(selectedFilter) {
        selectedFilter.overlays.associate { overlay ->
            overlay to try {
                ImageBitmap.imageResource(context.resources, overlay.imageRes)
            } catch (e: Exception) {
                null
            }
        }
    }
    
    // Log dimensions for debugging
    LaunchedEffect(viewSize, imageSize) {
        Log.d("FilterOverlay", "View size: ${viewSize.width}x${viewSize.height}")
        Log.d("FilterOverlay", "Image size: ${imageSize.width}x${imageSize.height}")
    }
    
    Canvas(modifier = modifier.fillMaxSize()) {
        faces.forEach { face ->
            drawFaceOverlays(
                face = face,
                filter = selectedFilter,
                overlayImages = overlayImages,
                viewSize = viewSize,
                imageSize = imageSize,
                isFrontCamera = isFrontCamera
            )
        }
    }
}

private fun DrawScope.drawFaceOverlays(
    face: Face,
    filter: ARFilter,
    overlayImages: Map<FilterOverlay, ImageBitmap?>,
    viewSize: Size,
    imageSize: Size,
    isFrontCamera: Boolean
) {
    // Simple direct scaling - map image coordinates to view coordinates
    val scaleX = viewSize.width / imageSize.width
    val scaleY = viewSize.height / imageSize.height
    
    // For now, use simple scaling without offsets to debug
    val scale = scaleX  // Try using just X scale
    

    
    filter.overlays.forEach { overlay ->
        val overlayImage = overlayImages[overlay] ?: return@forEach
        
        // Get landmark position
        val landmarkPos = getLandmarkPosition(face, overlay.landmark)
            ?: getCenterPointForLandmark(face, overlay.landmark)
            ?: return@forEach
        
        // Debug log the raw values
        if (overlay.landmark == FaceLandmarkType.BETWEEN_EYES) {
            Log.d("FilterOverlay", "=== Debug Info ===")
            Log.d("FilterOverlay", "View size: ${viewSize.width}x${viewSize.height}")
            Log.d("FilterOverlay", "Image size: ${imageSize.width}x${imageSize.height}")
            Log.d("FilterOverlay", "ScaleX: $scaleX, ScaleY: $scaleY, Using: $scale")
            Log.d("FilterOverlay", "Face bounds: ${face.boundingBox}")
            Log.d("FilterOverlay", "Landmark pos: $landmarkPos")
            Log.d("FilterOverlay", "isFrontCamera: $isFrontCamera")
        }
        
        // Calculate overlay size based on face width
        val faceWidth = face.boundingBox.width() * scaleX
        val overlayWidth = faceWidth * overlay.widthRatio
        val overlayScale = overlayWidth / overlayImage.width
        val overlayHeight = overlayImage.height * overlayScale
        
        // Calculate position in view coordinates - simple direct mapping
        var x = landmarkPos.x * scaleX
        var y = landmarkPos.y * scaleY
        
        // Apply filter offsets
        x += overlay.offsetX
        y += overlay.offsetY
        
        // Apply preview-specific corrections for alignment
        val previewOffsetY = if (overlay.landmark == FaceLandmarkType.BETWEEN_EYES) -850f else 0f
        val previewOffsetX = if (overlay.landmark == FaceLandmarkType.BETWEEN_EYES) 575f else 0f
        val previewScaleFactor = if (overlay.landmark == FaceLandmarkType.BETWEEN_EYES) 1.3f else 1.0f
        
        // Apply preview scale adjustment
        val adjustedOverlayWidth = overlayWidth * previewScaleFactor
        val adjustedOverlayHeight = overlayHeight * previewScaleFactor
        
        y += previewOffsetY
        x += previewOffsetX
        
        // Center the overlay on the landmark (using adjusted size)
        x -= adjustedOverlayWidth / 2f
        y -= adjustedOverlayHeight / 2f
        
        // Mirror for front camera
        if (isFrontCamera) {
            x = viewSize.width - x - overlayWidth
        }
        
        // More debug logging
        if (overlay.landmark == FaceLandmarkType.BETWEEN_EYES) {
            Log.d("FilterOverlay", "=== Position Calculation ===")
            Log.d("FilterOverlay", "Scaled position: ${landmarkPos.x * scaleX}, ${landmarkPos.y * scaleY}")
            Log.d("FilterOverlay", "After centering: x=$x, y=$y")
            Log.d("FilterOverlay", "Overlay size: ${overlayWidth}x${overlayHeight}")
            Log.d("FilterOverlay", "View bounds: 0,0 to ${viewSize.width},${viewSize.height}")
        }
        
        // Draw the overlay
        translate(x, y) {
            if (abs(overlay.rotation) > 0.1f) {
                rotate(
                    degrees = overlay.rotation,
                    pivot = Offset(adjustedOverlayWidth / 2f, adjustedOverlayHeight / 2f)
                ) {
                    drawImage(
                        image = overlayImage,
                        dstSize = Size(adjustedOverlayWidth, adjustedOverlayHeight).toIntSize()
                    )
                }
            } else {
                drawImage(
                    image = overlayImage,
                    dstSize = Size(adjustedOverlayWidth, adjustedOverlayHeight).toIntSize()
                )
            }
        }
    }
}

private fun getLandmarkPosition(face: Face, landmarkType: FaceLandmarkType): PointF? {
    val mlKitLandmarkType = when (landmarkType) {
        FaceLandmarkType.LEFT_EYE -> FaceLandmark.LEFT_EYE
        FaceLandmarkType.RIGHT_EYE -> FaceLandmark.RIGHT_EYE
        FaceLandmarkType.NOSE_BASE -> FaceLandmark.NOSE_BASE
        FaceLandmarkType.MOUTH_BOTTOM -> FaceLandmark.MOUTH_BOTTOM
        FaceLandmarkType.LEFT_EAR -> FaceLandmark.LEFT_EAR
        FaceLandmarkType.RIGHT_EAR -> FaceLandmark.RIGHT_EAR
        else -> null
    } ?: return null
    
    return face.getLandmark(mlKitLandmarkType)?.position
}

private fun getCenterPointForLandmark(face: Face, landmarkType: FaceLandmarkType): PointF? {
    return when (landmarkType) {
        FaceLandmarkType.FOREHEAD -> {
            val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)?.position
            val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)?.position
            if (leftEye != null && rightEye != null) {
                PointF(
                    (leftEye.x + rightEye.x) / 2f,
                    face.boundingBox.top.toFloat() + face.boundingBox.height() * 0.15f
                )
            } else null
        }
        FaceLandmarkType.BETWEEN_EYES -> {
            val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)?.position
            val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)?.position
            if (leftEye != null && rightEye != null) {
                PointF(
                    (leftEye.x + rightEye.x) / 2f,
                    (leftEye.y + rightEye.y) / 2f
                )
            } else null
        }
        else -> null
    }
}

private fun Size.toIntSize() = androidx.compose.ui.unit.IntSize(
    width.toInt(),
    height.toInt()
) 