package com.example.snapconnect.data.repository;

import com.example.snapconnect.data.remote.InspirationApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class InspirationRepository_Factory implements Factory<InspirationRepository> {
  private final Provider<InspirationApi> apiProvider;

  private final Provider<HttpClient> httpClientProvider;

  public InspirationRepository_Factory(Provider<InspirationApi> apiProvider,
      Provider<HttpClient> httpClientProvider) {
    this.apiProvider = apiProvider;
    this.httpClientProvider = httpClientProvider;
  }

  @Override
  public InspirationRepository get() {
    return newInstance(apiProvider.get(), httpClientProvider.get());
  }

  public static InspirationRepository_Factory create(Provider<InspirationApi> apiProvider,
      Provider<HttpClient> httpClientProvider) {
    return new InspirationRepository_Factory(apiProvider, httpClientProvider);
  }

  public static InspirationRepository newInstance(InspirationApi api, HttpClient httpClient) {
    return new InspirationRepository(api, httpClient);
  }
}
