package com.example.snapconnect.data.repository;

import com.example.snapconnect.data.remote.VisionApi;
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
public final class VisionRepository_Factory implements Factory<VisionRepository> {
  private final Provider<VisionApi> apiProvider;

  public VisionRepository_Factory(Provider<VisionApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public VisionRepository get() {
    return newInstance(apiProvider.get());
  }

  public static VisionRepository_Factory create(Provider<VisionApi> apiProvider) {
    return new VisionRepository_Factory(apiProvider);
  }

  public static VisionRepository newInstance(VisionApi api) {
    return new VisionRepository(api);
  }
}
