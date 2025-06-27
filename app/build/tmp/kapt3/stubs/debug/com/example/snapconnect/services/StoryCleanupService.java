package com.example.snapconnect.services;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0082@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\r\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u000f"}, d2 = {"Lcom/example/snapconnect/services/StoryCleanupService;", "", "httpClient", "Lio/ktor/client/HttpClient;", "supabaseClient", "Lio/github/jan/supabase/SupabaseClient;", "(Lio/ktor/client/HttpClient;Lio/github/jan/supabase/SupabaseClient;)V", "triggerCleanup", "Lkotlin/Result;", "", "triggerCleanup-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "triggerCleanupViaRpc", "triggerCleanupViaRpc-IoAF18A", "Companion", "app_debug"})
public final class StoryCleanupService {
    @org.jetbrains.annotations.NotNull()
    private final io.ktor.client.HttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.SupabaseClient supabaseClient = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SUPABASE_URL = "https://ngrbhordabshfvlbqmzu.supabase.co";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5ncmJob3JkYWJzaGZ2bGJxbXp1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTA3MDM3MzAsImV4cCI6MjA2NjI3OTczMH0.0Nse10raADtK8pU3V45hUKdQT4tyeYmvMLMw4P8MYvI";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.snapconnect.services.StoryCleanupService.Companion Companion = null;
    
    @javax.inject.Inject()
    public StoryCleanupService(@org.jetbrains.annotations.NotNull()
    io.ktor.client.HttpClient httpClient, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.SupabaseClient supabaseClient) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/example/snapconnect/services/StoryCleanupService$Companion;", "", "()V", "SUPABASE_ANON_KEY", "", "SUPABASE_URL", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}