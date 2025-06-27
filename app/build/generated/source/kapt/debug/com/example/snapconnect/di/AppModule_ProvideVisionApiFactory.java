package com.example.snapconnect.di;

import com.example.snapconnect.data.remote.VisionApi;
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
public final class AppModule_ProvideVisionApiFactory implements Factory<VisionApi> {
  private final Provider<HttpClient> clientProvider;

  public AppModule_ProvideVisionApiFactory(Provider<HttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public VisionApi get() {
    return provideVisionApi(clientProvider.get());
  }

  public static AppModule_ProvideVisionApiFactory create(Provider<HttpClient> clientProvider) {
    return new AppModule_ProvideVisionApiFactory(clientProvider);
  }

  public static VisionApi provideVisionApi(HttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVisionApi(client));
  }
}
