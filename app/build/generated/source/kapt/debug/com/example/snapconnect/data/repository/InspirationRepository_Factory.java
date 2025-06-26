package com.example.snapconnect.data.repository;

import com.example.snapconnect.data.remote.InspirationApi;
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
public final class InspirationRepository_Factory implements Factory<InspirationRepository> {
  private final Provider<InspirationApi> apiProvider;

  public InspirationRepository_Factory(Provider<InspirationApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public InspirationRepository get() {
    return newInstance(apiProvider.get());
  }

  public static InspirationRepository_Factory create(Provider<InspirationApi> apiProvider) {
    return new InspirationRepository_Factory(apiProvider);
  }

  public static InspirationRepository newInstance(InspirationApi api) {
    return new InspirationRepository(api);
  }
}
