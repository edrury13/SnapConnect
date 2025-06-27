package com.example.snapconnect.ui.screens.messages;

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
public final class CreateGroupViewModel_Factory implements Factory<CreateGroupViewModel> {
  private final Provider<FriendRepository> friendRepositoryProvider;

  private final Provider<MessagesRepository> messagesRepositoryProvider;

  public CreateGroupViewModel_Factory(Provider<FriendRepository> friendRepositoryProvider,
      Provider<MessagesRepository> messagesRepositoryProvider) {
    this.friendRepositoryProvider = friendRepositoryProvider;
    this.messagesRepositoryProvider = messagesRepositoryProvider;
  }

  @Override
  public CreateGroupViewModel get() {
    return newInstance(friendRepositoryProvider.get(), messagesRepositoryProvider.get());
  }

  public static CreateGroupViewModel_Factory create(
      Provider<FriendRepository> friendRepositoryProvider,
      Provider<MessagesRepository> messagesRepositoryProvider) {
    return new CreateGroupViewModel_Factory(friendRepositoryProvider, messagesRepositoryProvider);
  }

  public static CreateGroupViewModel newInstance(FriendRepository friendRepository,
      MessagesRepository messagesRepository) {
    return new CreateGroupViewModel(friendRepository, messagesRepository);
  }
}
