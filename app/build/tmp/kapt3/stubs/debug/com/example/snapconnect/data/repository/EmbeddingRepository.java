package com.example.snapconnect.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J6\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/example/snapconnect/data/repository/EmbeddingRepository;", "", "api", "Lcom/example/snapconnect/data/remote/EmbeddingApi;", "(Lcom/example/snapconnect/data/remote/EmbeddingApi;)V", "embedImage", "Lcom/example/snapconnect/data/remote/EmbedResponseDto;", "bytes", "", "fileName", "", "userId", "tags", "", "([BLjava/lang/String;Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "embedText", "request", "Lcom/example/snapconnect/data/remote/EmbedRequestDto;", "(Lcom/example/snapconnect/data/remote/EmbedRequestDto;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class EmbeddingRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.remote.EmbeddingApi api = null;
    
    @javax.inject.Inject()
    public EmbeddingRepository(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.remote.EmbeddingApi api) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object embedText(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.remote.EmbedRequestDto request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.snapconnect.data.remote.EmbedResponseDto> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object embedImage(@org.jetbrains.annotations.NotNull()
    byte[] bytes, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> tags, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.snapconnect.data.remote.EmbedResponseDto> $completion) {
        return null;
    }
}