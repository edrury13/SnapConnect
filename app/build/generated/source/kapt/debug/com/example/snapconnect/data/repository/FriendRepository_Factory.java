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
public final class FriendRepository_Factory implements Factory<FriendRepository> {
  private final Provider<SupabaseClient> supabaseProvider;

  public FriendRepository_Factory(Provider<SupabaseClient> supabaseProvider) {
    this.supabaseProvider = supabaseProvider;
  }

  @Override
  public FriendRepository get() {
    return newInstance(supabaseProvider.get());
  }

  public static FriendRepository_Factory create(Provider<SupabaseClient> supabaseProvider) {
    return new FriendRepository_Factory(supabaseProvider);
  }

  public static FriendRepository newInstance(SupabaseClient supabase) {
    return new FriendRepository(supabase);
  }
}
