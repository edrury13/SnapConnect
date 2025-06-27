package com.example.snapconnect.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0086@\u00a2\u0006\u0002\u0010\fJ@\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0005H\u0086@\u00a2\u0006\u0002\u0010\u0014R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/example/snapconnect/data/remote/LangchainApi;", "", "client", "Lio/ktor/client/HttpClient;", "baseUrl", "", "apiKey", "(Lio/ktor/client/HttpClient;Ljava/lang/String;Ljava/lang/String;)V", "autoCaption", "Lcom/example/snapconnect/data/remote/AutoCaptionResponse;", "userId", "tagsCsv", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processPost", "Lcom/example/snapconnect/data/remote/ProcessPostResponse;", "storyId", "caption", "tags", "", "imageUrl", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class LangchainApi {
    @org.jetbrains.annotations.NotNull()
    private final io.ktor.client.HttpClient client = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String baseUrl = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String apiKey = null;
    
    public LangchainApi(@org.jetbrains.annotations.NotNull()
    io.ktor.client.HttpClient client, @org.jetbrains.annotations.NotNull()
    java.lang.String baseUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object autoCaption(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String tagsCsv, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.snapconnect.data.remote.AutoCaptionResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object processPost(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String storyId, @org.jetbrains.annotations.NotNull()
    java.lang.String caption, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> tags, @org.jetbrains.annotations.Nullable()
    java.lang.String imageUrl, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.snapconnect.data.remote.ProcessPostResponse> $completion) {
        return null;
    }
}