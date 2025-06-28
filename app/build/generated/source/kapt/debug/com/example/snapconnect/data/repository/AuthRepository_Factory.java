package com.example.snapconnect.data.repository;

import android.content.SharedPreferences;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<SupabaseClient> supabaseProvider;

  private final Provider<SharedPreferences> sharedPreferencesProvider;

  public AuthRepository_Factory(Provider<SupabaseClient> supabaseProvider,
      Provider<SharedPreferences> sharedPreferencesProvider) {
    this.supabaseProvider = supabaseProvider;
    this.sharedPreferencesProvider = sharedPreferencesProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(supabaseProvider.get(), sharedPreferencesProvider.get());
  }

  public static AuthRepository_Factory create(Provider<SupabaseClient> supabaseProvider,
      Provider<SharedPreferences> sharedPreferencesProvider) {
    return new AuthRepository_Factory(supabaseProvider, sharedPreferencesProvider);
  }

  public static AuthRepository newInstance(SupabaseClient supabase,
      SharedPreferences sharedPreferences) {
    return new AuthRepository(supabase, sharedPreferences);
  }
}
