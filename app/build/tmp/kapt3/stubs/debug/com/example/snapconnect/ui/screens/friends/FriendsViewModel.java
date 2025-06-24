package com.example.snapconnect.ui.screens.friends;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u000fJ\u0006\u0010\u0013\u001a\u00020\u000fJ\b\u0010\u0014\u001a\u00020\u000fH\u0002J\b\u0010\u0015\u001a\u00020\u000fH\u0002J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u0011J\u000e\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u0011J\"\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u00112\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000f0\"R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006#"}, d2 = {"Lcom/example/snapconnect/ui/screens/friends/FriendsViewModel;", "Landroidx/lifecycle/ViewModel;", "friendRepository", "Lcom/example/snapconnect/data/repository/FriendRepository;", "messagesRepository", "Lcom/example/snapconnect/data/repository/MessagesRepository;", "(Lcom/example/snapconnect/data/repository/FriendRepository;Lcom/example/snapconnect/data/repository/MessagesRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/snapconnect/ui/screens/friends/FriendsUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "acceptFriendRequest", "", "friendshipId", "", "clearMessages", "clearSearch", "loadFriends", "loadRequests", "rejectFriendRequest", "removeFriend", "searchUsers", "query", "selectTab", "tab", "Lcom/example/snapconnect/ui/screens/friends/FriendsTab;", "sendFriendRequest", "username", "startChatWith", "friendId", "onSuccess", "Lkotlin/Function1;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class FriendsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.FriendRepository friendRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.MessagesRepository messagesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.snapconnect.ui.screens.friends.FriendsUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.friends.FriendsUiState> uiState = null;
    
    @javax.inject.Inject()
    public FriendsViewModel(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.FriendRepository friendRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.MessagesRepository messagesRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.friends.FriendsUiState> getUiState() {
        return null;
    }
    
    public final void selectTab(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.ui.screens.friends.FriendsTab tab) {
    }
    
    private final void loadFriends() {
    }
    
    private final void loadRequests() {
    }
    
    public final void searchUsers(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void sendFriendRequest(@org.jetbrains.annotations.NotNull()
    java.lang.String username) {
    }
    
    public final void acceptFriendRequest(@org.jetbrains.annotations.NotNull()
    java.lang.String friendshipId) {
    }
    
    public final void rejectFriendRequest(@org.jetbrains.annotations.NotNull()
    java.lang.String friendshipId) {
    }
    
    public final void removeFriend(@org.jetbrains.annotations.NotNull()
    java.lang.String friendshipId) {
    }
    
    public final void clearSearch() {
    }
    
    public final void clearMessages() {
    }
    
    public final void startChatWith(@org.jetbrains.annotations.NotNull()
    java.lang.String friendId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSuccess) {
    }
}