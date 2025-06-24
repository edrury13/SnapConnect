package com.example.snapconnect.ui.screens.camera;

import com.example.snapconnect.data.repository.AuthRepository;
import com.example.snapconnect.data.repository.MessagesRepository;
import com.example.snapconnect.data.repository.StorageRepository;
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
public final class StoryPostViewModel_Factory implements Factory<StoryPostViewModel> {
  private final Provider<StoryRepository> storyRepositoryProvider;

  private final Provider<StorageRepository> storageRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<MessagesRepository> messagesRepositoryProvider;

  public StoryPostViewModel_Factory(Provider<StoryRepository> storyRepositoryProvider,
      Provider<StorageRepository> storageRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<MessagesRepository> messagesRepositoryProvider) {
    this.storyRepositoryProvider = storyRepositoryProvider;
    this.storageRepositoryProvider = storageRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.messagesRepositoryProvider = messagesRepositoryProvider;
  }

  @Override
  public StoryPostViewModel get() {
    return newInstance(storyRepositoryProvider.get(), storageRepositoryProvider.get(), authRepositoryProvider.get(), messagesRepositoryProvider.get());
  }

  public static StoryPostViewModel_Factory create(Provider<StoryRepository> storyRepositoryProvider,
      Provider<StorageRepository> storageRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<MessagesRepository> messagesRepositoryProvider) {
    return new StoryPostViewModel_Factory(storyRepositoryProvider, storageRepositoryProvider, authRepositoryProvider, messagesRepositoryProvider);
  }

  public static StoryPostViewModel newInstance(StoryRepository storyRepository,
      StorageRepository storageRepository, AuthRepository authRepository,
      MessagesRepository messagesRepository) {
    return new StoryPostViewModel(storyRepository, storageRepository, authRepository, messagesRepository);
  }
}
