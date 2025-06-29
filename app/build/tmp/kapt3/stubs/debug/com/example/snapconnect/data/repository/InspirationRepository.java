package com.example.snapconnect.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ&\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\u000f2\u0006\u0010\u0010\u001a\u00020\n2\b\b\u0002\u0010\u0011\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/snapconnect/data/repository/InspirationRepository;", "", "api", "Lcom/example/snapconnect/data/remote/InspirationApi;", "httpClient", "Lio/ktor/client/HttpClient;", "(Lcom/example/snapconnect/data/remote/InspirationApi;Lio/ktor/client/HttpClient;)V", "analyseStyle", "Lcom/example/snapconnect/data/remote/StyleAnalysisResponse;", "caption", "", "limit", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateAiImages", "", "prompt", "n", "moodBoard", "Lcom/example/snapconnect/data/remote/MoodBoardResponse;", "app_debug"})
public final class InspirationRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.remote.InspirationApi api = null;
    @org.jetbrains.annotations.NotNull()
    private final io.ktor.client.HttpClient httpClient = null;
    
    @javax.inject.Inject()
    public InspirationRepository(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.remote.InspirationApi api, @org.jetbrains.annotations.NotNull()
    io.ktor.client.HttpClient httpClient) {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object generateAiImages(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt, int n, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
}