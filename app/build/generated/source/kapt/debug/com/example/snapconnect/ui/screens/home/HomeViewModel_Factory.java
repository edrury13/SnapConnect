package com.example.snapconnect.ui.screens.home;

import com.example.snapconnect.data.repository.AuthRepository;
import com.example.snapconnect.data.repository.FriendRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<StoryRepository> storyRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<FriendRepository> friendRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public HomeViewModel_Factory(Provider<StoryRepository> storyRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<FriendRepository> friendRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.storyRepositoryProvider = storyRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.friendRepositoryProvider = friendRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(storyRepositoryProvider.get(), userRepositoryProvider.get(), friendRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<StoryRepository> storyRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<FriendRepository> friendRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new HomeViewModel_Factory(storyRepositoryProvider, userRepositoryProvider, friendRepositoryProvider, authRepositoryProvider);
  }

  public static HomeViewModel newInstance(StoryRepository storyRepository,
      UserRepository userRepository, FriendRepository friendRepository,
      AuthRepository authRepository) {
    return new HomeViewModel(storyRepository, userRepository, friendRepository, authRepository);
  }
}
