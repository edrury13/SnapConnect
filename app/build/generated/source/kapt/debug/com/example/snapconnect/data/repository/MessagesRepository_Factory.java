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
public final class MessagesRepository_Factory implements Factory<MessagesRepository> {
  private final Provider<SupabaseClient> supabaseProvider;

  public MessagesRepository_Factory(Provider<SupabaseClient> supabaseProvider) {
    this.supabaseProvider = supabaseProvider;
  }

  @Override
  public MessagesRepository get() {
    return newInstance(supabaseProvider.get());
  }

  public static MessagesRepository_Factory create(Provider<SupabaseClient> supabaseProvider) {
    return new MessagesRepository_Factory(supabaseProvider);
  }

  public static MessagesRepository newInstance(SupabaseClient supabase) {
    return new MessagesRepository(supabase);
  }
}
