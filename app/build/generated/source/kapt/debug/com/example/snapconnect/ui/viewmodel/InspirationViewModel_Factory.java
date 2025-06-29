package com.example.snapconnect.ui.viewmodel;

import com.example.snapconnect.data.repository.InspirationRepository;
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
public final class InspirationViewModel_Factory implements Factory<InspirationViewModel> {
  private final Provider<InspirationRepository> repoProvider;

  private final Provider<StoryRepository> storyRepositoryProvider;

  public InspirationViewModel_Factory(Provider<InspirationRepository> repoProvider,
      Provider<StoryRepository> storyRepositoryProvider) {
    this.repoProvider = repoProvider;
    this.storyRepositoryProvider = storyRepositoryProvider;
  }

  @Override
  public InspirationViewModel get() {
    return newInstance(repoProvider.get(), storyRepositoryProvider.get());
  }

  public static InspirationViewModel_Factory create(Provider<InspirationRepository> repoProvider,
      Provider<StoryRepository> storyRepositoryProvider) {
    return new InspirationViewModel_Factory(repoProvider, storyRepositoryProvider);
  }

  public static InspirationViewModel newInstance(InspirationRepository repo,
      StoryRepository storyRepository) {
    return new InspirationViewModel(repo, storyRepository);
  }
}
