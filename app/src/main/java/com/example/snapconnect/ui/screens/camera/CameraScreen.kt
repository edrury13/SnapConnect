package com.example.snapconnect.ui.screens.camera

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.PhotoFilter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.snapconnect.data.repository.FiltersRepository
import com.example.snapconnect.data.model.ARFilter
import com.example.snapconnect.data.model.FaceLandmarkType
import com.example.snapconnect.navigation.Screen
import com.example.snapconnect.ui.components.FilterOverlayView
import com.example.snapconnect.ui.theme.SnapBlue
import com.example.snapconnect.ui.theme.SnapYellow
import com.example.snapconnect.utils.FilterProcessor
import com.google.accompanist.permissions.*
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.common.InputImage
import com.google.android.gms.tasks.Tasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Inject

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    navController: NavController,
    groupId: String? = null,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    
    // Camera permissions
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val audioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    )
    
    // Camera state
    var camera by remember { mutableStateOf<Camera?>(null) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var videoCapture by remember { mutableStateOf<VideoCapture<Recorder>?>(null) }
    var imageAnalysis by remember { mutableStateOf<ImageAnalysis?>(null) }
    var recording by remember { mutableStateOf<Recording?>(null) }
    var isRecording by remember { mutableStateOf(false) }
    var isFlashEnabled by remember { mutableStateOf(false) }
    var isFrontCamera by remember { mutableStateOf(false) }
    var captureMode by remember { mutableStateOf(CaptureMode.PHOTO) }
    
    // Face detection and filter state
    var detectedFaces by remember { mutableStateOf<List<Face>>(emptyList()) }
    var selectedFilter by remember { mutableStateOf<ARFilter?>(null) }
    val availableFilters = viewModel.availableFilters
    
    // Set the default filter to "No Filter" (first in the list)
    LaunchedEffect(availableFilters) {
        if (selectedFilter == null && availableFilters.isNotEmpty()) {
            selectedFilter = availableFilters.first()
        }
    }
    
    var viewSize by remember { mutableStateOf(Size.Zero) }
    var imageSize by remember { mutableStateOf(Size(1920f, 1080f)) } // Default to 1080p
    var imageRotation by remember { mutableStateOf(0) }
    
    val previewView = remember { PreviewView(context) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = ContextCompat.getMainExecutor(context)
    
                        LaunchedEffect(multiplePermissionsState.allPermissionsGranted) {
        if (multiplePermissionsState.allPermissionsGranted) {
            startCamera(
                context = context,
                lifecycleOwner = lifecycleOwner,
                cameraProviderFuture = cameraProviderFuture,
                previewView = previewView,
                isFrontCamera = isFrontCamera,
                onCameraReady = { cam, imgCapture, vidCapture, imgAnalysis ->
                    camera = cam
                    imageCapture = imgCapture
                    videoCapture = vidCapture
                    imageAnalysis = imgAnalysis
                },
                onFacesDetected = { faces ->
                    detectedFaces = faces
                },
                onImageSizeChanged = { size ->
                    imageSize = size
                    Log.d("CameraScreen", "Image analysis size: ${size.width}x${size.height}")
                }
            )
        }
    }
    
    LaunchedEffect(isFrontCamera) {
        if (multiplePermissionsState.allPermissionsGranted) {
            startCamera(
                context = context,
                lifecycleOwner = lifecycleOwner,
                cameraProviderFuture = cameraProviderFuture,
                previewView = previewView,
                isFrontCamera = isFrontCamera,
                onCameraReady = { cam, imgCapture, vidCapture, imgAnalysis ->
                    camera = cam
                    imageCapture = imgCapture
                    videoCapture = vidCapture
                    imageAnalysis = imgAnalysis
                },
                onFacesDetected = { faces ->
                    detectedFaces = faces
                },
                onImageSizeChanged = { size ->
                    imageSize = size
                    Log.d("CameraScreen", "Image analysis size: ${size.width}x${size.height}")
                }
            )
        }
    }
    
    if (!multiplePermissionsState.allPermissionsGranted) {
        // Permission request screen
        PermissionRequestScreen(
            onRequestPermission = {
                multiplePermissionsState.launchMultiplePermissionRequest()
            },
            onBack = { navController.navigateUp() }
        )
    } else {
        // Camera screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    viewSize = size.toSize()
                }
        ) {
            // Camera preview
            AndroidView(
                factory = { 
                    previewView.apply {
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    }
                },
                modifier = Modifier.fillMaxSize(),
                update = { view ->
                    // Log preview dimensions for debugging
                    Log.d("CameraPreview", "Preview view size: ${view.width}x${view.height}")
                }
            )
            
            // Filter overlay
            FilterOverlayView(
                faces = detectedFaces,
                selectedFilter = selectedFilter,
                viewSize = viewSize,
                imageSize = imageSize,
                isFrontCamera = isFrontCamera,
                modifier = Modifier.fillMaxSize()
            )
            
            // Color filter indicator - simplified without misleading preview
            selectedFilter?.let { filter ->
                if (filter.colorMatrix != null) {
                    // Filter active indicator
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 80.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.8f),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = when (filter.id) {
                                    "black_white" -> Color.White
                                    "vintage" -> Color(0xFFD2691E)
                                    "posterize" -> Color.Magenta
                                    else -> SnapBlue
                                },
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoFilter,
                                contentDescription = null,
                                tint = when (filter.id) {
                                    "black_white" -> Color.White
                                    "vintage" -> Color(0xFFD2691E)
                                    "posterize" -> Color.Magenta
                                    else -> SnapBlue
                                },
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${filter.name} Active",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            // Top controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
                
                // Flash button
                IconButton(
                    onClick = {
                        isFlashEnabled = !isFlashEnabled
                        camera?.cameraControl?.enableTorch(isFlashEnabled)
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = if (isFlashEnabled) Icons.Default.FlashOn else Icons.Default.FlashOff,
                        contentDescription = "Flash",
                        tint = Color.White
                    )
                }
            }
            
            // Bottom controls
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Filter selection
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(availableFilters) { filter ->
                        FilterChip(
                            onClick = { 
                                selectedFilter = filter
                            },
                            label = {
                                Text(
                                    text = filter.name,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            },
                            selected = selectedFilter?.id == filter.id,
                            modifier = Modifier
                                .width(80.dp)
                                .padding(horizontal = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SnapBlue,
                                selectedLabelColor = Color.Black
                            )
                        )
                    }
                }
                
                // Capture mode selector
                Row(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(4.dp)
                ) {
                    CaptureMode.values().forEach { mode ->
                        TextButton(
                            onClick = { captureMode = mode },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (captureMode == mode) Color.White else Color.Gray
                            )
                        ) {
                            Text(mode.label)
                        }
                    }
                }
                
                // Capture controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Gallery button (placeholder for now)
                    IconButton(
                        onClick = { /* TODO: Open gallery */ },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Photo,
                            contentDescription = "Gallery",
                            tint = Color.White
                        )
                    }
                    
                    // Capture button
                    IconButton(
                        onClick = {
                            when (captureMode) {
                                CaptureMode.PHOTO -> capturePhotoWithFilter(
                                    context = context,
                                    imageCapture = imageCapture,
                                    executor = executor,
                                    faces = detectedFaces,
                                    selectedFilter = selectedFilter,
                                    filterProcessor = viewModel.filterProcessor,
                                    isFrontCamera = isFrontCamera,
                                    scope = scope,
                                    onPhotoCaptured = { uri ->
                                        val encodedUri = URLEncoder.encode(uri.toString(), StandardCharsets.UTF_8.toString())
                                        val filterId = selectedFilter?.id
                                        navController.navigate(
                                            Screen.MediaPreview.createRoute(
                                                mediaUri = encodedUri,
                                                isVideo = false,
                                                groupId = groupId,
                                                filterId = filterId
                                            )
                                        )
                                    },
                                    onError = { exception ->
                                        Toast.makeText(context, "Capture failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                                    }
                                )
                                CaptureMode.VIDEO -> {
                                    if (isRecording) {
                                        stopRecording(recording)
                                        isRecording = false
                                    } else {
                                        recording = startVideoRecording(
                                            context = context,
                                            videoCapture = videoCapture,
                                            executor = executor,
                                            selectedFilter = selectedFilter,
                                            onVideoSaved = { uri ->
                                                val encodedUri = URLEncoder.encode(uri.toString(), StandardCharsets.UTF_8.toString())
                                                val filterId = selectedFilter?.id
                                                navController.navigate(
                                                    Screen.MediaPreview.createRoute(
                                                        mediaUri = encodedUri,
                                                        isVideo = true,
                                                        groupId = groupId,
                                                        filterId = filterId
                                                    )
                                                )
                                            },
                                            onError = { recordEvent ->
                                                Toast.makeText(context, "Recording failed", Toast.LENGTH_SHORT).show()
                                            }
                                        )
                                        isRecording = true
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .then(
                                if (captureMode == CaptureMode.VIDEO && isRecording) {
                                    Modifier.border(4.dp, Color.Red, CircleShape)
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        Icon(
                            imageVector = when {
                                captureMode == CaptureMode.VIDEO && isRecording -> Icons.Default.Stop
                                captureMode == CaptureMode.VIDEO -> Icons.Default.Videocam
                                else -> Icons.Default.CameraAlt
                            },
                            contentDescription = "Capture",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    
                    // Switch camera button
                    IconButton(
                        onClick = { isFrontCamera = !isFrontCamera },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlipCameraAndroid,
                            contentDescription = "Switch Camera",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PermissionRequestScreen(
    onRequestPermission: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Camera Permission Required",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "SnapConnect needs camera and microphone access to capture photos and videos.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onRequestPermission,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Grant Permission")
        }
        
        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go Back")
        }
    }
}

private enum class CaptureMode(val label: String) {
    PHOTO("Photo"),
    VIDEO("Video")
}

private fun startCamera(
    context: Context,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    cameraProviderFuture: com.google.common.util.concurrent.ListenableFuture<ProcessCameraProvider>,
    previewView: PreviewView,
    isFrontCamera: Boolean,
    onCameraReady: (Camera, ImageCapture, VideoCapture<Recorder>, ImageAnalysis) -> Unit,
    onFacesDetected: (List<Face>) -> Unit,
    onImageSizeChanged: (Size) -> Unit = {}
) {
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }
        
        val imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .build()
        
        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.HD))
            .build()
        
        val videoCapture = VideoCapture.withOutput(recorder)
        
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    FaceDetectionAnalyzer(
                        onFacesDetected = onFacesDetected,
                        onError = { /* Handle error */ },
                        onImageSizeChanged = { width, height ->
                            onImageSizeChanged(Size(width.toFloat(), height.toFloat()))
                        }
                    )
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
                preview,
                imageCapture,
                videoCapture,
                imageAnalysis
            )
            onCameraReady(camera, imageCapture, videoCapture, imageAnalysis)
        } catch (exc: Exception) {
            // Handle error
        }
    }, ContextCompat.getMainExecutor(context))
}

private fun capturePhotoWithFilter(
    context: Context,
    imageCapture: ImageCapture?,
    executor: Executor,
    faces: List<Face>,
    selectedFilter: ARFilter?,
    filterProcessor: FilterProcessor,
    isFrontCamera: Boolean,
    scope: kotlinx.coroutines.CoroutineScope,
    onPhotoCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val photoFile = File(
        context.cacheDir,
        SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
            .format(System.currentTimeMillis()) + ".jpg"
    )
    
    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
    
    imageCapture?.takePicture(
        outputFileOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                scope.launch {
                    val finalUri = if (selectedFilter != null && (selectedFilter.overlays.isNotEmpty() || selectedFilter.colorMatrix != null)) {
                        withContext(Dispatchers.IO) {
                            // Load the captured image with proper orientation
                            var bitmap = loadBitmapWithCorrectOrientation(photoFile.absolutePath)
                            
                            // Handle front camera mirroring
                            if (isFrontCamera) {
                                val matrix = Matrix().apply {
                                    postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f) // Mirror horizontally
                                }
                                val mirroredBitmap = Bitmap.createBitmap(
                                    bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
                                )
                                bitmap.recycle()
                                bitmap = mirroredBitmap
                            }
                            
                            // Only detect faces if we have face-based overlays
                            val facesToUse = if (selectedFilter.overlays.any { it.landmark != FaceLandmarkType.FULL_SCREEN }) {
                                // Detect faces in the captured image
                                val detector = FaceDetection.getClient(
                                    FaceDetectorOptions.Builder()
                                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                                        .build()
                                )
                                
                                val inputImage = InputImage.fromBitmap(bitmap, 0)
                                
                                try {
                                    val detectedFaces = Tasks.await(detector.process(inputImage))
                                    detector.close()
                                    detectedFaces
                                } catch (e: Exception) {
                                    detector.close()
                                    emptyList()
                                }
                            } else {
                                emptyList()
                            }
                            
                            // Apply filter (color matrix and/or overlays)
                            val filteredBitmap = filterProcessor.applyFilterToImage(
                                originalBitmap = bitmap,
                                faces = facesToUse,
                                filter = selectedFilter,
                                isFrontCamera = false // Already handled rotation/mirroring
                            )
                            
                            // Save filtered image
                            val filteredFile = File(
                                context.cacheDir,
                                "filtered_${photoFile.name}"
                            )
                            
                            FileOutputStream(filteredFile).use { out ->
                                filteredBitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
                            }
                            
                            // Clean up
                            bitmap.recycle()
                            filteredBitmap.recycle()
                            photoFile.delete()
                            
                            Uri.fromFile(filteredFile)
                        }
                    } else {
                        // Handle proper orientation for regular photos without filters
                        withContext(Dispatchers.IO) {
                            // Load the captured image with proper orientation
                            var bitmap = loadBitmapWithCorrectOrientation(photoFile.absolutePath)
                            
                            // Mirror for front camera
                            if (isFrontCamera) {
                                val matrix = Matrix().apply {
                                    postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f) // Mirror horizontally
                                }
                                val mirroredBitmap = Bitmap.createBitmap(
                                    bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
                                )
                                bitmap.recycle()
                                bitmap = mirroredBitmap
                            }
                            
                            // Save the properly oriented image
                            FileOutputStream(photoFile).use { out ->
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
                            }
                            bitmap.recycle()
                            
                            Uri.fromFile(photoFile)
                        }
                    }
                    
                    onPhotoCaptured(finalUri)
                }
            }
            
            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }
    )
}

private fun capturePhoto(
    context: Context,
    imageCapture: ImageCapture?,
    executor: Executor,
    onPhotoCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val photoFile = File(
        context.cacheDir,
        SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
            .format(System.currentTimeMillis()) + ".jpg"
    )
    
    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
    
    imageCapture?.takePicture(
        outputFileOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                onPhotoCaptured(savedUri)
            }
            
            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }
    )
}

private fun startVideoRecording(
    context: Context,
    videoCapture: VideoCapture<Recorder>?,
    executor: Executor,
    selectedFilter: ARFilter?,
    onVideoSaved: (Uri) -> Unit,
    onError: (VideoRecordEvent) -> Unit
): Recording? {
    val videoFile = File(
        context.cacheDir,
        SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
            .format(System.currentTimeMillis()) + ".mp4"
    )
    
    val outputOptions = FileOutputOptions.Builder(videoFile).build()
    
    return videoCapture?.output
        ?.prepareRecording(context, outputOptions)
        ?.withAudioEnabled()
        ?.start(executor) { recordEvent ->
            when (recordEvent) {
                is VideoRecordEvent.Start -> {
                    // Recording started
                }
                is VideoRecordEvent.Finalize -> {
                    if (!recordEvent.hasError()) {
                        val savedUri = Uri.fromFile(videoFile)
                        onVideoSaved(savedUri)
                    } else {
                        onError(recordEvent)
                    }
                }
            }
        }
}

private fun stopRecording(recording: Recording?) {
    recording?.stop()
}

private fun loadBitmapWithCorrectOrientation(imagePath: String): Bitmap {
    // First load the image
    val bitmap = BitmapFactory.decodeFile(imagePath)
    
    // Read EXIF data to get orientation
    val exif = ExifInterface(imagePath)
    val orientation = exif.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )
    
    // Apply rotation based on EXIF orientation
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> {
            val matrix = Matrix().apply { postRotate(90f) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true).also {
                bitmap.recycle()
            }
        }
        ExifInterface.ORIENTATION_ROTATE_180 -> {
            val matrix = Matrix().apply { postRotate(180f) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true).also {
                bitmap.recycle()
            }
        }
        ExifInterface.ORIENTATION_ROTATE_270 -> {
            val matrix = Matrix().apply { postRotate(270f) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true).also {
                bitmap.recycle()
            }
        }
        ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> {
            val matrix = Matrix().apply { postScale(-1f, 1f) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true).also {
                bitmap.recycle()
            }
        }
        ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
            val matrix = Matrix().apply { postScale(1f, -1f) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true).also {
                bitmap.recycle()
            }
        }
        else -> bitmap // No rotation needed
    }
}

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val filtersRepository: FiltersRepository,
    val filterProcessor: FilterProcessor
) : ViewModel() {
    
    val availableFilters = filtersRepository.getAvailableFilters()
} 