package com.example.snapconnect.ui.screens.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.SurfaceTexture
import android.graphics.YuvImage
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.util.Log
import androidx.camera.core.*
import androidx.camera.core.Camera
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.snapconnect.data.model.ARFilter
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.android.gms.tasks.Tasks
import java.io.ByteArrayOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.nio.ByteBuffer

@Composable
fun FilteredCameraPreview(
    modifier: Modifier = Modifier,
    isFrontCamera: Boolean,
    selectedFilter: ARFilter?,
    onCameraReady: (Camera, ImageCapture, VideoCapture<Recorder>, ImageAnalysis) -> Unit,
    onFacesDetected: (List<Face>) -> Unit,
    onImageSizeChanged: (Size) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    
    var textureView by remember { mutableStateOf<TextureView?>(null) }
    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    var filterProcessor by remember { mutableStateOf<FilterProcessor?>(null) }
    
    DisposableEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
        }, ContextCompat.getMainExecutor(context))
        
        onDispose {
            cameraProvider?.unbindAll()
        }
    }
    
    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                TextureView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    textureView = this
                    filterProcessor = FilterProcessor()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
    
    LaunchedEffect(cameraProvider, isFrontCamera, selectedFilter, textureView) {
        val provider = cameraProvider ?: return@LaunchedEffect
        val texture = textureView ?: return@LaunchedEffect
        
        if (texture.isAvailable) {
            startFilteredCamera(
                context = context,
                lifecycleOwner = lifecycleOwner,
                cameraProvider = provider,
                textureView = texture,
                isFrontCamera = isFrontCamera,
                selectedFilter = selectedFilter,
                filterProcessor = filterProcessor!!,
                scope = scope,
                onCameraReady = onCameraReady,
                onFacesDetected = onFacesDetected,
                onImageSizeChanged = onImageSizeChanged
            )
        } else {
            texture.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                    startFilteredCamera(
                        context = context,
                        lifecycleOwner = lifecycleOwner,
                        cameraProvider = provider,
                        textureView = texture,
                        isFrontCamera = isFrontCamera,
                        selectedFilter = selectedFilter,
                        filterProcessor = filterProcessor!!,
                        scope = scope,
                        onCameraReady = onCameraReady,
                        onFacesDetected = onFacesDetected,
                        onImageSizeChanged = onImageSizeChanged
                    )
                }
                
                override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = true
                override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
            }
        }
    }
}

private fun startFilteredCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    cameraProvider: ProcessCameraProvider,
    textureView: TextureView,
    isFrontCamera: Boolean,
    selectedFilter: ARFilter?,
    filterProcessor: FilterProcessor,
    scope: kotlinx.coroutines.CoroutineScope,
    onCameraReady: (Camera, ImageCapture, VideoCapture<Recorder>, ImageAnalysis) -> Unit,
    onFacesDetected: (List<Face>) -> Unit,
    onImageSizeChanged: (Size) -> Unit
) {
    val executor = Executors.newSingleThreadExecutor()
    
    val imageCapture = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
        .build()
    
    val recorder = Recorder.Builder()
        .setQualitySelector(QualitySelector.from(Quality.HD))
        .build()
    
    val videoCapture = VideoCapture.withOutput(recorder)
    
    // Create ImageAnalysis for both face detection and filter preview
    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
        .setTargetResolution(android.util.Size(720, 1280)) // Lower resolution for performance
        .build()
    
    // Custom analyzer that handles both face detection and filter preview
    imageAnalysis.setAnalyzer(executor) { imageProxy ->
        processFrame(
            imageProxy = imageProxy,
            textureView = textureView,
            selectedFilter = selectedFilter,
            filterProcessor = filterProcessor,
            isFrontCamera = isFrontCamera,
            scope = scope,
            onFacesDetected = onFacesDetected,
            onImageSizeChanged = onImageSizeChanged
        )
    }
    
    val cameraSelector = if (isFrontCamera) {
        CameraSelector.DEFAULT_FRONT_CAMERA
    } else {
        CameraSelector.DEFAULT_BACK_CAMERA
    }
    
    try {
        cameraProvider.unbindAll()
        val camera = cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            imageCapture,
            videoCapture,
            imageAnalysis
        )
        onCameraReady(camera, imageCapture, videoCapture, imageAnalysis)
    } catch (exc: Exception) {
        // Handle error
    }
}

private fun processFrame(
    imageProxy: ImageProxy,
    textureView: TextureView,
    selectedFilter: ARFilter?,
    filterProcessor: FilterProcessor,
    isFrontCamera: Boolean,
    scope: kotlinx.coroutines.CoroutineScope,
    onFacesDetected: (List<Face>) -> Unit,
    onImageSizeChanged: (Size) -> Unit
) {
    scope.launch(Dispatchers.Default) {
        try {
            // Convert ImageProxy to Bitmap
            val bitmap = imageProxyToBitmap(imageProxy)
            
            // Detect faces for AR overlays
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            val detector = FaceDetection.getClient(
                FaceDetectorOptions.Builder()
                    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                    .build()
            )
            
            try {
                val faces = Tasks.await(detector.process(inputImage))
                scope.launch(Dispatchers.Main) {
                    onFacesDetected(faces)
                }
            } catch (e: Exception) {
                // Ignore face detection errors
            } finally {
                detector.close()
            }
            
            // Apply filter if selected
            val processedBitmap = if (selectedFilter?.colorMatrix != null) {
                filterProcessor.applyColorMatrix(bitmap, selectedFilter.colorMatrix)
            } else {
                bitmap
            }
            
            // Draw to TextureView
            val canvas = textureView.lockCanvas()
            canvas?.let {
                // Clear canvas
                it.drawColor(Color.BLACK)
                
                // Calculate scaling to fit the view
                val viewWidth = textureView.width.toFloat()
                val viewHeight = textureView.height.toFloat()
                val bitmapWidth = processedBitmap.width.toFloat()
                val bitmapHeight = processedBitmap.height.toFloat()
                
                val scale = Math.max(viewWidth / bitmapWidth, viewHeight / bitmapHeight)
                val scaledWidth = bitmapWidth * scale
                val scaledHeight = bitmapHeight * scale
                val left = (viewWidth - scaledWidth) / 2f
                val top = (viewHeight - scaledHeight) / 2f
                
                val destRect = RectF(left, top, left + scaledWidth, top + scaledHeight)
                val paint = Paint().apply {
                    isFilterBitmap = true
                    isAntiAlias = true
                }
                
                // Mirror for front camera
                if (isFrontCamera) {
                    it.save()
                    it.scale(-1f, 1f, viewWidth / 2f, viewHeight / 2f)
                }
                
                it.drawBitmap(processedBitmap, null, destRect, paint)
                
                if (isFrontCamera) {
                    it.restore()
                }
                
                textureView.unlockCanvasAndPost(it)
            }
            
            // Clean up
            if (processedBitmap != bitmap) {
                processedBitmap.recycle()
            }
            bitmap.recycle()
            
            // Update size info - account for rotation
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val width = if (rotationDegrees == 90 || rotationDegrees == 270) {
                imageProxy.height.toFloat()
            } else {
                imageProxy.width.toFloat()
            }
            val height = if (rotationDegrees == 90 || rotationDegrees == 270) {
                imageProxy.width.toFloat()
            } else {
                imageProxy.height.toFloat()
            }
            onImageSizeChanged(Size(width, height))
            
        } finally {
            imageProxy.close()
        }
    }
}

private fun imageProxyToBitmap(imageProxy: ImageProxy): Bitmap {
    val buffer = imageProxy.planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    
    val bitmap = Bitmap.createBitmap(
        imageProxy.width,
        imageProxy.height,
        Bitmap.Config.ARGB_8888
    )
    bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bytes))
    
    // Apply rotation based on imageProxy rotation
    val rotationDegrees = imageProxy.imageInfo.rotationDegrees
    return if (rotationDegrees != 0) {
        val matrix = Matrix().apply {
            postRotate(rotationDegrees.toFloat())
        }
        val rotatedBitmap = Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
        bitmap.recycle()
        rotatedBitmap
    } else {
        bitmap
    }
}

private class FilterProcessor {
    private val paint = Paint()
    
    fun applyColorMatrix(source: Bitmap, colorMatrix: FloatArray): Bitmap {
        val result = Bitmap.createBitmap(source.width, source.height, source.config)
        val canvas = Canvas(result)
        
        paint.colorFilter = ColorMatrixColorFilter(ColorMatrix(colorMatrix))
        canvas.drawBitmap(source, 0f, 0f, paint)
        
        return result
    }
} 