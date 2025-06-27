package com.example.snapconnect.ui.screens.camera;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u0012\u001a\u00020\u0013J6\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u00182\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001aJ4\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001e\u001a\u00020\u001a2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001aR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001f"}, d2 = {"Lcom/example/snapconnect/ui/screens/camera/StoryPostViewModel;", "Landroidx/lifecycle/ViewModel;", "storyRepository", "Lcom/example/snapconnect/data/repository/StoryRepository;", "storageRepository", "Lcom/example/snapconnect/data/repository/StorageRepository;", "authRepository", "Lcom/example/snapconnect/data/repository/AuthRepository;", "messagesRepository", "Lcom/example/snapconnect/data/repository/MessagesRepository;", "(Lcom/example/snapconnect/data/repository/StoryRepository;Lcom/example/snapconnect/data/repository/StorageRepository;Lcom/example/snapconnect/data/repository/AuthRepository;Lcom/example/snapconnect/data/repository/MessagesRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/snapconnect/ui/screens/camera/StoryPostUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearState", "", "postStory", "mediaUri", "Landroid/net/Uri;", "isVideo", "", "caption", "", "isPublic", "filterId", "sendToChat", "groupId", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class StoryPostViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.StoryRepository storyRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.StorageRepository storageRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.MessagesRepository messagesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.snapconnect.ui.screens.camera.StoryPostUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.camera.StoryPostUiState> uiState = null;
    
    @javax.inject.Inject()
    public StoryPostViewModel(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.StoryRepository storyRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.StorageRepository storageRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.MessagesRepository messagesRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.camera.StoryPostUiState> getUiState() {
        return null;
    }
    
    public final void postStory(@org.jetbrains.annotations.NotNull()
    android.net.Uri mediaUri, boolean isVideo, @org.jetbrains.annotations.Nullable()
    java.lang.String caption, boolean isPublic, @org.jetbrains.annotations.Nullable()
    java.lang.String filterId) {
    }
    
    public final void sendToChat(@org.jetbrains.annotations.NotNull()
    android.net.Uri mediaUri, boolean isVideo, @org.jetbrains.annotations.Nullable()
    java.lang.String caption, @org.jetbrains.annotations.NotNull()
    java.lang.String groupId, @org.jetbrains.annotations.Nullable()
    java.lang.String filterId) {
    }
    
    public final void clearState() {
    }
}