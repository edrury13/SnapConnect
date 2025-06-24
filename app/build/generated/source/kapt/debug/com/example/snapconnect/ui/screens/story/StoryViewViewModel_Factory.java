package com.example.snapconnect.ui.screens.story;

import androidx.lifecycle.SavedStateHandle;
import com.example.snapconnect.data.repository.AuthRepository;
import com.example.snapconnect.data.repository.CommentsRepository;
import com.example.snapconnect.data.repository.StoryRepository;
import com.example.snapconnect.data.repository.UserRepository;
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
public final class StoryViewViewModel_Factory implements Factory<StoryViewViewModel> {
  private final Provider<StoryRepository> storyRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<CommentsRepository> commentsRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public StoryViewViewModel_Factory(Provider<StoryRepository> storyRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<CommentsRepository> commentsRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.storyRepositoryProvider = storyRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.commentsRepositoryProvider = commentsRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public StoryViewViewModel get() {
    return newInstance(storyRepositoryProvider.get(), userRepositoryProvider.get(), authRepositoryProvider.get(), commentsRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static StoryViewViewModel_Factory create(Provider<StoryRepository> storyRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<CommentsRepository> commentsRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new StoryViewViewModel_Factory(storyRepositoryProvider, userRepositoryProvider, authRepositoryProvider, commentsRepositoryProvider, savedStateHandleProvider);
  }

  public static StoryViewViewModel newInstance(StoryRepository storyRepository,
      UserRepository userRepository, AuthRepository authRepository,
      CommentsRepository commentsRepository, SavedStateHandle savedStateHandle) {
    return new StoryViewViewModel(storyRepository, userRepository, authRepository, commentsRepository, savedStateHandle);
  }
}
