package com.example.snapconnect.di;

import com.example.snapconnect.data.remote.LangchainApi;
import com.example.snapconnect.data.repository.LangchainRepository;
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
public final class AppModule_ProvideLangchainRepositoryFactory implements Factory<LangchainRepository> {
  private final Provider<LangchainApi> apiProvider;

  public AppModule_ProvideLangchainRepositoryFactory(Provider<LangchainApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public LangchainRepository get() {
    return provideLangchainRepository(apiProvider.get());
  }

  public static AppModule_ProvideLangchainRepositoryFactory create(
      Provider<LangchainApi> apiProvider) {
    return new AppModule_ProvideLangchainRepositoryFactory(apiProvider);
  }

  public static LangchainRepository provideLangchainRepository(LangchainApi api) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideLangchainRepository(api));
  }
}
