package com.example.snapconnect.ui.screens.camera

import android.graphics.*
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.snapconnect.data.model.ARFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.io.ByteArrayOutputStream

class FilterPreviewProcessor(
    private val scope: CoroutineScope,
    private val onFrameProcessed: (Bitmap) -> Unit
) : ImageAnalysis.Analyzer {
    
    private var currentFilter: ARFilter? = null
    private val paint = Paint()
    private var colorMatrixFilter: ColorMatrixColorFilter? = null
    
    fun setFilter(filter: ARFilter?) {
        currentFilter = filter
        colorMatrixFilter = filter?.colorMatrix?.let { 
            ColorMatrixColorFilter(ColorMatrix(it))
        }
        paint.colorFilter = colorMatrixFilter
    }
    
    override fun analyze(image: ImageProxy) {
        // Only process if we have a color filter
        if (currentFilter?.colorMatrix != null) {
            scope.launch(Dispatchers.Default) {
                val bitmap = imageProxyToBitmap(image)
                val processedBitmap = applyColorFilter(bitmap)
                
                // Clean up
                bitmap.recycle()
                
                // Send processed frame to UI
                scope.launch(Dispatchers.Main) {
                    onFrameProcessed(processedBitmap)
                }
            }
        }
        
        // Always close the image
        image.close()
    }
    
    private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
        val planes = image.planes
        val yPlane = planes[0]
        val uPlane = planes[1]
        val vPlane = planes[2]
        
        val ySize = yPlane.buffer.remaining()
        val uSize = uPlane.buffer.remaining()
        val vSize = vPlane.buffer.remaining()
        
        val nv21 = ByteArray(ySize + uSize + vSize)
        
        yPlane.buffer.get(nv21, 0, ySize)
        
        val uvPixelStride = uPlane.pixelStride
        if (uvPixelStride == 1) {
            uPlane.buffer.get(nv21, ySize, uSize)
            vPlane.buffer.get(nv21, ySize + uSize, vSize)
        } else {
            // Interleave the U and V channels
            val uvBuffer = ByteArray(uSize + vSize)
            uPlane.buffer.get(uvBuffer, 0, uSize)
            vPlane.buffer.get(uvBuffer, uSize, vSize)
            
            var uvIndex = 0
            for (i in 0 until uSize step uvPixelStride) {
                nv21[ySize + uvIndex] = uvBuffer[i]
                nv21[ySize + uvIndex + 1] = uvBuffer[uSize + i]
                uvIndex += 2
            }
        }
        
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, image.width, image.height), 100, out)
        val jpegBytes = out.toByteArray()
        
        return BitmapFactory.decodeByteArray(jpegBytes, 0, jpegBytes.size)
    }
    
    private fun applyColorFilter(source: Bitmap): Bitmap {
        val result = Bitmap.createBitmap(source.width, source.height, source.config)
        val canvas = Canvas(result)
        canvas.drawBitmap(source, 0f, 0f, paint)
        return result
    }
} 