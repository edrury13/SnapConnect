package com.example.snapconnect.di;

import com.example.snapconnect.data.remote.InspirationApi;
import com.example.snapconnect.data.repository.InspirationRepository;
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
public final class AppModule_ProvideInspirationRepositoryFactory implements Factory<InspirationRepository> {
  private final Provider<InspirationApi> apiProvider;

  public AppModule_ProvideInspirationRepositoryFactory(Provider<InspirationApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public InspirationRepository get() {
    return provideInspirationRepository(apiProvider.get());
  }

  public static AppModule_ProvideInspirationRepositoryFactory create(
      Provider<InspirationApi> apiProvider) {
    return new AppModule_ProvideInspirationRepositoryFactory(apiProvider);
  }

  public static InspirationRepository provideInspirationRepository(InspirationApi api) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideInspirationRepository(api));
  }
}
