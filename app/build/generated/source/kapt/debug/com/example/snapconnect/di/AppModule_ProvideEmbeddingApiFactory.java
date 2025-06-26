package com.example.snapconnect.di;

import com.example.snapconnect.data.remote.EmbeddingApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class AppModule_ProvideEmbeddingApiFactory implements Factory<EmbeddingApi> {
  private final Provider<HttpClient> clientProvider;

  public AppModule_ProvideEmbeddingApiFactory(Provider<HttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public EmbeddingApi get() {
    return provideEmbeddingApi(clientProvider.get());
  }

  public static AppModule_ProvideEmbeddingApiFactory create(Provider<HttpClient> clientProvider) {
    return new AppModule_ProvideEmbeddingApiFactory(clientProvider);
  }

  public static EmbeddingApi provideEmbeddingApi(HttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEmbeddingApi(client));
  }
}
