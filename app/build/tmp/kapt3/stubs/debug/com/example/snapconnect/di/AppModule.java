package com.example.snapconnect.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\tH\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0007J\b\u0010\u0012\u001a\u00020\u000eH\u0007J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u0018H\u0007J\u0012\u0010\u001b\u001a\u00020\u001c2\b\b\u0001\u0010\n\u001a\u00020\tH\u0007J\b\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\r\u001a\u00020\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/example/snapconnect/di/AppModule;", "", "()V", "API_KEY", "", "BACKEND_BASE_URL", "SUPABASE_ANON_KEY", "SUPABASE_URL", "provideContext", "Landroid/content/Context;", "context", "provideEmbeddingApi", "Lcom/example/snapconnect/data/remote/EmbeddingApi;", "client", "Lio/ktor/client/HttpClient;", "provideEmbeddingRepository", "Lcom/example/snapconnect/data/repository/EmbeddingRepository;", "api", "provideHttpClient", "provideInspirationApi", "Lcom/example/snapconnect/data/remote/InspirationApi;", "provideInspirationRepository", "Lcom/example/snapconnect/data/repository/InspirationRepository;", "provideLangchainApi", "Lcom/example/snapconnect/data/remote/LangchainApi;", "provideLangchainRepository", "Lcom/example/snapconnect/data/repository/LangchainRepository;", "provideSharedPreferences", "Landroid/content/SharedPreferences;", "provideSupabaseClient", "Lio/github/jan/supabase/SupabaseClient;", "provideVisionApi", "Lcom/example/snapconnect/data/remote/VisionApi;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SUPABASE_URL = "https://ngrbhordabshfvlbqmzu.supabase.co";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5ncmJob3JkYWJzaGZ2bGJxbXp1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTA3MDM3MzAsImV4cCI6MjA2NjI3OTczMH0.0Nse10raADtK8pU3V45hUKdQT4tyeYmvMLMw4P8MYvI";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BACKEND_BASE_URL = "https://snapconnect-backend.onrender.com";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String API_KEY = "RT5PrU6c8dnk5UUaP1dyDIqQjY6KuV2IXXKZvKvkMiz8N2AwrZhHJwYm8GZlCjQWPqaAB9UAMqwYkzPx67DQnx6muHfXscKpmmJVVVp9FWoyWIudwx35Qrv5H3TqwCnm";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.snapconnect.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final io.github.jan.supabase.SupabaseClient provideSupabaseClient() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context provideContext(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final android.content.SharedPreferences provideSharedPreferences(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final io.ktor.client.HttpClient provideHttpClient() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.data.remote.InspirationApi provideInspirationApi(@org.jetbrains.annotations.NotNull()
    io.ktor.client.HttpClient client) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.data.repository.InspirationRepository provideInspirationRepository(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.remote.InspirationApi api, @org.jetbrains.annotations.NotNull()
    io.ktor.client.HttpClient client) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.data.remote.EmbeddingApi provideEmbeddingApi(@org.jetbrains.annotations.NotNull()
    io.ktor.client.HttpClient client) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.data.repository.EmbeddingRepository provideEmbeddingRepository(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.remote.EmbeddingApi api) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.data.remote.LangchainApi provideLangchainApi(@org.jetbrains.annotations.NotNull()
    io.ktor.client.HttpClient client) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.data.repository.LangchainRepository provideLangchainRepository(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.remote.LangchainApi api) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.data.remote.VisionApi provideVisionApi(@org.jetbrains.annotations.NotNull()
    io.ktor.client.HttpClient client) {
        return null;
    }
}