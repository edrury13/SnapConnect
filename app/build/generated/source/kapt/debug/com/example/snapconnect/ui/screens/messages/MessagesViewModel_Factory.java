package com.example.snapconnect.ui.screens.messages;

import com.example.snapconnect.data.repository.AuthRepository;
import com.example.snapconnect.data.repository.MessagesRepository;
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
public final class MessagesViewModel_Factory implements Factory<MessagesViewModel> {
  private final Provider<MessagesRepository> messagesRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public MessagesViewModel_Factory(Provider<MessagesRepository> messagesRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.messagesRepositoryProvider = messagesRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public MessagesViewModel get() {
    return newInstance(messagesRepositoryProvider.get(), userRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static MessagesViewModel_Factory create(
      Provider<MessagesRepository> messagesRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new MessagesViewModel_Factory(messagesRepositoryProvider, userRepositoryProvider, authRepositoryProvider);
  }

  public static MessagesViewModel newInstance(MessagesRepository messagesRepository,
      UserRepository userRepository, AuthRepository authRepository) {
    return new MessagesViewModel(messagesRepository, userRepository, authRepository);
  }
}
