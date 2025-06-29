package com.example.snapconnect.utils.gl

import android.graphics.SurfaceTexture
import android.opengl.EGL14
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.Matrix
import android.util.Log
import android.view.Surface
import com.example.snapconnect.data.model.ARFilter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Holds state associated with a Surface used for MediaCodec decoder output.
 * The Surface is created from a SurfaceTexture that can be used to render filtered frames.
 */
class OutputSurface(
    private val filter: ARFilter, 
    private val rotation: Int = 0,
    private val needsFlip: Boolean = false
) : SurfaceTexture.OnFrameAvailableListener {
    
    private var surfaceTexture: SurfaceTexture? = null
    val surface: Surface
    
    private var program = 0
    private var textureId = 0
    
    private var positionHandle = 0
    private var textureCoordHandle = 0
    private var mvpMatrixHandle = 0
    private var stMatrixHandle = 0
    
    private val mvpMatrix = FloatArray(16)
    private val stMatrix = FloatArray(16)
    private val rotationMatrix = FloatArray(16)
    
    private var frameAvailable = false
    private val frameSyncObject = Object()
    
    private val triangleVertices: FloatBuffer
    
    companion object {
        private const val TAG = "OutputSurface"
        
        private const val FLOAT_SIZE_BYTES = 4
        private const val TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 5 * FLOAT_SIZE_BYTES
        private const val TRIANGLE_VERTICES_DATA_POS_OFFSET = 0
        private const val TRIANGLE_VERTICES_DATA_UV_OFFSET = 3
        
        private val TRIANGLE_VERTICES_DATA = floatArrayOf(
            // X, Y, Z, U, V
            -1.0f, -1.0f, 0f, 0f, 0f,
             1.0f, -1.0f, 0f, 1f, 0f,
            -1.0f,  1.0f, 0f, 0f, 1f,
             1.0f,  1.0f, 0f, 1f, 1f
        )
        
        // Flipped horizontally (mirrored texture coordinates)
        private val TRIANGLE_VERTICES_DATA_FLIPPED = floatArrayOf(
            // X, Y, Z, U, V (U coordinates flipped)
            -1.0f, -1.0f, 0f, 1f, 0f,
             1.0f, -1.0f, 0f, 0f, 0f,
            -1.0f,  1.0f, 0f, 1f, 1f,
             1.0f,  1.0f, 0f, 0f, 1f
        )
    }
    
    init {
        triangleVertices = ByteBuffer.allocateDirect(
            TRIANGLE_VERTICES_DATA.size * FLOAT_SIZE_BYTES
        ).order(ByteOrder.nativeOrder()).asFloatBuffer()
        triangleVertices.put(TRIANGLE_VERTICES_DATA).position(0)
        
        Matrix.setIdentityM(mvpMatrix, 0)
        Matrix.setIdentityM(rotationMatrix, 0)
        
        // Don't apply rotation in OpenGL - we'll preserve rotation metadata instead
        // This way the video player will handle rotation correctly
        // (Commenting out rotation handling)
        /*
        when (rotation) {
            90 -> {
                Matrix.rotateM(rotationMatrix, 0, 90f, 0f, 0f, 1f)
            }
            180 -> {
                Matrix.rotateM(rotationMatrix, 0, 180f, 0f, 0f, 1f)
            }
            270 -> {
                Matrix.rotateM(rotationMatrix, 0, 270f, 0f, 0f, 1f)
            }
        }
        */
        
        setup()
        
        surfaceTexture = SurfaceTexture(textureId)
        surfaceTexture?.setOnFrameAvailableListener(this)
        
        surface = Surface(surfaceTexture)
    }
    
    private fun setup() {
        // Create texture
        val textures = IntArray(1)
        GLES20.glGenTextures(1, textures, 0)
        textureId = textures[0]
        
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId)
        checkGlError("glBindTexture")
        
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST.toFloat())
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
        checkGlError("glTexParameter")
        
        // Create program
        val fragmentShader = FilterShaders.getFragmentShader(filter.colorMatrix?.let { filter.id })
        program = GLUtils.createProgram(FilterShaders.VERTEX_SHADER, fragmentShader)
        if (program == 0) {
            throw RuntimeException("failed creating program")
        }
        
        // Get handles
        positionHandle = GLES20.glGetAttribLocation(program, "aPosition")
        checkGlError("glGetAttribLocation aPosition")
        if (positionHandle == -1) {
            throw RuntimeException("Could not get attrib location for aPosition")
        }
        
        textureCoordHandle = GLES20.glGetAttribLocation(program, "aTextureCoord")
        checkGlError("glGetAttribLocation aTextureCoord")
        if (textureCoordHandle == -1) {
            throw RuntimeException("Could not get attrib location for aTextureCoord")
        }
        
        mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
        checkGlError("glGetUniformLocation uMVPMatrix")
        
        stMatrixHandle = GLES20.glGetUniformLocation(program, "uSTMatrix")
        checkGlError("glGetUniformLocation uSTMatrix")
    }
    
    override fun onFrameAvailable(surfaceTexture: SurfaceTexture) {
        synchronized(frameSyncObject) {
            if (frameAvailable) {
                Log.w(TAG, "Frame available before consuming")
            }
            frameAvailable = true
            frameSyncObject.notifyAll()
        }
    }
    
    fun awaitNewImage() {
        val timeoutMs = 2500L
        
        synchronized(frameSyncObject) {
            while (!frameAvailable) {
                try {
                    frameSyncObject.wait(timeoutMs)
                    if (!frameAvailable) {
                        throw RuntimeException("Frame wait timed out")
                    }
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    throw RuntimeException(e)
                }
            }
            frameAvailable = false
        }
        
        surfaceTexture?.updateTexImage()
        surfaceTexture?.getTransformMatrix(stMatrix)
        
        // When copying from decoder surface to encoder surface via OpenGL,
        // the video often appears horizontally flipped compared to direct playback.
        // This is because the transformation matrix is designed for display rendering,
        // not for surface-to-surface copies. Apply a horizontal flip to correct this.
        // This is a common issue when using MediaCodec with OpenGL.
        if (needsFlip) {
            val flipMatrix = floatArrayOf(
                -1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                1f, 0f, 0f, 1f
            )
            
            val temp = FloatArray(16)
            Matrix.multiplyMM(temp, 0, stMatrix, 0, flipMatrix, 0)
            System.arraycopy(temp, 0, stMatrix, 0, 16)
        }
    }
    
    fun drawImage() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        
        GLES20.glUseProgram(program)
        checkGlError("glUseProgram")
        
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId)
        
        triangleVertices.position(TRIANGLE_VERTICES_DATA_POS_OFFSET)
        GLES20.glVertexAttribPointer(
            positionHandle, 3, GLES20.GL_FLOAT, false,
            TRIANGLE_VERTICES_DATA_STRIDE_BYTES, triangleVertices
        )
        checkGlError("glVertexAttribPointer position")
        GLES20.glEnableVertexAttribArray(positionHandle)
        checkGlError("glEnableVertexAttribArray position")
        
        triangleVertices.position(TRIANGLE_VERTICES_DATA_UV_OFFSET)
        GLES20.glVertexAttribPointer(
            textureCoordHandle, 2, GLES20.GL_FLOAT, false,
            TRIANGLE_VERTICES_DATA_STRIDE_BYTES, triangleVertices
        )
        checkGlError("glVertexAttribPointer textureCoord")
        GLES20.glEnableVertexAttribArray(textureCoordHandle)
        checkGlError("glEnableVertexAttribArray textureCoord")
        
        if (mvpMatrixHandle != -1) {
            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)
        }
        if (stMatrixHandle != -1) {
            GLES20.glUniformMatrix4fv(stMatrixHandle, 1, false, stMatrix, 0)
        }
        
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        checkGlError("glDrawArrays")
        
        GLES20.glDisableVertexAttribArray(positionHandle)
        GLES20.glDisableVertexAttribArray(textureCoordHandle)
        
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0)
        GLES20.glUseProgram(0)
    }
    
    fun release() {
        surface.release()
        surfaceTexture?.release()
        surfaceTexture = null
        
        if (program != 0) {
            GLES20.glDeleteProgram(program)
            program = 0
        }
        
        if (textureId != 0) {
            val textures = intArrayOf(textureId)
            GLES20.glDeleteTextures(1, textures, 0)
            textureId = 0
        }
    }
    
    private fun checkGlError(op: String) {
        val error = GLES20.glGetError()
        if (error != GLES20.GL_NO_ERROR) {
            Log.e(TAG, "$op: glError $error")
            throw RuntimeException("$op: glError $error")
        }
    }
} 