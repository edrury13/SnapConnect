package com.example.snapconnect.utils

import android.content.Context
import android.graphics.*
import androidx.core.graphics.scale
import com.example.snapconnect.data.model.ARFilter
import com.example.snapconnect.data.model.FaceLandmarkType
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceLandmark
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

@Singleton
class FilterProcessor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    fun applyFilterToImage(
        originalBitmap: Bitmap,
        faces: List<Face>,
        filter: ARFilter,
        isFrontCamera: Boolean = false
    ): Bitmap {
        // Create a mutable copy of the bitmap
        val resultBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(resultBitmap)
        
        // Apply filter to each detected face
        faces.forEach { face ->
            applyFilterToFace(canvas, face, filter, isFrontCamera, resultBitmap.width, resultBitmap.height)
        }
        
        return resultBitmap
    }
    
    private fun applyFilterToFace(
        canvas: Canvas,
        face: Face,
        filter: ARFilter,
        isFrontCamera: Boolean,
        imageWidth: Int,
        imageHeight: Int
    ) {
        val paint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
        }
        
        filter.overlays.forEach { overlay ->
            try {
                // Get the overlay bitmap
                val overlayBitmap = BitmapFactory.decodeResource(
                    context.resources,
                    overlay.imageRes
                ) ?: return@forEach
                
                // Get the landmark position
                val landmarkPosition = getLandmarkPosition(face, overlay.landmark)
                    ?: getCenterPointForLandmark(face, overlay.landmark)
                    ?: return@forEach
                
                // Calculate the scale based on face width
                val faceWidth = face.boundingBox.width()
                val desiredWidth = faceWidth * overlay.widthRatio
                val scale = desiredWidth / overlayBitmap.width
                
                // Scale the overlay bitmap
                val scaledBitmap = overlayBitmap.scale(
                    (overlayBitmap.width * scale).toInt(),
                    (overlayBitmap.height * scale).toInt()
                )
                
                // Calculate position
                var x = landmarkPosition.x - (scaledBitmap.width / 2f) + overlay.offsetX
                var y = landmarkPosition.y - (scaledBitmap.height / 2f) + overlay.offsetY
                
                // Mirror for front camera
                if (isFrontCamera) {
                    x = imageWidth - x - scaledBitmap.width
                }
                
                // Apply rotation if needed
                if (abs(overlay.rotation) > 0.1f) {
                    canvas.save()
                    canvas.rotate(
                        overlay.rotation,
                        x + scaledBitmap.width / 2f,
                        y + scaledBitmap.height / 2f
                    )
                    canvas.drawBitmap(scaledBitmap, x, y, paint)
                    canvas.restore()
                } else {
                    canvas.drawBitmap(scaledBitmap, x, y, paint)
                }
                
                // Clean up
                if (scaledBitmap != overlayBitmap) {
                    scaledBitmap.recycle()
                }
            } catch (e: Exception) {
                // Ignore overlay if there's an error loading it
                e.printStackTrace()
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
                // Calculate forehead position (above eyes)
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
                // Calculate position between eyes
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
} 