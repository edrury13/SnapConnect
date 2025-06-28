package com.example.snapconnect.utils;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J8\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J.\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00062\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001a2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0013J(\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J\u0010\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\u001a\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\"H\u0002J\u001a\u0010#\u001a\u0004\u0018\u00010 2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\"H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/example/snapconnect/utils/FilterProcessor;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "applyColorFilter", "Landroid/graphics/Bitmap;", "bitmap", "colorMatrix", "", "applyFilterToFace", "", "canvas", "Landroid/graphics/Canvas;", "face", "Lcom/google/mlkit/vision/face/Face;", "filter", "Lcom/example/snapconnect/data/model/ARFilter;", "isFrontCamera", "", "imageWidth", "", "imageHeight", "applyFilterToImage", "originalBitmap", "faces", "", "applyFullScreenOverlay", "overlay", "Lcom/example/snapconnect/data/model/FilterOverlay;", "applyPosterizeEffect", "getCenterPointForLandmark", "Landroid/graphics/PointF;", "landmarkType", "Lcom/example/snapconnect/data/model/FaceLandmarkType;", "getLandmarkPosition", "app_debug"})
public final class FilterProcessor {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public FilterProcessor(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap applyFilterToImage(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap originalBitmap, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.google.mlkit.vision.face.Face> faces, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.ARFilter filter, boolean isFrontCamera) {
        return null;
    }
    
    private final android.graphics.Bitmap applyColorFilter(android.graphics.Bitmap bitmap, float[] colorMatrix) {
        return null;
    }
    
    private final android.graphics.Bitmap applyPosterizeEffect(android.graphics.Bitmap bitmap) {
        return null;
    }
    
    private final void applyFullScreenOverlay(android.graphics.Canvas canvas, com.example.snapconnect.data.model.FilterOverlay overlay, int imageWidth, int imageHeight) {
    }
    
    private final void applyFilterToFace(android.graphics.Canvas canvas, com.google.mlkit.vision.face.Face face, com.example.snapconnect.data.model.ARFilter filter, boolean isFrontCamera, int imageWidth, int imageHeight) {
    }
    
    private final android.graphics.PointF getLandmarkPosition(com.google.mlkit.vision.face.Face face, com.example.snapconnect.data.model.FaceLandmarkType landmarkType) {
        return null;
    }
    
    private final android.graphics.PointF getCenterPointForLandmark(com.google.mlkit.vision.face.Face face, com.example.snapconnect.data.model.FaceLandmarkType landmarkType) {
        return null;
    }
}