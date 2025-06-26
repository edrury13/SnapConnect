package com.example.snapconnect.ui.viewmodel;

import com.example.snapconnect.data.repository.InspirationRepository;
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

  public InspirationViewModel_Factory(Provider<InspirationRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public InspirationViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static InspirationViewModel_Factory create(Provider<InspirationRepository> repoProvider) {
    return new InspirationViewModel_Factory(repoProvider);
  }

  public static InspirationViewModel newInstance(InspirationRepository repo) {
    return new InspirationViewModel(repo);
  }
}
