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
public final class StorageRepository_Factory implements Factory<StorageRepository> {
  private final Provider<SupabaseClient> supabaseProvider;

  private final Provider<Context> contextProvider;

  public StorageRepository_Factory(Provider<SupabaseClient> supabaseProvider,
      Provider<Context> contextProvider) {
    this.supabaseProvider = supabaseProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public StorageRepository get() {
    return newInstance(supabaseProvider.get(), contextProvider.get());
  }

  public static StorageRepository_Factory create(Provider<SupabaseClient> supabaseProvider,
      Provider<Context> contextProvider) {
    return new StorageRepository_Factory(supabaseProvider, contextProvider);
  }

  public static StorageRepository newInstance(SupabaseClient supabase, Context context) {
    return new StorageRepository(supabase, context);
  }
}
