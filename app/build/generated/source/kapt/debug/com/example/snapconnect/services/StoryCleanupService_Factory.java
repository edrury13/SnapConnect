package com.example.snapconnect.services;

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
public final class StoryCleanupService_Factory implements Factory<StoryCleanupService> {
  private final Provider<HttpClient> httpClientProvider;

  private final Provider<SupabaseClient> supabaseClientProvider;

  public StoryCleanupService_Factory(Provider<HttpClient> httpClientProvider,
      Provider<SupabaseClient> supabaseClientProvider) {
    this.httpClientProvider = httpClientProvider;
    this.supabaseClientProvider = supabaseClientProvider;
  }

  @Override
  public StoryCleanupService get() {
    return newInstance(httpClientProvider.get(), supabaseClientProvider.get());
  }

  public static StoryCleanupService_Factory create(Provider<HttpClient> httpClientProvider,
      Provider<SupabaseClient> supabaseClientProvider) {
    return new StoryCleanupService_Factory(httpClientProvider, supabaseClientProvider);
  }

  public static StoryCleanupService newInstance(HttpClient httpClient,
      SupabaseClient supabaseClient) {
    return new StoryCleanupService(httpClient, supabaseClient);
  }
}
