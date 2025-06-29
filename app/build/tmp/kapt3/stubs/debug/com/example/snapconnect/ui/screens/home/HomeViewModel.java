package com.example.snapconnect.ui.screens.home;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u000e\u0010\u0018\u001a\u00020\u0016H\u0082@\u00a2\u0006\u0002\u0010\u0019J\u0010\u0010\u001a\u001a\u00020\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u0010R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/example/snapconnect/ui/screens/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "storyRepository", "Lcom/example/snapconnect/data/repository/StoryRepository;", "userRepository", "Lcom/example/snapconnect/data/repository/UserRepository;", "friendRepository", "Lcom/example/snapconnect/data/repository/FriendRepository;", "authRepository", "Lcom/example/snapconnect/data/repository/AuthRepository;", "(Lcom/example/snapconnect/data/repository/StoryRepository;Lcom/example/snapconnect/data/repository/UserRepository;Lcom/example/snapconnect/data/repository/FriendRepository;Lcom/example/snapconnect/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/snapconnect/ui/screens/home/HomeUiState;", "friendIds", "", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "loadStories", "loadStoriesWithoutRecommendations", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setStyleFilter", "style", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.StoryRepository storyRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.FriendRepository friendRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.snapconnect.ui.screens.home.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.home.HomeUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.Set<java.lang.String> friendIds;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.StoryRepository storyRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.FriendRepository friendRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.home.HomeUiState> getUiState() {
        return null;
    }
    
    public final void loadStories() {
    }
    
    private final java.lang.Object loadStoriesWithoutRecommendations(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void clearError() {
    }
    
    public final void setStyleFilter(@org.jetbrains.annotations.Nullable()
    java.lang.String style) {
    }
}