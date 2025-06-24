package com.example.snapconnect.ui.screens.camera;

import com.example.snapconnect.data.repository.AuthRepository;
import com.example.snapconnect.data.repository.FriendRepository;
import com.example.snapconnect.data.repository.MessagesRepository;
import com.example.snapconnect.data.repository.StorageRepository;
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
public final class MediaSendViewModel_Factory implements Factory<MediaSendViewModel> {
  private final Provider<FriendRepository> friendRepositoryProvider;

  private final Provider<MessagesRepository> messagesRepositoryProvider;

  private final Provider<StorageRepository> storageRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public MediaSendViewModel_Factory(Provider<FriendRepository> friendRepositoryProvider,
      Provider<MessagesRepository> messagesRepositoryProvider,
      Provider<StorageRepository> storageRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.friendRepositoryProvider = friendRepositoryProvider;
    this.messagesRepositoryProvider = messagesRepositoryProvider;
    this.storageRepositoryProvider = storageRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public MediaSendViewModel get() {
    return newInstance(friendRepositoryProvider.get(), messagesRepositoryProvider.get(), storageRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static MediaSendViewModel_Factory create(
      Provider<FriendRepository> friendRepositoryProvider,
      Provider<MessagesRepository> messagesRepositoryProvider,
      Provider<StorageRepository> storageRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new MediaSendViewModel_Factory(friendRepositoryProvider, messagesRepositoryProvider, storageRepositoryProvider, authRepositoryProvider);
  }

  public static MediaSendViewModel newInstance(FriendRepository friendRepository,
      MessagesRepository messagesRepository, StorageRepository storageRepository,
      AuthRepository authRepository) {
    return new MediaSendViewModel(friendRepository, messagesRepository, storageRepository, authRepository);
  }
}
