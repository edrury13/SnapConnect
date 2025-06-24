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
public final class CommentsRepository_Factory implements Factory<CommentsRepository> {
  private final Provider<SupabaseClient> supabaseProvider;

  public CommentsRepository_Factory(Provider<SupabaseClient> supabaseProvider) {
    this.supabaseProvider = supabaseProvider;
  }

  @Override
  public CommentsRepository get() {
    return newInstance(supabaseProvider.get());
  }

  public static CommentsRepository_Factory create(Provider<SupabaseClient> supabaseProvider) {
    return new CommentsRepository_Factory(supabaseProvider);
  }

  public static CommentsRepository newInstance(SupabaseClient supabase) {
    return new CommentsRepository(supabase);
  }
}
