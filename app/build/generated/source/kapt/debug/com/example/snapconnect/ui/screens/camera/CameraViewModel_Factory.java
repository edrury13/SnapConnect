package com.example.snapconnect.ui.screens.camera;

import com.example.snapconnect.data.repository.FiltersRepository;
import com.example.snapconnect.utils.FilterProcessor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class CameraViewModel_Factory implements Factory<CameraViewModel> {
  private final Provider<FiltersRepository> filtersRepositoryProvider;

  private final Provider<FilterProcessor> filterProcessorProvider;

  public CameraViewModel_Factory(Provider<FiltersRepository> filtersRepositoryProvider,
      Provider<FilterProcessor> filterProcessorProvider) {
    this.filtersRepositoryProvider = filtersRepositoryProvider;
    this.filterProcessorProvider = filterProcessorProvider;
  }

  @Override
  public CameraViewModel get() {
    return newInstance(filtersRepositoryProvider.get(), filterProcessorProvider.get());
  }

  public static CameraViewModel_Factory create(
      Provider<FiltersRepository> filtersRepositoryProvider,
      Provider<FilterProcessor> filterProcessorProvider) {
    return new CameraViewModel_Factory(filtersRepositoryProvider, filterProcessorProvider);
  }

  public static CameraViewModel newInstance(FiltersRepository filtersRepository,
      FilterProcessor filterProcessor) {
    return new CameraViewModel(filtersRepository, filterProcessor);
  }
}
