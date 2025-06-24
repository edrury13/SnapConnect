package com.example.snapconnect.ui.screens.camera

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceDetectionAnalyzer(
    private val onFacesDetected: (List<Face>) -> Unit,
    private val onError: (Exception) -> Unit = {},
    private val onImageSizeChanged: (width: Int, height: Int) -> Unit = { _, _ -> },
    private val onRotationChanged: (Int) -> Unit = {}
) : ImageAnalysis.Analyzer {
    
    private var lastImageWidth = 0
    private var lastImageHeight = 0
    private var lastRotation = -1
    
    private val detector = FaceDetection.getClient(
        FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            .setMinFaceSize(0.15f)
            .enableTracking()
            .build()
    )
    
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            // Check if image size has changed
            if (imageProxy.width != lastImageWidth || imageProxy.height != lastImageHeight) {
                lastImageWidth = imageProxy.width
                lastImageHeight = imageProxy.height
                onImageSizeChanged(imageProxy.width, imageProxy.height)
            }
            
            // Check if rotation has changed
            if (imageProxy.imageInfo.rotationDegrees != lastRotation) {
                lastRotation = imageProxy.imageInfo.rotationDegrees
                onRotationChanged(lastRotation)
            }
            
            val image = InputImage.fromMediaImage(
                mediaImage, 
                imageProxy.imageInfo.rotationDegrees
            )
            
            detector.process(image)
                .addOnSuccessListener { faces ->
                    onFacesDetected(faces)
                }
                .addOnFailureListener { exception ->
                    onError(exception)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
    
    fun close() {
        detector.close()
    }
} 