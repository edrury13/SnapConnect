package com.example.snapconnect.ui.screens.notifications;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class NotificationsViewModel_Factory implements Factory<NotificationsViewModel> {
  private final Provider<Context> contextProvider;

  public NotificationsViewModel_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NotificationsViewModel get() {
    return newInstance(contextProvider.get());
  }

  public static NotificationsViewModel_Factory create(Provider<Context> contextProvider) {
    return new NotificationsViewModel_Factory(contextProvider);
  }

  public static NotificationsViewModel newInstance(Context context) {
    return new NotificationsViewModel(context);
  }
}
