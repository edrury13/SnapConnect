package com.example.snapconnect.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ8\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\n2\u0006\u0010\u0015\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\"\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00190\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\"\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00190\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001d\u0010\u001bJ\u001c\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b \u0010\u001bJ\u0012\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00190\"J$\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00140\n2\u0006\u0010\u0015\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b$\u0010\u0017J\u000e\u0010%\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006&"}, d2 = {"Lcom/example/snapconnect/data/repository/StoryRepository;", "", "supabase", "Lio/github/jan/supabase/SupabaseClient;", "embeddingRepo", "Lcom/example/snapconnect/data/repository/EmbeddingRepository;", "langchainRepo", "Lcom/example/snapconnect/data/repository/LangchainRepository;", "(Lio/github/jan/supabase/SupabaseClient;Lcom/example/snapconnect/data/repository/EmbeddingRepository;Lcom/example/snapconnect/data/repository/LangchainRepository;)V", "createStory", "Lkotlin/Result;", "Lcom/example/snapconnect/data/model/Story;", "mediaUrl", "", "mediaType", "Lcom/example/snapconnect/data/model/MediaType;", "caption", "createStory-BWLJW6A", "(Ljava/lang/String;Lcom/example/snapconnect/data/model/MediaType;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteStory", "", "storyId", "deleteStory-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFriendsStories", "", "getFriendsStories-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMyStories", "getMyStories-IoAF18A", "getMyStoriesCount", "", "getMyStoriesCount-IoAF18A", "getStoriesRealtime", "Lkotlinx/coroutines/flow/Flow;", "markStoryAsViewed", "markStoryAsViewed-gIAlu-s", "triggerStoryCleanup", "app_debug"})
public final class StoryRepository {
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.SupabaseClient supabase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.EmbeddingRepository embeddingRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.LangchainRepository langchainRepo = null;
    
    @javax.inject.Inject()
    public StoryRepository(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.SupabaseClient supabase, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.EmbeddingRepository embeddingRepo, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.LangchainRepository langchainRepo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.snapconnect.data.model.Story>> getStoriesRealtime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object triggerStoryCleanup(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}