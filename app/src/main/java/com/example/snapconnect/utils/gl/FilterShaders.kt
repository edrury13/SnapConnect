package com.example.snapconnect.utils.gl

object FilterShaders {
    
    // Basic vertex shader for all filters
    const val VERTEX_SHADER = """
        attribute vec4 aPosition;
        attribute vec4 aTextureCoord;
        uniform mat4 uMVPMatrix;
        uniform mat4 uSTMatrix;
        varying vec2 vTextureCoord;
        
        void main() {
            gl_Position = aPosition;
            vTextureCoord = (uSTMatrix * aTextureCoord).xy;
        }
    """
    
    // Fragment shader for normal (no filter)
    const val FRAGMENT_SHADER_NORMAL = """
        #extension GL_OES_EGL_image_external : require
        precision mediump float;
        varying vec2 vTextureCoord;
        uniform samplerExternalOES sTexture;
        
        void main() {
            gl_FragColor = texture2D(sTexture, vTextureCoord);
        }
    """
    
    // Fragment shader for black and white filter
    const val FRAGMENT_SHADER_BLACK_WHITE = """
        #extension GL_OES_EGL_image_external : require
        precision mediump float;
        varying vec2 vTextureCoord;
        uniform samplerExternalOES sTexture;
        
        void main() {
            vec4 color = texture2D(sTexture, vTextureCoord);
            float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
            gl_FragColor = vec4(gray, gray, gray, color.a);
        }
    """
    
    // Fragment shader for vintage/sepia filter
    const val FRAGMENT_SHADER_VINTAGE = """
        #extension GL_OES_EGL_image_external : require
        precision mediump float;
        varying vec2 vTextureCoord;
        uniform samplerExternalOES sTexture;
        
        void main() {
            vec4 color = texture2D(sTexture, vTextureCoord);
            
            // Apply sepia tone
            float r = (color.r * 0.393) + (color.g * 0.769) + (color.b * 0.189);
            float g = (color.r * 0.349) + (color.g * 0.686) + (color.b * 0.168);
            float b = (color.r * 0.272) + (color.g * 0.534) + (color.b * 0.131);
            
            // Add slight vignette effect
            vec2 position = vTextureCoord - vec2(0.5);
            float vignette = 1.0 - dot(position, position) * 0.45;
            
            gl_FragColor = vec4(
                min(r * vignette, 1.0),
                min(g * vignette, 1.0),
                min(b * vignette, 1.0),
                color.a
            );
        }
    """
    
    // Fragment shader for posterize filter
    const val FRAGMENT_SHADER_POSTERIZE = """
        #extension GL_OES_EGL_image_external : require
        precision mediump float;
        varying vec2 vTextureCoord;
        uniform samplerExternalOES sTexture;
        
        void main() {
            vec4 color = texture2D(sTexture, vTextureCoord);
            
            // Posterize effect - reduce color levels
            float levels = 5.0;
            color.r = floor(color.r * levels) / levels;
            color.g = floor(color.g * levels) / levels;
            color.b = floor(color.b * levels) / levels;
            
            gl_FragColor = color;
        }
    """
    
    fun getFragmentShader(filterId: String?): String {
        return when (filterId) {
            "black_white" -> FRAGMENT_SHADER_BLACK_WHITE
            "vintage" -> FRAGMENT_SHADER_VINTAGE
            "posterize" -> FRAGMENT_SHADER_POSTERIZE
            else -> FRAGMENT_SHADER_NORMAL
        }
    }
} 