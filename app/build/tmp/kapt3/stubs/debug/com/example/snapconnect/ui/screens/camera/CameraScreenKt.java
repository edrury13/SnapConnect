package com.example.snapconnect.ui.screens.camera;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0080\u0001\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001c\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0007\u001a$\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001aJ\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u00122\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u0012H\u0002\u001a\\\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2$\u0010 \u001a \u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0#\u0012\u0004\u0012\u00020\u00010!H\u0002\u001aR\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010\u000b\u001a\u00020\f2\u000e\u0010\'\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010#2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u00122\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00010\u0012H\u0002\u001a\u0012\u0010*\u001a\u00020\u00012\b\u0010+\u001a\u0004\u0018\u00010&H\u0002\u00a8\u0006,"}, d2 = {"CameraScreen", "", "navController", "Landroidx/navigation/NavController;", "groupId", "", "PermissionRequestScreen", "onRequestPermission", "Lkotlin/Function0;", "onBack", "capturePhoto", "context", "Landroid/content/Context;", "imageCapture", "Landroidx/camera/core/ImageCapture;", "executor", "Ljava/util/concurrent/Executor;", "onPhotoCaptured", "Lkotlin/Function1;", "Landroid/net/Uri;", "onError", "Landroidx/camera/core/ImageCaptureException;", "startCamera", "lifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "cameraProviderFuture", "Lcom/google/common/util/concurrent/ListenableFuture;", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "previewView", "Landroidx/camera/view/PreviewView;", "isFrontCamera", "", "onCameraReady", "Lkotlin/Function3;", "Landroidx/camera/core/Camera;", "Landroidx/camera/video/VideoCapture;", "Landroidx/camera/video/Recorder;", "startVideoRecording", "Landroidx/camera/video/Recording;", "videoCapture", "onVideoSaved", "Landroidx/camera/video/VideoRecordEvent;", "stopRecording", "recording", "app_debug"})
public final class CameraScreenKt {
    
    @kotlin.OptIn(markerClass = {com.google.accompanist.permissions.ExperimentalPermissionsApi.class, androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void CameraScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.Nullable()
    java.lang.String groupId) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PermissionRequestScreen(kotlin.jvm.functions.Function0<kotlin.Unit> onRequestPermission, kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    private static final void startCamera(android.content.Context context, androidx.lifecycle.LifecycleOwner lifecycleOwner, com.google.common.util.concurrent.ListenableFuture<androidx.camera.lifecycle.ProcessCameraProvider> cameraProviderFuture, androidx.camera.view.PreviewView previewView, boolean isFrontCamera, kotlin.jvm.functions.Function3<? super androidx.camera.core.Camera, ? super androidx.camera.core.ImageCapture, ? super androidx.camera.video.VideoCapture<androidx.camera.video.Recorder>, kotlin.Unit> onCameraReady) {
    }
    
    private static final void capturePhoto(android.content.Context context, androidx.camera.core.ImageCapture imageCapture, java.util.concurrent.Executor executor, kotlin.jvm.functions.Function1<? super android.net.Uri, kotlin.Unit> onPhotoCaptured, kotlin.jvm.functions.Function1<? super androidx.camera.core.ImageCaptureException, kotlin.Unit> onError) {
    }
    
    private static final androidx.camera.video.Recording startVideoRecording(android.content.Context context, androidx.camera.video.VideoCapture<androidx.camera.video.Recorder> videoCapture, java.util.concurrent.Executor executor, kotlin.jvm.functions.Function1<? super android.net.Uri, kotlin.Unit> onVideoSaved, kotlin.jvm.functions.Function1<? super androidx.camera.video.VideoRecordEvent, kotlin.Unit> onError) {
        return null;
    }
    
    private static final void stopRecording(androidx.camera.video.Recording recording) {
    }
}