package com.example.snapconnect.ui.screens.story;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0018\b\u0086\b\u0018\u00002\u00020\u0001BM\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\rH\u00c6\u0003JQ\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u00c6\u0001J\u0013\u0010!\u001a\u00020\u00032\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020\u000bH\u00d6\u0001J\t\u0010$\u001a\u00020\rH\u00d6\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0019\u00a8\u0006%"}, d2 = {"Lcom/example/snapconnect/ui/screens/story/StoryViewUiState;", "", "isLoading", "", "currentStory", "Lcom/example/snapconnect/data/model/Story;", "currentUser", "Lcom/example/snapconnect/data/model/User;", "allUserStories", "", "currentIndex", "", "errorMessage", "", "(ZLcom/example/snapconnect/data/model/Story;Lcom/example/snapconnect/data/model/User;Ljava/util/List;ILjava/lang/String;)V", "getAllUserStories", "()Ljava/util/List;", "getCurrentIndex", "()I", "getCurrentStory", "()Lcom/example/snapconnect/data/model/Story;", "getCurrentUser", "()Lcom/example/snapconnect/data/model/User;", "getErrorMessage", "()Ljava/lang/String;", "()Z", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class StoryViewUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final com.example.snapconnect.data.model.Story currentStory = null;
    @org.jetbrains.annotations.Nullable()
    private final com.example.snapconnect.data.model.User currentUser = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.snapconnect.data.model.Story> allUserStories = null;
    private final int currentIndex = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    
    public StoryViewUiState(boolean isLoading, @org.jetbrains.annotations.Nullable()
    com.example.snapconnect.data.model.Story currentStory, @org.jetbrains.annotations.Nullable()
    com.example.snapconnect.data.model.User currentUser, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.snapconnect.data.model.Story> allUserStories, int currentIndex, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.snapconnect.data.model.Story getCurrentStory() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.snapconnect.data.model.User getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.snapconnect.data.model.Story> getAllUserStories() {
        return null;
    }
    
    public final int getCurrentIndex() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    public StoryViewUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.snapconnect.data.model.Story component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.snapconnect.data.model.User component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.snapconnect.data.model.Story> component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.ui.screens.story.StoryViewUiState copy(boolean isLoading, @org.jetbrains.annotations.Nullable()
    com.example.snapconnect.data.model.Story currentStory, @org.jetbrains.annotations.Nullable()
    com.example.snapconnect.data.model.User currentUser, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.snapconnect.data.model.Story> allUserStories, int currentIndex, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
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