package com.example.snapconnect.utils

import android.content.Context
import android.graphics.*
import androidx.core.graphics.scale
import com.example.snapconnect.data.model.ARFilter
import com.example.snapconnect.data.model.FaceLandmarkType
import com.example.snapconnect.data.model.FilterOverlay
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
        // Start with a copy of the original bitmap
        var resultBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        
        // Apply color filter first if present
        filter.colorMatrix?.let { matrix ->
            resultBitmap = if (filter.id == "posterize") {
                // Special handling for posterize effect
                applyPosterizeEffect(resultBitmap)
            } else {
                // Apply standard color matrix
                applyColorFilter(resultBitmap, matrix)
            }
        }
        
        // Then apply overlays if any
        if (filter.overlays.isNotEmpty()) {
            val canvas = Canvas(resultBitmap)
            
            // First, apply full-screen overlays
            filter.overlays
                .filter { it.landmark == FaceLandmarkType.FULL_SCREEN }
                .forEach { overlay ->
                    applyFullScreenOverlay(canvas, overlay, resultBitmap.width, resultBitmap.height)
                }
            
            // Then apply filter to each detected face
            faces.forEach { face ->
                applyFilterToFace(canvas, face, filter, isFrontCamera, resultBitmap.width, resultBitmap.height)
            }
        }
        
        return resultBitmap
    }
    
    private fun applyColorFilter(bitmap: Bitmap, colorMatrix: FloatArray): Bitmap {
        val result = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(result)
        val paint = Paint().apply {
            isAntiAlias = true
            isDither = true
            colorFilter = ColorMatrixColorFilter(ColorMatrix(colorMatrix))
        }
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        bitmap.recycle()
        return result
    }
    
    private fun applyPosterizeEffect(bitmap: Bitmap): Bitmap {
        val result = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        
        // Apply posterization by reducing color levels
        val width = result.width
        val height = result.height
        val pixels = IntArray(width * height)
        result.getPixels(pixels, 0, width, 0, 0, width, height)
        
        // Number of color levels (lower = more posterized)
        val levels = 5
        val factor = 255f / (levels - 1)
        
        for (i in pixels.indices) {
            val pixel = pixels[i]
            val a = (pixel shr 24) and 0xff
            var r = (pixel shr 16) and 0xff
            var g = (pixel shr 8) and 0xff
            var b = pixel and 0xff
            
            // Reduce color levels
            r = ((r / factor).toInt() * factor).toInt().coerceIn(0, 255)
            g = ((g / factor).toInt() * factor).toInt().coerceIn(0, 255)
            b = ((b / factor).toInt() * factor).toInt().coerceIn(0, 255)
            
            pixels[i] = (a shl 24) or (r shl 16) or (g shl 8) or b
        }
        
        result.setPixels(pixels, 0, width, 0, 0, width, height)
        bitmap.recycle()
        return result
    }
    
    private fun applyFullScreenOverlay(
        canvas: Canvas,
        overlay: FilterOverlay,
        imageWidth: Int,
        imageHeight: Int
    ) {
        val paint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
        }
        
        try {
            // Get the overlay bitmap
            val overlayBitmap = BitmapFactory.decodeResource(
                context.resources,
                overlay.imageRes
            ) ?: return
            
            // Scale based on height to maintain full height coverage
            val scaleY = imageHeight.toFloat() / overlayBitmap.height
            
            // Apply width ratio to compress horizontally while keeping full height
            val scaledWidth = (overlayBitmap.width * scaleY * overlay.widthRatio).toInt()
            val scaledHeight = (overlayBitmap.height * scaleY).toInt()
            
            // Scale the overlay bitmap
            val scaledBitmap = overlayBitmap.scale(scaledWidth, scaledHeight)
            
            // Center the frame on the image and apply offsets
            val offsetX = ((imageWidth - scaledWidth) / 2f) + overlay.offsetX
            val offsetY = ((imageHeight - scaledHeight) / 2f) + overlay.offsetY
            
            // Draw the overlay
            canvas.drawBitmap(scaledBitmap, offsetX, offsetY, paint)
            
            // Clean up
            if (scaledBitmap != overlayBitmap) {
                scaledBitmap.recycle()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            // Skip full-screen overlays as they're handled separately
            if (overlay.landmark == FaceLandmarkType.FULL_SCREEN) return@forEach
            
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