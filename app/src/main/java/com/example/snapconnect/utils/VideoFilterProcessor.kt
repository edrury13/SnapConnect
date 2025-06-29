package com.example.snapconnect.utils

import android.content.Context
import android.graphics.*
import android.media.*
import android.net.Uri
import android.opengl.EGL14
import android.opengl.EGLConfig
import android.opengl.EGLContext
import android.opengl.EGLDisplay
import android.opengl.EGLSurface
import android.opengl.GLES20
import android.os.Build
import android.util.Log
import android.view.Surface
import com.example.snapconnect.data.model.ARFilter
import com.example.snapconnect.utils.gl.VideoFilterRenderer
import com.example.snapconnect.utils.gl.InputSurface
import com.example.snapconnect.utils.gl.OutputSurface
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoFilterProcessor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "VideoFilterProcessor"
        private const val TIMEOUT_US = 10000L
        private const val MIME_TYPE = "video/avc"
        private const val BIT_RATE = 5000000 // 5 Mbps
        private const val FRAME_RATE = 30
        private const val I_FRAME_INTERVAL = 1
    }
    
    suspend fun applyFilterToVideo(
        inputVideoUri: Uri,
        outputFile: File,
        filter: ARFilter,
        onProgress: (Float) -> Unit = {}
    ): Result<Uri> = withContext(Dispatchers.IO) {
        var extractor: MediaExtractor? = null
        var decoder: MediaCodec? = null
        var encoder: MediaCodec? = null
        var muxer: MediaMuxer? = null
        var inputSurface: InputSurface? = null
        var outputSurface: OutputSurface? = null
        
        try {
            // Setup extractor
            extractor = MediaExtractor()
            context.contentResolver.openFileDescriptor(inputVideoUri, "r")?.use { pfd ->
                extractor.setDataSource(pfd.fileDescriptor)
            }
            
            val videoTrackIndex = selectVideoTrack(extractor)
            if (videoTrackIndex < 0) {
                return@withContext Result.failure(Exception("No video track found"))
            }
            
            extractor.selectTrack(videoTrackIndex)
            val inputFormat = extractor.getTrackFormat(videoTrackIndex)
            
            val width = inputFormat.getInteger(MediaFormat.KEY_WIDTH)
            val height = inputFormat.getInteger(MediaFormat.KEY_HEIGHT)
            val duration = inputFormat.getLong(MediaFormat.KEY_DURATION, -1L)
            
            // Get rotation from format or metadata
            var rotation = 0
            if (inputFormat.containsKey(MediaFormat.KEY_ROTATION)) {
                rotation = inputFormat.getInteger(MediaFormat.KEY_ROTATION)
            } else {
                // Try to get rotation from MediaExtractor
                val retriever = MediaMetadataRetriever()
                try {
                    context.contentResolver.openFileDescriptor(inputVideoUri, "r")?.use { pfd ->
                        retriever.setDataSource(pfd.fileDescriptor)
                        val rotationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)
                        rotation = rotationStr?.toIntOrNull() ?: 0
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to get rotation from metadata", e)
                } finally {
                    retriever.release()
                }
            }
            
            Log.d(TAG, "Video: ${width}x${height}, duration: $duration, rotation: $rotation degrees")
            
            // Setup encoder
            // Don't swap dimensions - keep original dimensions and let rotation metadata handle orientation
            val outputFormat = MediaFormat.createVideoFormat(MIME_TYPE, width, height).apply {
                setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
                setInteger(MediaFormat.KEY_BIT_RATE, BIT_RATE)
                setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE)
                setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, I_FRAME_INTERVAL)
                // Don't set rotation here - the muxer will handle it with setOrientationHint
            }
            
            encoder = MediaCodec.createEncoderByType(MIME_TYPE)
            encoder.configure(outputFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            
            inputSurface = InputSurface(encoder.createInputSurface())
            inputSurface.makeCurrent()
            encoder.start()
            
            // Setup decoder with output surface
            // Check if video needs horizontal flip (typically front camera videos)
            val needsFlip = checkIfVideoNeedsFlip(inputVideoUri)
            outputSurface = OutputSurface(filter, rotation, needsFlip)
            
            val mime = inputFormat.getString(MediaFormat.KEY_MIME) ?: MIME_TYPE
            decoder = MediaCodec.createDecoderByType(mime)
            decoder.configure(inputFormat, outputSurface.surface, null, 0)
            decoder.start()
            
            // Setup muxer
            muxer = MediaMuxer(outputFile.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
            
            // Don't set orientation hint here; frames are already rendered in the correct
            // orientation through the SurfaceTexture transformation matrix. Adding the
            // hint would rotate the video a second time, causing 90° offset.
            
            // Process video
            doExtractDecodeEditEncodeMux(
                extractor,
                decoder,
                encoder,
                muxer,
                inputSurface,
                outputSurface,
                duration,
                onProgress
            )
            
            onProgress(1f)
            Result.success(Uri.fromFile(outputFile))
            
        } catch (e: Exception) {
            Log.e(TAG, "Error processing video", e)
            outputFile.delete()
            Result.failure(e)
        } finally {
            // Cleanup
            try {
                inputSurface?.release()
                outputSurface?.release()
                decoder?.stop()
                decoder?.release()
                encoder?.stop()
                encoder?.release()
                extractor?.release()
                muxer?.stop()
                muxer?.release()
            } catch (e: Exception) {
                Log.e(TAG, "Error during cleanup", e)
            }
        }
    }
    
    private fun selectVideoTrack(extractor: MediaExtractor): Int {
        val trackCount = extractor.trackCount
        for (i in 0 until trackCount) {
            val format = extractor.getTrackFormat(i)
            val mime = format.getString(MediaFormat.KEY_MIME)
            if (mime?.startsWith("video/") == true) {
                return i
            }
        }
        return -1
    }
    
    private fun doExtractDecodeEditEncodeMux(
        extractor: MediaExtractor,
        decoder: MediaCodec,
        encoder: MediaCodec,
        muxer: MediaMuxer,
        inputSurface: InputSurface,
        outputSurface: OutputSurface,
        duration: Long,
        onProgress: (Float) -> Unit
    ) {
        val bufferInfo = MediaCodec.BufferInfo()
        var inputDone = false
        var outputDone = false
        var encoderOutputTrackIndex = -1
        var muxerStarted = false
        var lastProgress = 0f
        
        while (!outputDone) {
            // Feed input to decoder
            if (!inputDone) {
                val inputBufferIndex = decoder.dequeueInputBuffer(TIMEOUT_US)
                if (inputBufferIndex >= 0) {
                    val inputBuffer = decoder.getInputBuffer(inputBufferIndex)
                    val sampleSize = extractor.readSampleData(inputBuffer!!, 0)
                    
                    if (sampleSize < 0) {
                        decoder.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                        inputDone = true
                    } else {
                        val presentationTimeUs = extractor.sampleTime
                        decoder.queueInputBuffer(inputBufferIndex, 0, sampleSize, presentationTimeUs, 0)
                        extractor.advance()
                        
                        // Update progress
                        if (duration > 0) {
                            val progress = (presentationTimeUs.toFloat() / duration).coerceIn(0f, 0.9f)
                            if (progress - lastProgress > 0.01f) {
                                onProgress(progress)
                                lastProgress = progress
                            }
                        }
                    }
                }
            }
            
            // Process decoder output
            var decoderOutputAvailable = true
            while (decoderOutputAvailable) {
                val outputBufferIndex = decoder.dequeueOutputBuffer(bufferInfo, 0)
                when {
                    outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER -> {
                        decoderOutputAvailable = false
                    }
                    outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                        Log.d(TAG, "Decoder output format changed")
                    }
                    outputBufferIndex >= 0 -> {
                        val doRender = bufferInfo.size != 0
                        decoder.releaseOutputBuffer(outputBufferIndex, doRender)
                        
                        if (doRender) {
                            outputSurface.awaitNewImage()
                            outputSurface.drawImage()
                            inputSurface.setPresentationTime(bufferInfo.presentationTimeUs * 1000)
                            inputSurface.swapBuffers()
                        }
                        
                        if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                            encoder.signalEndOfInputStream()
                            decoderOutputAvailable = false
                        }
                    }
                }
            }
            
            // Process encoder output
            var encoderOutputAvailable = true
            while (encoderOutputAvailable) {
                val outputBufferIndex = encoder.dequeueOutputBuffer(bufferInfo, 0)
                when {
                    outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER -> {
                        encoderOutputAvailable = false
                    }
                    outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                        if (muxerStarted) {
                            throw RuntimeException("Format changed twice")
                        }
                        val newFormat = encoder.outputFormat
                        encoderOutputTrackIndex = muxer.addTrack(newFormat)
                        muxer.start()
                        muxerStarted = true
                    }
                    outputBufferIndex >= 0 -> {
                        val encodedData = encoder.getOutputBuffer(outputBufferIndex)
                            ?: throw RuntimeException("Encoder output buffer was null")
                        
                        if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) {
                            bufferInfo.size = 0
                        }
                        
                        if (bufferInfo.size != 0 && muxerStarted) {
                            encodedData.position(bufferInfo.offset)
                            encodedData.limit(bufferInfo.offset + bufferInfo.size)
                            muxer.writeSampleData(encoderOutputTrackIndex, encodedData, bufferInfo)
                        }
                        
                        encoder.releaseOutputBuffer(outputBufferIndex, false)
                        
                        if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                            outputDone = true
                        }
                    }
                }
            }
        }
    }
    
    private fun checkIfVideoNeedsFlip(videoUri: Uri): Boolean {
        // Check if the video was recorded with a front-facing camera
        // Front camera videos often need to be flipped horizontally
        val retriever = MediaMetadataRetriever()
        return try {
            context.contentResolver.openFileDescriptor(videoUri, "r")?.use { pfd ->
                retriever.setDataSource(pfd.fileDescriptor)
                // Check for front camera metadata
                // Note: This metadata key might not be available on all devices
                val location = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION)
                // For now, we'll assume videos need flipping based on common behavior
                // You might need to adjust this based on your specific use case
                false // Since unfiltered videos aren't flipped, default to false
            } ?: false
        } catch (e: Exception) {
            Log.e(TAG, "Failed to check video metadata", e)
            false
        } finally {
            retriever.release()
        }
    }
} 