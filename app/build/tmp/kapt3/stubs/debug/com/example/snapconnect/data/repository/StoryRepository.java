package com.example.snapconnect.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J8\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\r\u0010\u000eJ$\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00062\u0006\u0010\u0011\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\"\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00150\u0006H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\"\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00150\u0006H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u0017J\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0006H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u0017J\u0012\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00150\u001eJ$\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00062\u0006\u0010\u0011\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b \u0010\u0013J\u000e\u0010!\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\""}, d2 = {"Lcom/example/snapconnect/data/repository/StoryRepository;", "", "supabase", "Lio/github/jan/supabase/SupabaseClient;", "(Lio/github/jan/supabase/SupabaseClient;)V", "createStory", "Lkotlin/Result;", "Lcom/example/snapconnect/data/model/Story;", "mediaUrl", "", "mediaType", "Lcom/example/snapconnect/data/model/MediaType;", "caption", "createStory-BWLJW6A", "(Ljava/lang/String;Lcom/example/snapconnect/data/model/MediaType;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteStory", "", "storyId", "deleteStory-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFriendsStories", "", "getFriendsStories-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMyStories", "getMyStories-IoAF18A", "getMyStoriesCount", "", "getMyStoriesCount-IoAF18A", "getStoriesRealtime", "Lkotlinx/coroutines/flow/Flow;", "markStoryAsViewed", "markStoryAsViewed-gIAlu-s", "triggerStoryCleanup", "app_debug"})
public final class StoryRepository {
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.SupabaseClient supabase = null;
    
    @javax.inject.Inject()
    public StoryRepository(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.SupabaseClient supabase) {
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