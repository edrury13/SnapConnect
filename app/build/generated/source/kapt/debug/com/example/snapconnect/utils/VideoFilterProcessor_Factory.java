package com.example.snapconnect.utils;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class VideoFilterProcessor_Factory implements Factory<VideoFilterProcessor> {
  private final Provider<Context> contextProvider;

  public VideoFilterProcessor_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public VideoFilterProcessor get() {
    return newInstance(contextProvider.get());
  }

  public static VideoFilterProcessor_Factory create(Provider<Context> contextProvider) {
    return new VideoFilterProcessor_Factory(contextProvider);
  }

  public static VideoFilterProcessor newInstance(Context context) {
    return new VideoFilterProcessor(context);
  }
}
