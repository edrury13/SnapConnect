package com.example.snapconnect.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.SupabaseClient;
import io.ktor.client.HttpClient;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class StoryRepository_Factory implements Factory<StoryRepository> {
  private final Provider<SupabaseClient> supabaseProvider;

  private final Provider<EmbeddingRepository> embeddingRepoProvider;

  private final Provider<LangchainRepository> langchainRepoProvider;

  private final Provider<VisionRepository> visionRepoProvider;

  private final Provider<HttpClient> httpClientProvider;

  public StoryRepository_Factory(Provider<SupabaseClient> supabaseProvider,
      Provider<EmbeddingRepository> embeddingRepoProvider,
      Provider<LangchainRepository> langchainRepoProvider,
      Provider<VisionRepository> visionRepoProvider, Provider<HttpClient> httpClientProvider) {
    this.supabaseProvider = supabaseProvider;
    this.embeddingRepoProvider = embeddingRepoProvider;
    this.langchainRepoProvider = langchainRepoProvider;
    this.visionRepoProvider = visionRepoProvider;
    this.httpClientProvider = httpClientProvider;
  }

  @Override
  public StoryRepository get() {
    return newInstance(supabaseProvider.get(), embeddingRepoProvider.get(), langchainRepoProvider.get(), visionRepoProvider.get(), httpClientProvider.get());
  }

  public static StoryRepository_Factory create(Provider<SupabaseClient> supabaseProvider,
      Provider<EmbeddingRepository> embeddingRepoProvider,
      Provider<LangchainRepository> langchainRepoProvider,
      Provider<VisionRepository> visionRepoProvider, Provider<HttpClient> httpClientProvider) {
    return new StoryRepository_Factory(supabaseProvider, embeddingRepoProvider, langchainRepoProvider, visionRepoProvider, httpClientProvider);
  }

  public static StoryRepository newInstance(SupabaseClient supabase,
      EmbeddingRepository embeddingRepo, LangchainRepository langchainRepo,
      VisionRepository visionRepo, HttpClient httpClient) {
    return new StoryRepository(supabase, embeddingRepo, langchainRepo, visionRepo, httpClient);
  }
}
