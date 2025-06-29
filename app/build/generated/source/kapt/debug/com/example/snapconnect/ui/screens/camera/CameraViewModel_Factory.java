package com.example.snapconnect.ui.screens.camera;

import com.example.snapconnect.data.repository.FiltersRepository;
import com.example.snapconnect.utils.FilterProcessor;
import com.example.snapconnect.utils.VideoFilterProcessor;
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

  private final Provider<VideoFilterProcessor> videoFilterProcessorProvider;

  public CameraViewModel_Factory(Provider<FiltersRepository> filtersRepositoryProvider,
      Provider<FilterProcessor> filterProcessorProvider,
      Provider<VideoFilterProcessor> videoFilterProcessorProvider) {
    this.filtersRepositoryProvider = filtersRepositoryProvider;
    this.filterProcessorProvider = filterProcessorProvider;
    this.videoFilterProcessorProvider = videoFilterProcessorProvider;
  }

  @Override
  public CameraViewModel get() {
    return newInstance(filtersRepositoryProvider.get(), filterProcessorProvider.get(), videoFilterProcessorProvider.get());
  }

  public static CameraViewModel_Factory create(
      Provider<FiltersRepository> filtersRepositoryProvider,
      Provider<FilterProcessor> filterProcessorProvider,
      Provider<VideoFilterProcessor> videoFilterProcessorProvider) {
    return new CameraViewModel_Factory(filtersRepositoryProvider, filterProcessorProvider, videoFilterProcessorProvider);
  }

  public static CameraViewModel newInstance(FiltersRepository filtersRepository,
      FilterProcessor filterProcessor, VideoFilterProcessor videoFilterProcessor) {
    return new CameraViewModel(filtersRepository, filterProcessor, videoFilterProcessor);
  }
}
