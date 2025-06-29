package com.example.snapconnect.utils.gl;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u0010\u0010\u0018\u001a\u00020\u000e2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/example/snapconnect/utils/gl/VideoFilterRenderer;", "", "()V", "frameAvailable", "", "mvpMatrix", "", "mvpMatrixHandle", "", "positionHandle", "program", "stMatrix", "stMatrixHandle", "surfaceTexture", "Landroid/graphics/SurfaceTexture;", "textureBuffer", "Ljava/nio/FloatBuffer;", "textureCoordHandle", "textureHandle", "textureId", "vertexBuffer", "draw", "", "release", "setup", "filterId", "", "Companion", "app_debug"})
public final class VideoFilterRenderer {
    private int program = 0;
    private int textureId = 0;
    @org.jetbrains.annotations.Nullable()
    private android.graphics.SurfaceTexture surfaceTexture;
    private boolean frameAvailable = false;
    @org.jetbrains.annotations.NotNull()
    private java.nio.FloatBuffer vertexBuffer;
    @org.jetbrains.annotations.NotNull()
    private java.nio.FloatBuffer textureBuffer;
    private int positionHandle = 0;
    private int textureCoordHandle = 0;
    private int mvpMatrixHandle = 0;
    private int stMatrixHandle = 0;
    private int textureHandle = 0;
    @org.jetbrains.annotations.NotNull()
    private final float[] mvpMatrix = null;
    @org.jetbrains.annotations.NotNull()
    private final float[] stMatrix = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "VideoFilterRenderer";
    private static final int FLOAT_SIZE_BYTES = 4;
    private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 20;
    private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
    private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
    @org.jetbrains.annotations.NotNull()
    private static final float[] VERTEX_COORDS = {-1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F};
    @org.jetbrains.annotations.NotNull()
    public static final com.example.snapconnect.utils.gl.VideoFilterRenderer.Companion Companion = null;
    
    public VideoFilterRenderer() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.SurfaceTexture setup(@org.jetbrains.annotations.Nullable()
    java.lang.String filterId) {
        return null;
    }
    
    public final void draw() {
    }
    
    public final void release() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0014\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/example/snapconnect/utils/gl/VideoFilterRenderer$Companion;", "", "()V", "FLOAT_SIZE_BYTES", "", "TAG", "", "TRIANGLE_VERTICES_DATA_POS_OFFSET", "TRIANGLE_VERTICES_DATA_STRIDE_BYTES", "TRIANGLE_VERTICES_DATA_UV_OFFSET", "VERTEX_COORDS", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}