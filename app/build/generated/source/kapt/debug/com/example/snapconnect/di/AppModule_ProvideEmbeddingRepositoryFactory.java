package com.example.snapconnect.di;

import com.example.snapconnect.data.remote.EmbeddingApi;
import com.example.snapconnect.data.repository.EmbeddingRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideEmbeddingRepositoryFactory implements Factory<EmbeddingRepository> {
  private final Provider<EmbeddingApi> apiProvider;

  public AppModule_ProvideEmbeddingRepositoryFactory(Provider<EmbeddingApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public EmbeddingRepository get() {
    return provideEmbeddingRepository(apiProvider.get());
  }

  public static AppModule_ProvideEmbeddingRepositoryFactory create(
      Provider<EmbeddingApi> apiProvider) {
    return new AppModule_ProvideEmbeddingRepositoryFactory(apiProvider);
  }

  public static EmbeddingRepository provideEmbeddingRepository(EmbeddingApi api) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEmbeddingRepository(api));
  }
}
