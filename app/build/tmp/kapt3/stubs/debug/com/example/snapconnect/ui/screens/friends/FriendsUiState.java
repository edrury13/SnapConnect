package com.example.snapconnect.ui.screens.friends;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u00a9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u001a\b\u0002\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005\u0012\u001a\b\u0002\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005\u0012\u001a\b\u0002\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\r\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0012H\u00c6\u0003J\u001b\u0010\"\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005H\u00c6\u0003J\u001b\u0010#\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005H\u00c6\u0003J\u001b\u0010$\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005H\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\rH\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u00ad\u0001\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u001a\b\u0002\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u00052\u001a\b\u0002\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u00052\u001a\b\u0002\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u00052\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u00c6\u0001J\u0013\u0010+\u001a\u00020\u00032\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020.H\u00d6\u0001J\t\u0010/\u001a\u00020\rH\u00d6\u0001R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R#\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0018R#\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR#\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015\u00a8\u00060"}, d2 = {"Lcom/example/snapconnect/ui/screens/friends/FriendsUiState;", "", "isLoading", "", "friends", "", "Lkotlin/Pair;", "Lcom/example/snapconnect/data/model/Friend;", "Lcom/example/snapconnect/data/model/User;", "pendingRequests", "sentRequests", "searchResults", "searchQuery", "", "isSearching", "errorMessage", "successMessage", "selectedTab", "Lcom/example/snapconnect/ui/screens/friends/FriendsTab;", "(ZLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Lcom/example/snapconnect/ui/screens/friends/FriendsTab;)V", "getErrorMessage", "()Ljava/lang/String;", "getFriends", "()Ljava/util/List;", "()Z", "getPendingRequests", "getSearchQuery", "getSearchResults", "getSelectedTab", "()Lcom/example/snapconnect/ui/screens/friends/FriendsTab;", "getSentRequests", "getSuccessMessage", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class FriendsUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> friends = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> pendingRequests = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> sentRequests = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.snapconnect.data.model.User> searchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String searchQuery = null;
    private final boolean isSearching = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String successMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.ui.screens.friends.FriendsTab selectedTab = null;
    
    public FriendsUiState(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> friends, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> pendingRequests, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> sentRequests, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.snapconnect.data.model.User> searchResults, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, boolean isSearching, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String successMessage, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.ui.screens.friends.FriendsTab selectedTab) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> getFriends() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> getPendingRequests() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> getSentRequests() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.snapconnect.data.model.User> getSearchResults() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSearchQuery() {
        return null;
    }
    
    public final boolean isSearching() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSuccessMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.ui.screens.friends.FriendsTab getSelectedTab() {
        return null;
    }
    
    public FriendsUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.ui.screens.friends.FriendsTab component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.snapconnect.data.model.User> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.ui.screens.friends.FriendsUiState copy(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> friends, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> pendingRequests, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Friend, com.example.snapconnect.data.model.User>> sentRequests, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.snapconnect.data.model.User> searchResults, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, boolean isSearching, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String successMessage, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.ui.screens.friends.FriendsTab selectedTab) {
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