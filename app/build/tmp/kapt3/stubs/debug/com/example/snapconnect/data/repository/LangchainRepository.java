package com.example.snapconnect.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\nJ4\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/example/snapconnect/data/repository/LangchainRepository;", "", "api", "Lcom/example/snapconnect/data/remote/LangchainApi;", "(Lcom/example/snapconnect/data/remote/LangchainApi;)V", "autoCaption", "Lcom/example/snapconnect/data/remote/AutoCaptionResponse;", "userId", "", "tagsCsv", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processPost", "Lcom/example/snapconnect/data/remote/ProcessPostResponse;", "storyId", "caption", "tags", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class LangchainRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.remote.LangchainApi api = null;
    
    @javax.inject.Inject()
    public LangchainRepository(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.remote.LangchainApi api) {
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
    java.util.List<java.lang.String> tags, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.snapconnect.data.remote.ProcessPostResponse> $completion) {
        return null;
    }
}