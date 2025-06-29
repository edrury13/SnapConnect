package com.example.snapconnect.ui.screens.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0006\u0010\u0016\u001a\u00020\u0017J\b\u0010\u0018\u001a\u00020\u0017H\u0002J\u000e\u0010\u0019\u001a\u00020\u0017H\u0082@\u00a2\u0006\u0002\u0010\u001aJ\b\u0010\u001b\u001a\u00020\u0017H\u0002J\u0006\u0010\u001c\u001a\u00020\u0017J\u000e\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u0011R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/example/snapconnect/ui/screens/chat/ChatViewModel;", "Landroidx/lifecycle/ViewModel;", "messagesRepository", "Lcom/example/snapconnect/data/repository/MessagesRepository;", "userRepository", "Lcom/example/snapconnect/data/repository/UserRepository;", "authRepository", "Lcom/example/snapconnect/data/repository/AuthRepository;", "storageRepository", "Lcom/example/snapconnect/data/repository/StorageRepository;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "(Lcom/example/snapconnect/data/repository/MessagesRepository;Lcom/example/snapconnect/data/repository/UserRepository;Lcom/example/snapconnect/data/repository/AuthRepository;Lcom/example/snapconnect/data/repository/StorageRepository;Landroidx/lifecycle/SavedStateHandle;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/snapconnect/ui/screens/chat/ChatUiState;", "groupId", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "loadChatData", "loadGroupInfo", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeMessages", "sendMessage", "setGroupAvatar", "uri", "Landroid/net/Uri;", "updateMessageInput", "text", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChatViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.MessagesRepository messagesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.StorageRepository storageRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String groupId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.snapconnect.ui.screens.chat.ChatUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.chat.ChatUiState> uiState = null;
    
    @javax.inject.Inject()
    public ChatViewModel(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.MessagesRepository messagesRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.StorageRepository storageRepository, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.chat.ChatUiState> getUiState() {
        return null;
    }
    
    private final void loadChatData() {
    }
    
    private final java.lang.Object loadGroupInfo(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void observeMessages() {
    }
    
    public final void updateMessageInput(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void sendMessage() {
    }
    
    public final void clearError() {
    }
    
    public final void setGroupAvatar(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
}