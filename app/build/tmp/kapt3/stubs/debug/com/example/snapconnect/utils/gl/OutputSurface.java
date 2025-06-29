package com.example.snapconnect.utils.gl;

/**
 * Holds state associated with a Surface used for MediaCodec decoder output.
 * The Surface is created from a SurfaceTexture that can be used to render filtered frames.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u0000 \'2\u00020\u0001:\u0001\'B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u001e\u001a\u00020\u001fJ\u0010\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\"H\u0002J\u0006\u0010#\u001a\u00020\u001fJ\u0010\u0010$\u001a\u00020\u001f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0006\u0010%\u001a\u00020\u001fJ\b\u0010&\u001a\u00020\u001fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/example/snapconnect/utils/gl/OutputSurface;", "Landroid/graphics/SurfaceTexture$OnFrameAvailableListener;", "filter", "Lcom/example/snapconnect/data/model/ARFilter;", "rotation", "", "needsFlip", "", "(Lcom/example/snapconnect/data/model/ARFilter;IZ)V", "frameAvailable", "frameSyncObject", "Ljava/lang/Object;", "mvpMatrix", "", "mvpMatrixHandle", "positionHandle", "program", "rotationMatrix", "stMatrix", "stMatrixHandle", "surface", "Landroid/view/Surface;", "getSurface", "()Landroid/view/Surface;", "surfaceTexture", "Landroid/graphics/SurfaceTexture;", "textureCoordHandle", "textureId", "triangleVertices", "Ljava/nio/FloatBuffer;", "awaitNewImage", "", "checkGlError", "op", "", "drawImage", "onFrameAvailable", "release", "setup", "Companion", "app_debug"})
public final class OutputSurface implements android.graphics.SurfaceTexture.OnFrameAvailableListener {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.model.ARFilter filter = null;
    private final int rotation = 0;
    private final boolean needsFlip = false;
    @org.jetbrains.annotations.Nullable()
    private android.graphics.SurfaceTexture surfaceTexture;
    @org.jetbrains.annotations.NotNull()
    private final android.view.Surface surface = null;
    private int program = 0;
    private int textureId = 0;
    private int positionHandle = 0;
    private int textureCoordHandle = 0;
    private int mvpMatrixHandle = 0;
    private int stMatrixHandle = 0;
    @org.jetbrains.annotations.NotNull()
    private final float[] mvpMatrix = null;
    @org.jetbrains.annotations.NotNull()
    private final float[] stMatrix = null;
    @org.jetbrains.annotations.NotNull()
    private final float[] rotationMatrix = null;
    private boolean frameAvailable = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object frameSyncObject = null;
    @org.jetbrains.annotations.NotNull()
    private final java.nio.FloatBuffer triangleVertices = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "OutputSurface";
    private static final int FLOAT_SIZE_BYTES = 4;
    private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 20;
    private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
    private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
    @org.jetbrains.annotations.NotNull()
    private static final float[] TRIANGLE_VERTICES_DATA = {-1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F};
    @org.jetbrains.annotations.NotNull()
    private static final float[] TRIANGLE_VERTICES_DATA_FLIPPED = {-1.0F, -1.0F, 0.0F, 1.0F, 0.0F, 1.0F, -1.0F, 0.0F, 0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F};
    @org.jetbrains.annotations.NotNull()
    public static final com.example.snapconnect.utils.gl.OutputSurface.Companion Companion = null;
    
    public OutputSurface(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.ARFilter filter, int rotation, boolean needsFlip) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.view.Surface getSurface() {
        return null;
    }
    
    private final void setup() {
    }
    
    @java.lang.Override()
    public void onFrameAvailable(@org.jetbrains.annotations.NotNull()
    android.graphics.SurfaceTexture surfaceTexture) {
    }
    
    public final void awaitNewImage() {
    }
    
    public final void drawImage() {
    }
    
    public final void release() {
    }
    
    private final void checkGlError(java.lang.String op) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/snapconnect/utils/gl/OutputSurface$Companion;", "", "()V", "FLOAT_SIZE_BYTES", "", "TAG", "", "TRIANGLE_VERTICES_DATA", "", "TRIANGLE_VERTICES_DATA_FLIPPED", "TRIANGLE_VERTICES_DATA_POS_OFFSET", "TRIANGLE_VERTICES_DATA_STRIDE_BYTES", "TRIANGLE_VERTICES_DATA_UV_OFFSET", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}