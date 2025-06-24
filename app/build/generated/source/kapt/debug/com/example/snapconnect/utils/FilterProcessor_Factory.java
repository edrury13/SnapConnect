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
public final class FilterProcessor_Factory implements Factory<FilterProcessor> {
  private final Provider<Context> contextProvider;

  public FilterProcessor_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public FilterProcessor get() {
    return newInstance(contextProvider.get());
  }

  public static FilterProcessor_Factory create(Provider<Context> contextProvider) {
    return new FilterProcessor_Factory(contextProvider);
  }

  public static FilterProcessor newInstance(Context context) {
    return new FilterProcessor(context);
  }
}
