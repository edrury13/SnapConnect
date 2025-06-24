package com.example.snapconnect.ui.screens.profile;

import com.example.snapconnect.data.repository.AuthRepository;
import com.example.snapconnect.data.repository.FriendRepository;
import com.example.snapconnect.data.repository.StorageRepository;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<StoryRepository> storyRepositoryProvider;

  private final Provider<FriendRepository> friendRepositoryProvider;

  private final Provider<StorageRepository> storageRepositoryProvider;

  public ProfileViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<StoryRepository> storyRepositoryProvider,
      Provider<FriendRepository> friendRepositoryProvider,
      Provider<StorageRepository> storageRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.storyRepositoryProvider = storyRepositoryProvider;
    this.friendRepositoryProvider = friendRepositoryProvider;
    this.storageRepositoryProvider = storageRepositoryProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(authRepositoryProvider.get(), userRepositoryProvider.get(), storyRepositoryProvider.get(), friendRepositoryProvider.get(), storageRepositoryProvider.get());
  }

  public static ProfileViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<StoryRepository> storyRepositoryProvider,
      Provider<FriendRepository> friendRepositoryProvider,
      Provider<StorageRepository> storageRepositoryProvider) {
    return new ProfileViewModel_Factory(authRepositoryProvider, userRepositoryProvider, storyRepositoryProvider, friendRepositoryProvider, storageRepositoryProvider);
  }

  public static ProfileViewModel newInstance(AuthRepository authRepository,
      UserRepository userRepository, StoryRepository storyRepository,
      FriendRepository friendRepository, StorageRepository storageRepository) {
    return new ProfileViewModel(authRepository, userRepository, storyRepository, friendRepository, storageRepository);
  }
}
