package com.example.snapconnect.ui.screens.friends;

import com.example.snapconnect.data.repository.FriendRepository;
import com.example.snapconnect.data.repository.MessagesRepository;
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
public final class FriendsViewModel_Factory implements Factory<FriendsViewModel> {
  private final Provider<FriendRepository> friendRepositoryProvider;

  private final Provider<MessagesRepository> messagesRepositoryProvider;

  public FriendsViewModel_Factory(Provider<FriendRepository> friendRepositoryProvider,
      Provider<MessagesRepository> messagesRepositoryProvider) {
    this.friendRepositoryProvider = friendRepositoryProvider;
    this.messagesRepositoryProvider = messagesRepositoryProvider;
  }

  @Override
  public FriendsViewModel get() {
    return newInstance(friendRepositoryProvider.get(), messagesRepositoryProvider.get());
  }

  public static FriendsViewModel_Factory create(Provider<FriendRepository> friendRepositoryProvider,
      Provider<MessagesRepository> messagesRepositoryProvider) {
    return new FriendsViewModel_Factory(friendRepositoryProvider, messagesRepositoryProvider);
  }

  public static FriendsViewModel newInstance(FriendRepository friendRepository,
      MessagesRepository messagesRepository) {
    return new FriendsViewModel(friendRepository, messagesRepository);
  }
}
