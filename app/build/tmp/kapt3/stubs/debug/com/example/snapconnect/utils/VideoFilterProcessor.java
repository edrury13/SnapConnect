package com.example.snapconnect.utils;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 &2\u00020\u0001:\u0001&B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004JJ\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0007H\u0002JT\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eH\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\'"}, d2 = {"Lcom/example/snapconnect/utils/VideoFilterProcessor;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "applyFilterToVideo", "Lkotlin/Result;", "Landroid/net/Uri;", "inputVideoUri", "outputFile", "Ljava/io/File;", "filter", "Lcom/example/snapconnect/data/model/ARFilter;", "onProgress", "Lkotlin/Function1;", "", "", "applyFilterToVideo-yxL6bBk", "(Landroid/net/Uri;Ljava/io/File;Lcom/example/snapconnect/data/model/ARFilter;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkIfVideoNeedsFlip", "", "videoUri", "doExtractDecodeEditEncodeMux", "extractor", "Landroid/media/MediaExtractor;", "decoder", "Landroid/media/MediaCodec;", "encoder", "muxer", "Landroid/media/MediaMuxer;", "inputSurface", "Lcom/example/snapconnect/utils/gl/InputSurface;", "outputSurface", "Lcom/example/snapconnect/utils/gl/OutputSurface;", "duration", "", "selectVideoTrack", "", "Companion", "app_debug"})
public final class VideoFilterProcessor {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "VideoFilterProcessor";
    private static final long TIMEOUT_US = 10000L;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String MIME_TYPE = "video/avc";
    private static final int BIT_RATE = 5000000;
    private static final int FRAME_RATE = 30;
    private static final int I_FRAME_INTERVAL = 1;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.snapconnect.utils.VideoFilterProcessor.Companion Companion = null;
    
    @javax.inject.Inject()
    public VideoFilterProcessor(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final int selectVideoTrack(android.media.MediaExtractor extractor) {
        return 0;
    }
    
    private final void doExtractDecodeEditEncodeMux(android.media.MediaExtractor extractor, android.media.MediaCodec decoder, android.media.MediaCodec encoder, android.media.MediaMuxer muxer, com.example.snapconnect.utils.gl.InputSurface inputSurface, com.example.snapconnect.utils.gl.OutputSurface outputSurface, long duration, kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onProgress) {
    }
    
    private final boolean checkIfVideoNeedsFlip(android.net.Uri videoUri) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/example/snapconnect/utils/VideoFilterProcessor$Companion;", "", "()V", "BIT_RATE", "", "FRAME_RATE", "I_FRAME_INTERVAL", "MIME_TYPE", "", "TAG", "TIMEOUT_US", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}