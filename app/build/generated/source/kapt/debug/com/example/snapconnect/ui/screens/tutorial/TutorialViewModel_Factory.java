package com.example.snapconnect.ui.screens.tutorial;

import com.example.snapconnect.data.repository.AuthRepository;
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
public final class TutorialViewModel_Factory implements Factory<TutorialViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public TutorialViewModel_Factory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public TutorialViewModel get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static TutorialViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider) {
    return new TutorialViewModel_Factory(authRepositoryProvider);
  }

  public static TutorialViewModel newInstance(AuthRepository authRepository) {
    return new TutorialViewModel(authRepository);
  }
}
