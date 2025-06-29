package com.example.snapconnect.ui.screens.story;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B7\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0015J\u000e\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00020\u0015J\b\u0010\u001e\u001a\u0004\u0018\u00010\u0015J\u0006\u0010\u001f\u001a\u00020 J\u0006\u0010!\u001a\u00020 J\u0010\u0010\"\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010#\u001a\u00020\u001bH\u0002J\u0010\u0010$\u001a\u00020\u001b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0006\u0010%\u001a\u00020\u001bJ\b\u0010&\u001a\u00020\u001bH\u0014J\u0006\u0010\'\u001a\u00020\u001bJ\u0006\u0010(\u001a\u00020\u001bJ\u000e\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\u0015J\u0006\u0010+\u001a\u00020\u001bJ\u000e\u0010,\u001a\u00020\u001b2\u0006\u0010-\u001a\u00020.J\u000e\u0010/\u001a\u00020\u001b2\u0006\u00100\u001a\u00020\u0015R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/example/snapconnect/ui/screens/story/StoryViewViewModel;", "Landroidx/lifecycle/ViewModel;", "storyRepository", "Lcom/example/snapconnect/data/repository/StoryRepository;", "userRepository", "Lcom/example/snapconnect/data/repository/UserRepository;", "authRepository", "Lcom/example/snapconnect/data/repository/AuthRepository;", "commentsRepository", "Lcom/example/snapconnect/data/repository/CommentsRepository;", "friendRepository", "Lcom/example/snapconnect/data/repository/FriendRepository;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "(Lcom/example/snapconnect/data/repository/StoryRepository;Lcom/example/snapconnect/data/repository/UserRepository;Lcom/example/snapconnect/data/repository/AuthRepository;Lcom/example/snapconnect/data/repository/CommentsRepository;Lcom/example/snapconnect/data/repository/FriendRepository;Landroidx/lifecycle/SavedStateHandle;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/snapconnect/ui/screens/story/StoryViewUiState;", "commentsJob", "Lkotlinx/coroutines/Job;", "storyId", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "deleteComment", "", "commentId", "deleteStory", "getCurrentUserId", "hasNextStory", "", "hasPreviousStory", "loadComments", "loadStory", "markAsViewed", "nextStory", "onCleared", "previousStory", "sendComment", "sendFriendRequest", "userId", "toggleComments", "toggleReaction", "reactionType", "Lcom/example/snapconnect/data/model/ReactionType;", "updateCommentInput", "text", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class StoryViewViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.StoryRepository storyRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.CommentsRepository commentsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.FriendRepository friendRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String storyId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.snapconnect.ui.screens.story.StoryViewUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.story.StoryViewUiState> uiState = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job commentsJob;
    
    @javax.inject.Inject()
    public StoryViewViewModel(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.StoryRepository storyRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.CommentsRepository commentsRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.FriendRepository friendRepository, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.story.StoryViewUiState> getUiState() {
        return null;
    }
    
    private final void loadStory() {
    }
    
    public final void nextStory() {
    }
    
    public final void previousStory() {
    }
    
    public final boolean hasNextStory() {
        return false;
    }
    
    public final boolean hasPreviousStory() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCurrentUserId() {
        return null;
    }
    
    private final void markAsViewed(java.lang.String storyId) {
    }
    
    public final void deleteStory(@org.jetbrains.annotations.NotNull()
    java.lang.String storyId) {
    }
    
    public final void toggleComments() {
    }
    
    private final void loadComments(java.lang.String storyId) {
    }
    
    public final void updateCommentInput(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void sendComment() {
    }
    
    public final void deleteComment(@org.jetbrains.annotations.NotNull()
    java.lang.String commentId) {
    }
    
    public final void toggleReaction(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.ReactionType reactionType) {
    }
    
    public final void sendFriendRequest(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}