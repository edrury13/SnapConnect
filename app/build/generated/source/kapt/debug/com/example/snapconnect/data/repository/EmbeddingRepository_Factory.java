package com.example.snapconnect.data.repository;

import com.example.snapconnect.data.remote.EmbeddingApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class EmbeddingRepository_Factory implements Factory<EmbeddingRepository> {
  private final Provider<EmbeddingApi> apiProvider;

  public EmbeddingRepository_Factory(Provider<EmbeddingApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public EmbeddingRepository get() {
    return newInstance(apiProvider.get());
  }

  public static EmbeddingRepository_Factory create(Provider<EmbeddingApi> apiProvider) {
    return new EmbeddingRepository_Factory(apiProvider);
  }

  public static EmbeddingRepository newInstance(EmbeddingApi api) {
    return new EmbeddingRepository(api);
  }
}
