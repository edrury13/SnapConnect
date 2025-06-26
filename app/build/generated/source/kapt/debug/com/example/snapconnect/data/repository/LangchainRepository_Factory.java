package com.example.snapconnect.data.repository;

import com.example.snapconnect.data.remote.LangchainApi;
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
public final class LangchainRepository_Factory implements Factory<LangchainRepository> {
  private final Provider<LangchainApi> apiProvider;

  public LangchainRepository_Factory(Provider<LangchainApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public LangchainRepository get() {
    return newInstance(apiProvider.get());
  }

  public static LangchainRepository_Factory create(Provider<LangchainApi> apiProvider) {
    return new LangchainRepository_Factory(apiProvider);
  }

  public static LangchainRepository newInstance(LangchainApi api) {
    return new LangchainRepository(api);
  }
}
