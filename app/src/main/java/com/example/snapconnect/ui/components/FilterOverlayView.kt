package com.example.snapconnect.ui.components

import android.graphics.PointF
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
    // Calculate scale factors to map from image coordinates to view coordinates
    val scaleX = viewSize.width / imageSize.width
    val scaleY = viewSize.height / imageSize.height
    
    filter.overlays.forEach { overlay ->
        val overlayImage = overlayImages[overlay] ?: return@forEach
        
        // Get landmark position
        val landmarkPos = getLandmarkPosition(face, overlay.landmark)
            ?: getCenterPointForLandmark(face, overlay.landmark)
            ?: return@forEach
        
        // Calculate overlay size based on face width
        val faceWidth = face.boundingBox.width() * scaleX
        val overlayWidth = faceWidth * overlay.widthRatio
        val overlayScale = overlayWidth / overlayImage.width
        val overlayHeight = overlayImage.height * overlayScale
        
        // Calculate position in view coordinates
        var x = landmarkPos.x * scaleX
        var y = landmarkPos.y * scaleY
        
        // Apply offsets
        x += overlay.offsetX * scaleX
        y += overlay.offsetY * scaleY
        
        // Center the overlay on the landmark
        x -= overlayWidth / 2f
        y -= overlayHeight / 2f
        
        // Mirror for front camera
        if (isFrontCamera) {
            x = viewSize.width - x - overlayWidth
        }
        
        // Draw the overlay
        translate(x, y) {
            if (abs(overlay.rotation) > 0.1f) {
                rotate(
                    degrees = overlay.rotation,
                    pivot = Offset(overlayWidth / 2f, overlayHeight / 2f)
                ) {
                    drawImage(
                        image = overlayImage,
                        dstSize = Size(overlayWidth, overlayHeight).toIntSize()
                    )
                }
            } else {
                drawImage(
                    image = overlayImage,
                    dstSize = Size(overlayWidth, overlayHeight).toIntSize()
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