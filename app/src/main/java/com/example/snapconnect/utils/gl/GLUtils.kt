package com.example.snapconnect.utils.gl

import android.opengl.GLES20
import android.util.Log

object GLUtils {
    private const val TAG = "GLUtils"
    
    fun createProgram(vertexShader: String, fragmentShader: String): Int {
        val vertexShaderId = loadShader(GLES20.GL_VERTEX_SHADER, vertexShader)
        if (vertexShaderId == 0) return 0
        
        val fragmentShaderId = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader)
        if (fragmentShaderId == 0) return 0
        
        val program = GLES20.glCreateProgram()
        if (program == 0) {
            Log.e(TAG, "Could not create program")
            return 0
        }
        
        GLES20.glAttachShader(program, vertexShaderId)
        checkGlError("glAttachShader vertex")
        
        GLES20.glAttachShader(program, fragmentShaderId)
        checkGlError("glAttachShader fragment")
        
        GLES20.glLinkProgram(program)
        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0)
        
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e(TAG, "Could not link program: ${GLES20.glGetProgramInfoLog(program)}")
            GLES20.glDeleteProgram(program)
            return 0
        }
        
        return program
    }
    
    private fun loadShader(shaderType: Int, source: String): Int {
        val shader = GLES20.glCreateShader(shaderType)
        if (shader == 0) {
            Log.e(TAG, "Could not create shader")
            return 0
        }
        
        GLES20.glShaderSource(shader, source)
        GLES20.glCompileShader(shader)
        
        val compiled = IntArray(1)
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0)
        
        if (compiled[0] == 0) {
            Log.e(TAG, "Could not compile shader $shaderType: ${GLES20.glGetShaderInfoLog(shader)}")
            GLES20.glDeleteShader(shader)
            return 0
        }
        
        return shader
    }
    
    fun checkGlError(op: String) {
        val error = GLES20.glGetError()
        if (error != GLES20.GL_NO_ERROR) {
            Log.e(TAG, "$op: glError $error")
            throw RuntimeException("$op: glError $error")
        }
    }
} 