package com.example.snapconnect.utils;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J8\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002J.\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u00162\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eJ\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u00182\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/example/snapconnect/utils/FilterProcessor;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "applyFilterToFace", "", "canvas", "Landroid/graphics/Canvas;", "face", "Lcom/google/mlkit/vision/face/Face;", "filter", "Lcom/example/snapconnect/data/model/ARFilter;", "isFrontCamera", "", "imageWidth", "", "imageHeight", "applyFilterToImage", "Landroid/graphics/Bitmap;", "originalBitmap", "faces", "", "getCenterPointForLandmark", "Landroid/graphics/PointF;", "landmarkType", "Lcom/example/snapconnect/data/model/FaceLandmarkType;", "getLandmarkPosition", "app_debug"})
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
    
    private final void applyFilterToFace(android.graphics.Canvas canvas, com.google.mlkit.vision.face.Face face, com.example.snapconnect.data.model.ARFilter filter, boolean isFrontCamera, int imageWidth, int imageHeight) {
    }
    
    private final android.graphics.PointF getLandmarkPosition(com.google.mlkit.vision.face.Face face, com.example.snapconnect.data.model.FaceLandmarkType landmarkType) {
        return null;
    }
    
    private final android.graphics.PointF getCenterPointForLandmark(com.google.mlkit.vision.face.Face face, com.example.snapconnect.data.model.FaceLandmarkType landmarkType) {
        return null;
    }
}