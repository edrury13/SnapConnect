package com.example.snapconnect.data.repository;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.SupabaseClient;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class StorageRepository_Factory implements Factory<StorageRepository> {
  private final Provider<SupabaseClient> supabaseProvider;

  private final Provider<Context> contextProvider;

  private final Provider<EmbeddingRepository> embeddingRepoProvider;

  public StorageRepository_Factory(Provider<SupabaseClient> supabaseProvider,
      Provider<Context> contextProvider, Provider<EmbeddingRepository> embeddingRepoProvider) {
    this.supabaseProvider = supabaseProvider;
    this.contextProvider = contextProvider;
    this.embeddingRepoProvider = embeddingRepoProvider;
  }

  @Override
  public StorageRepository get() {
    return newInstance(supabaseProvider.get(), contextProvider.get(), embeddingRepoProvider.get());
  }

  public static StorageRepository_Factory create(Provider<SupabaseClient> supabaseProvider,
      Provider<Context> contextProvider, Provider<EmbeddingRepository> embeddingRepoProvider) {
    return new StorageRepository_Factory(supabaseProvider, contextProvider, embeddingRepoProvider);
  }

  public static StorageRepository newInstance(SupabaseClient supabase, Context context,
      EmbeddingRepository embeddingRepo) {
    return new StorageRepository(supabase, context, embeddingRepo);
  }
}
