package com.example.snapconnect.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/example/snapconnect/data/repository/InspirationRepository;", "", "api", "Lcom/example/snapconnect/data/remote/InspirationApi;", "(Lcom/example/snapconnect/data/remote/InspirationApi;)V", "analyseStyle", "Lcom/example/snapconnect/data/remote/StyleAnalysisResponse;", "caption", "", "limit", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "moodBoard", "Lcom/example/snapconnect/data/remote/MoodBoardResponse;", "prompt", "app_debug"})
public final class InspirationRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.remote.InspirationApi api = null;
    
    @javax.inject.Inject()
    public InspirationRepository(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.remote.InspirationApi api) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object moodBoard(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.snapconnect.data.remote.MoodBoardResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object analyseStyle(@org.jetbrains.annotations.NotNull()
    java.lang.String caption, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.snapconnect.data.remote.StyleAnalysisResponse> $completion) {
        return null;
    }
}