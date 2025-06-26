package com.example.snapconnect.di;

import com.example.snapconnect.data.remote.LangchainApi;
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
public final class AppModule_ProvideLangchainApiFactory implements Factory<LangchainApi> {
  private final Provider<HttpClient> clientProvider;

  public AppModule_ProvideLangchainApiFactory(Provider<HttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public LangchainApi get() {
    return provideLangchainApi(clientProvider.get());
  }

  public static AppModule_ProvideLangchainApiFactory create(Provider<HttpClient> clientProvider) {
    return new AppModule_ProvideLangchainApiFactory(clientProvider);
  }

  public static LangchainApi provideLangchainApi(HttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideLangchainApi(client));
  }
}
