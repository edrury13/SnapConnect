package com.example.snapconnect.ui.screens.chat;

import androidx.lifecycle.SavedStateHandle;
import com.example.snapconnect.data.repository.AuthRepository;
import com.example.snapconnect.data.repository.MessagesRepository;
import com.example.snapconnect.data.repository.StorageRepository;
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<MessagesRepository> messagesRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<StorageRepository> storageRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public ChatViewModel_Factory(Provider<MessagesRepository> messagesRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<StorageRepository> storageRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.messagesRepositoryProvider = messagesRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.storageRepositoryProvider = storageRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(messagesRepositoryProvider.get(), userRepositoryProvider.get(), authRepositoryProvider.get(), storageRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static ChatViewModel_Factory create(
      Provider<MessagesRepository> messagesRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<StorageRepository> storageRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new ChatViewModel_Factory(messagesRepositoryProvider, userRepositoryProvider, authRepositoryProvider, storageRepositoryProvider, savedStateHandleProvider);
  }

  public static ChatViewModel newInstance(MessagesRepository messagesRepository,
      UserRepository userRepository, AuthRepository authRepository,
      StorageRepository storageRepository, SavedStateHandle savedStateHandle) {
    return new ChatViewModel(messagesRepository, userRepository, authRepository, storageRepository, savedStateHandle);
  }
}
