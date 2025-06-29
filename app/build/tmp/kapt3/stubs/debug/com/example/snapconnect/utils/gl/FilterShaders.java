package com.example.snapconnect.utils.gl;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/example/snapconnect/utils/gl/FilterShaders;", "", "()V", "FRAGMENT_SHADER_BLACK_WHITE", "", "FRAGMENT_SHADER_NORMAL", "FRAGMENT_SHADER_POSTERIZE", "FRAGMENT_SHADER_VINTAGE", "VERTEX_SHADER", "getFragmentShader", "filterId", "app_debug"})
public final class FilterShaders {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String VERTEX_SHADER = "\n        attribute vec4 aPosition;\n        attribute vec4 aTextureCoord;\n        uniform mat4 uMVPMatrix;\n        uniform mat4 uSTMatrix;\n        varying vec2 vTextureCoord;\n        \n        void main() {\n            gl_Position = aPosition;\n            vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FRAGMENT_SHADER_NORMAL = "\n        #extension GL_OES_EGL_image_external : require\n        precision mediump float;\n        varying vec2 vTextureCoord;\n        uniform samplerExternalOES sTexture;\n        \n        void main() {\n            gl_FragColor = texture2D(sTexture, vTextureCoord);\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FRAGMENT_SHADER_BLACK_WHITE = "\n        #extension GL_OES_EGL_image_external : require\n        precision mediump float;\n        varying vec2 vTextureCoord;\n        uniform samplerExternalOES sTexture;\n        \n        void main() {\n            vec4 color = texture2D(sTexture, vTextureCoord);\n            float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));\n            gl_FragColor = vec4(gray, gray, gray, color.a);\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FRAGMENT_SHADER_VINTAGE = "\n        #extension GL_OES_EGL_image_external : require\n        precision mediump float;\n        varying vec2 vTextureCoord;\n        uniform samplerExternalOES sTexture;\n        \n        void main() {\n            vec4 color = texture2D(sTexture, vTextureCoord);\n            \n            // Apply sepia tone\n            float r = (color.r * 0.393) + (color.g * 0.769) + (color.b * 0.189);\n            float g = (color.r * 0.349) + (color.g * 0.686) + (color.b * 0.168);\n            float b = (color.r * 0.272) + (color.g * 0.534) + (color.b * 0.131);\n            \n            // Add slight vignette effect\n            vec2 position = vTextureCoord - vec2(0.5);\n            float vignette = 1.0 - dot(position, position) * 0.45;\n            \n            gl_FragColor = vec4(\n                min(r * vignette, 1.0),\n                min(g * vignette, 1.0),\n                min(b * vignette, 1.0),\n                color.a\n            );\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FRAGMENT_SHADER_POSTERIZE = "\n        #extension GL_OES_EGL_image_external : require\n        precision mediump float;\n        varying vec2 vTextureCoord;\n        uniform samplerExternalOES sTexture;\n        \n        void main() {\n            vec4 color = texture2D(sTexture, vTextureCoord);\n            \n            // Posterize effect - reduce color levels\n            float levels = 5.0;\n            color.r = floor(color.r * levels) / levels;\n            color.g = floor(color.g * levels) / levels;\n            color.b = floor(color.b * levels) / levels;\n            \n            gl_FragColor = color;\n        }\n    ";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.snapconnect.utils.gl.FilterShaders INSTANCE = null;
    
    private FilterShaders() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFragmentShader(@org.jetbrains.annotations.Nullable()
    java.lang.String filterId) {
        return null;
    }
}