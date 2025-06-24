package com.example.snapconnect.ui.screens.camera;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aN\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007\u001a0\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0007H\u0003\u001a@\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u00a8\u0006\u001c"}, d2 = {"MediaPreviewScreen", "", "navController", "Landroidx/navigation/NavController;", "mediaUri", "", "isVideo", "", "groupId", "filterId", "viewModel", "Lcom/example/snapconnect/ui/screens/camera/StoryPostViewModel;", "sendViewModel", "Lcom/example/snapconnect/ui/screens/camera/MediaSendViewModel;", "SendOptionButton", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "label", "onClick", "Lkotlin/Function0;", "enabled", "SendOptionsContent", "uiState", "Lcom/example/snapconnect/ui/screens/camera/MediaSendUiState;", "onToggleFriend", "Lkotlin/Function1;", "onSend", "onCancel", "app_debug"})
public final class MediaPreviewScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MediaPreviewScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    java.lang.String mediaUri, boolean isVideo, @org.jetbrains.annotations.Nullable()
    java.lang.String groupId, @org.jetbrains.annotations.Nullable()
    java.lang.String filterId, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.ui.screens.camera.StoryPostViewModel viewModel, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.ui.screens.camera.MediaSendViewModel sendViewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SendOptionButton(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String label, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, boolean enabled) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SendOptionsContent(com.example.snapconnect.ui.screens.camera.MediaSendUiState uiState, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onToggleFriend, kotlin.jvm.functions.Function0<kotlin.Unit> onSend, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel) {
    }
}