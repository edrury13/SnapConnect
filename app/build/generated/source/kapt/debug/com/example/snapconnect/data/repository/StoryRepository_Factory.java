package com.example.snapconnect.data.repository;

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
public final class StoryRepository_Factory implements Factory<StoryRepository> {
  private final Provider<SupabaseClient> supabaseProvider;

  public StoryRepository_Factory(Provider<SupabaseClient> supabaseProvider) {
    this.supabaseProvider = supabaseProvider;
  }

  @Override
  public StoryRepository get() {
    return newInstance(supabaseProvider.get());
  }

  public static StoryRepository_Factory create(Provider<SupabaseClient> supabaseProvider) {
    return new StoryRepository_Factory(supabaseProvider);
  }

  public static StoryRepository newInstance(SupabaseClient supabase) {
    return new StoryRepository(supabase);
  }
}
