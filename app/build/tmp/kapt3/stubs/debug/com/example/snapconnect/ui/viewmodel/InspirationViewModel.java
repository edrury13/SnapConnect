package com.example.snapconnect.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0018B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0018\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00112\b\b\u0002\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0011J\u0018\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00112\b\b\u0002\u0010\u0014\u001a\u00020\u0015R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/snapconnect/ui/viewmodel/InspirationViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/example/snapconnect/data/repository/InspirationRepository;", "storyRepository", "Lcom/example/snapconnect/data/repository/StoryRepository;", "(Lcom/example/snapconnect/data/repository/InspirationRepository;Lcom/example/snapconnect/data/repository/StoryRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/snapconnect/ui/viewmodel/InspirationViewModel$UiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "analyseStyle", "", "caption", "", "generateAiImages", "prompt", "n", "", "generateMoodboard", "generateMoreAiImages", "UiState", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class InspirationViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.InspirationRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.StoryRepository storyRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.snapconnect.ui.viewmodel.InspirationViewModel.UiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.viewmodel.InspirationViewModel.UiState> state = null;
    
    @javax.inject.Inject()
    public InspirationViewModel(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.InspirationRepository repo, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.StoryRepository storyRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.snapconnect.ui.viewmodel.InspirationViewModel.UiState> getState() {
        return null;
    }
    
    public final void generateMoodboard(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt) {
    }
    
    public final void generateAiImages(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt, int n) {
    }
    
    public final void analyseStyle(@org.jetbrains.annotations.NotNull()
    java.lang.String caption) {
    }
    
    public final void generateMoreAiImages(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt, int n) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\bH\u00c6\u0003JI\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\bH\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\u00032\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\bH\u00d6\u0001R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\n\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\r\u00a8\u0006\u001f"}, d2 = {"Lcom/example/snapconnect/ui/viewmodel/InspirationViewModel$UiState;", "", "loading", "", "moodItems", "", "Lcom/example/snapconnect/data/remote/MoodBoardItem;", "aiImages", "", "loadingAi", "error", "(ZLjava/util/List;Ljava/util/List;ZLjava/lang/String;)V", "getAiImages", "()Ljava/util/List;", "getError", "()Ljava/lang/String;", "getLoading", "()Z", "getLoadingAi", "getMoodItems", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class UiState {
        private final boolean loading = false;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.example.snapconnect.data.remote.MoodBoardItem> moodItems = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> aiImages = null;
        private final boolean loadingAi = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String error = null;
        
        public UiState(boolean loading, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.snapconnect.data.remote.MoodBoardItem> moodItems, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> aiImages, boolean loadingAi, @org.jetbrains.annotations.Nullable()
        java.lang.String error) {
            super();
        }
        
        public final boolean getLoading() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.snapconnect.data.remote.MoodBoardItem> getMoodItems() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getAiImages() {
            return null;
        }
        
        public final boolean getLoadingAi() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getError() {
            return null;
        }
        
        public UiState() {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.snapconnect.data.remote.MoodBoardItem> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component3() {
            return null;
        }
        
        public final boolean component4() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.snapconnect.ui.viewmodel.InspirationViewModel.UiState copy(boolean loading, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.snapconnect.data.remote.MoodBoardItem> moodItems, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> aiImages, boolean loadingAi, @org.jetbrains.annotations.Nullable()
        java.lang.String error) {
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