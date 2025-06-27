package com.example.snapconnect.ui.screens.inspiration;

import com.example.snapconnect.data.repository.StoryRepository;
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
public final class StyleGalleryViewModel_Factory implements Factory<StyleGalleryViewModel> {
  private final Provider<StoryRepository> storyRepositoryProvider;

  public StyleGalleryViewModel_Factory(Provider<StoryRepository> storyRepositoryProvider) {
    this.storyRepositoryProvider = storyRepositoryProvider;
  }

  @Override
  public StyleGalleryViewModel get() {
    return newInstance(storyRepositoryProvider.get());
  }

  public static StyleGalleryViewModel_Factory create(
      Provider<StoryRepository> storyRepositoryProvider) {
    return new StyleGalleryViewModel_Factory(storyRepositoryProvider);
  }

  public static StyleGalleryViewModel newInstance(StoryRepository storyRepository) {
    return new StyleGalleryViewModel(storyRepository);
  }
}
