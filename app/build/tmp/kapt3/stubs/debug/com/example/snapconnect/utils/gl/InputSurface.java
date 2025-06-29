package com.example.snapconnect.utils.gl;

/**
 * Holds state associated with a Surface used for MediaCodec encoder input.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0006\u0010\u000f\u001a\u00020\fJ\u0006\u0010\u0010\u001a\u00020\fJ\u000e\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013J\b\u0010\u0014\u001a\u00020\fH\u0002J\u0006\u0010\u0015\u001a\u00020\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/example/snapconnect/utils/gl/InputSurface;", "", "surface", "Landroid/view/Surface;", "(Landroid/view/Surface;)V", "eglContext", "Landroid/opengl/EGLContext;", "eglDisplay", "Landroid/opengl/EGLDisplay;", "eglSurface", "Landroid/opengl/EGLSurface;", "checkEglError", "", "msg", "", "makeCurrent", "release", "setPresentationTime", "nsecs", "", "setupEGL", "swapBuffers", "app_debug"})
public final class InputSurface {
    @org.jetbrains.annotations.NotNull()
    private final android.view.Surface surface = null;
    @org.jetbrains.annotations.NotNull()
    private android.opengl.EGLDisplay eglDisplay;
    @org.jetbrains.annotations.NotNull()
    private android.opengl.EGLContext eglContext;
    @org.jetbrains.annotations.NotNull()
    private android.opengl.EGLSurface eglSurface;
    
    public InputSurface(@org.jetbrains.annotations.NotNull()
    android.view.Surface surface) {
        super();
    }
    
    private final void setupEGL() {
    }
    
    public final void makeCurrent() {
    }
    
    public final void swapBuffers() {
    }
    
    public final void setPresentationTime(long nsecs) {
    }
    
    public final void release() {
    }
    
    private final void checkEglError(java.lang.String msg) {
    }
}