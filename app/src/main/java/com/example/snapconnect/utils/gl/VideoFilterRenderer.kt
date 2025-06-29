package com.example.snapconnect.utils.gl

import android.graphics.SurfaceTexture
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.Matrix
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class VideoFilterRenderer {
    private var program = 0
    private var textureId = 0
    private var surfaceTexture: SurfaceTexture? = null
    private var frameAvailable = false
    
    private var vertexBuffer: FloatBuffer
    private var textureBuffer: FloatBuffer
    
    private var positionHandle = 0
    private var textureCoordHandle = 0
    private var mvpMatrixHandle = 0
    private var stMatrixHandle = 0
    private var textureHandle = 0
    
    private val mvpMatrix = FloatArray(16)
    private val stMatrix = FloatArray(16)
    
    companion object {
        private const val TAG = "VideoFilterRenderer"
        private const val FLOAT_SIZE_BYTES = 4
        private const val TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 5 * FLOAT_SIZE_BYTES
        private const val TRIANGLE_VERTICES_DATA_POS_OFFSET = 0
        private const val TRIANGLE_VERTICES_DATA_UV_OFFSET = 3
        
        private val VERTEX_COORDS = floatArrayOf(
            -1.0f, -1.0f, 0f, 0f, 0f,
             1.0f, -1.0f, 0f, 1f, 0f,
            -1.0f,  1.0f, 0f, 0f, 1f,
             1.0f,  1.0f, 0f, 1f, 1f
        )
    }
    
    init {
        vertexBuffer = ByteBuffer.allocateDirect(VERTEX_COORDS.size * FLOAT_SIZE_BYTES)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(VERTEX_COORDS)
        vertexBuffer.position(0)
        
        val textureCoords = floatArrayOf(
            0f, 0f,
            1f, 0f,
            0f, 1f,
            1f, 1f
        )
        
        textureBuffer = ByteBuffer.allocateDirect(textureCoords.size * FLOAT_SIZE_BYTES)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(textureCoords)
        textureBuffer.position(0)
        
        Matrix.setIdentityM(mvpMatrix, 0)
        Matrix.setIdentityM(stMatrix, 0)
    }
    
    fun setup(filterId: String?): SurfaceTexture {
        // Create texture
        val textures = IntArray(1)
        GLES20.glGenTextures(1, textures, 0)
        textureId = textures[0]
        
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId)
        GLUtils.checkGlError("glBindTexture")
        
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST.toFloat())
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
        
        // Create program
        val fragmentShader = FilterShaders.getFragmentShader(filterId)
        program = GLUtils.createProgram(FilterShaders.VERTEX_SHADER, fragmentShader)
        if (program == 0) {
            throw RuntimeException("failed creating program")
        }
        
        // Get handles
        positionHandle = GLES20.glGetAttribLocation(program, "aPosition")
        GLUtils.checkGlError("glGetAttribLocation aPosition")
        
        textureCoordHandle = GLES20.glGetAttribLocation(program, "aTextureCoord")
        GLUtils.checkGlError("glGetAttribLocation aTextureCoord")
        
        mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
        stMatrixHandle = GLES20.glGetUniformLocation(program, "uSTMatrix")
        textureHandle = GLES20.glGetUniformLocation(program, "sTexture")
        
        // Create surface texture
        surfaceTexture = SurfaceTexture(textureId)
        surfaceTexture?.setOnFrameAvailableListener {
            synchronized(this) {
                frameAvailable = true
            }
        }
        
        return surfaceTexture!!
    }
    
    fun draw() {
        synchronized(this) {
            if (frameAvailable) {
                surfaceTexture?.updateTexImage()
                surfaceTexture?.getTransformMatrix(stMatrix)
                frameAvailable = false
            }
        }
        
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        
        GLES20.glUseProgram(program)
        GLUtils.checkGlError("glUseProgram")
        
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId)
        
        vertexBuffer.position(TRIANGLE_VERTICES_DATA_POS_OFFSET)
        GLES20.glVertexAttribPointer(
            positionHandle, 3, GLES20.GL_FLOAT, false,
            TRIANGLE_VERTICES_DATA_STRIDE_BYTES, vertexBuffer
        )
        GLES20.glEnableVertexAttribArray(positionHandle)
        
        vertexBuffer.position(TRIANGLE_VERTICES_DATA_UV_OFFSET)
        GLES20.glVertexAttribPointer(
            textureCoordHandle, 2, GLES20.GL_FLOAT, false,
            TRIANGLE_VERTICES_DATA_STRIDE_BYTES, vertexBuffer
        )
        GLES20.glEnableVertexAttribArray(textureCoordHandle)
        
        if (mvpMatrixHandle != -1) {
            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)
        }
        
        if (stMatrixHandle != -1) {
            GLES20.glUniformMatrix4fv(stMatrixHandle, 1, false, stMatrix, 0)
        }
        
        GLES20.glUniform1i(textureHandle, 0)
        
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLUtils.checkGlError("glDrawArrays")
        
        GLES20.glDisableVertexAttribArray(positionHandle)
        GLES20.glDisableVertexAttribArray(textureCoordHandle)
    }
    
    fun release() {
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
} 