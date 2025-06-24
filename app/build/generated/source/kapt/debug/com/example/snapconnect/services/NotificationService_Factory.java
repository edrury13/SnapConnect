package com.example.snapconnect.services;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NotificationService_Factory implements Factory<NotificationService> {
  private final Provider<Context> contextProvider;

  public NotificationService_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NotificationService get() {
    return newInstance(contextProvider.get());
  }

  public static NotificationService_Factory create(Provider<Context> contextProvider) {
    return new NotificationService_Factory(contextProvider);
  }

  public static NotificationService newInstance(Context context) {
    return new NotificationService(context);
  }
}
