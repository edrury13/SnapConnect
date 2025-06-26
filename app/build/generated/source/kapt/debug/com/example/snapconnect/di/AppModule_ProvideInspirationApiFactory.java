package com.example.snapconnect.di;

import com.example.snapconnect.data.remote.InspirationApi;
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
public final class AppModule_ProvideInspirationApiFactory implements Factory<InspirationApi> {
  private final Provider<HttpClient> clientProvider;

  public AppModule_ProvideInspirationApiFactory(Provider<HttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public InspirationApi get() {
    return provideInspirationApi(clientProvider.get());
  }

  public static AppModule_ProvideInspirationApiFactory create(Provider<HttpClient> clientProvider) {
    return new AppModule_ProvideInspirationApiFactory(clientProvider);
  }

  public static InspirationApi provideInspirationApi(HttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideInspirationApi(client));
  }
}
