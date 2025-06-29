package com.example.snapconnect.di;

import com.example.snapconnect.data.remote.InspirationApi;
import com.example.snapconnect.data.repository.InspirationRepository;
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
public final class AppModule_ProvideInspirationRepositoryFactory implements Factory<InspirationRepository> {
  private final Provider<InspirationApi> apiProvider;

  private final Provider<HttpClient> clientProvider;

  public AppModule_ProvideInspirationRepositoryFactory(Provider<InspirationApi> apiProvider,
      Provider<HttpClient> clientProvider) {
    this.apiProvider = apiProvider;
    this.clientProvider = clientProvider;
  }

  @Override
  public InspirationRepository get() {
    return provideInspirationRepository(apiProvider.get(), clientProvider.get());
  }

  public static AppModule_ProvideInspirationRepositoryFactory create(
      Provider<InspirationApi> apiProvider, Provider<HttpClient> clientProvider) {
    return new AppModule_ProvideInspirationRepositoryFactory(apiProvider, clientProvider);
  }

  public static InspirationRepository provideInspirationRepository(InspirationApi api,
      HttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideInspirationRepository(api, client));
  }
}
