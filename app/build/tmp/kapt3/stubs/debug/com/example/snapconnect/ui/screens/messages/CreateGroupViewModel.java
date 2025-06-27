package com.example.snapconnect.ui.screens.messages;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0016B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u000fH\u0002J\u000e\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0012R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0017"}, d2 = {"Lcom/example/snapconnect/ui/screens/messages/CreateGroupViewModel;", "Landroidx/lifecycle/ViewModel;", "friendRepository", "Lcom/example/snapconnect/data/repository/FriendRepository;", "messagesRepository", "Lcom/example/snapconnect/data/repository/MessagesRepository;", "(Lcom/example/snapconnect/data/repository/FriendRepository;Lcom/example/snapconnect/data/repository/MessagesRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/snapconnect/ui/screens/messages/CreateGroupViewModel$UiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "createGroup", "name", "", "loadFriends", "toggleFriendSelection", "userId", "UiState", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CreateGroupViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.FriendRepository friendRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.MessagesRepository messagesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.snapconnect.ui.screens.messages.CreateGroupViewModel.UiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.messages.CreateGroupViewModel.UiState> uiState = null;
    
    @javax.inject.Inject()
    public CreateGroupViewModel(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.FriendRepository friendRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.MessagesRepository messagesRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.screens.messages.CreateGroupViewModel.UiState> getUiState() {
        return null;
    }
    
    public final void toggleFriendSelection(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    private final void loadFriends() {
    }
    
    public final void createGroup(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void clearError() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\tH\u00c6\u0003JK\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\tH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u00032\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\tH\u00d6\u0001R\u0013\u0010\n\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0012R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006 "}, d2 = {"Lcom/example/snapconnect/ui/screens/messages/CreateGroupViewModel$UiState;", "", "isLoading", "", "friends", "", "Lcom/example/snapconnect/data/model/User;", "selectedFriends", "", "", "errorMessage", "groupCreatedId", "(ZLjava/util/List;Ljava/util/Set;Ljava/lang/String;Ljava/lang/String;)V", "getErrorMessage", "()Ljava/lang/String;", "getFriends", "()Ljava/util/List;", "getGroupCreatedId", "()Z", "getSelectedFriends", "()Ljava/util/Set;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class UiState {
        private final boolean isLoading = false;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.example.snapconnect.data.model.User> friends = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<java.lang.String> selectedFriends = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String errorMessage = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String groupCreatedId = null;
        
        public UiState(boolean isLoading, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.snapconnect.data.model.User> friends, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> selectedFriends, @org.jetbrains.annotations.Nullable()
        java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
        java.lang.String groupCreatedId) {
            super();
        }
        
        public final boolean isLoading() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.snapconnect.data.model.User> getFriends() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> getSelectedFriends() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getErrorMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getGroupCreatedId() {
            return null;
        }
        
        public UiState() {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.snapconnect.data.model.User> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.snapconnect.ui.screens.messages.CreateGroupViewModel.UiState copy(boolean isLoading, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.snapconnect.data.model.User> friends, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> selectedFriends, @org.jetbrains.annotations.Nullable()
        java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
        java.lang.String groupCreatedId) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}