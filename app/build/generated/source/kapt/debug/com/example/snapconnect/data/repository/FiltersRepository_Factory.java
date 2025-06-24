package com.example.snapconnect.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class FiltersRepository_Factory implements Factory<FiltersRepository> {
  @Override
  public FiltersRepository get() {
    return newInstance();
  }

  public static FiltersRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FiltersRepository newInstance() {
    return new FiltersRepository();
  }

  private static final class InstanceHolder {
    private static final FiltersRepository_Factory INSTANCE = new FiltersRepository_Factory();
  }
}
