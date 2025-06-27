package com.example.snapconnect;

import com.example.snapconnect.services.StoryCleanupService;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<StoryCleanupService> storyCleanupServiceProvider;

  public MainActivity_MembersInjector(Provider<StoryCleanupService> storyCleanupServiceProvider) {
    this.storyCleanupServiceProvider = storyCleanupServiceProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<StoryCleanupService> storyCleanupServiceProvider) {
    return new MainActivity_MembersInjector(storyCleanupServiceProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectStoryCleanupService(instance, storyCleanupServiceProvider.get());
  }

  @InjectedFieldSignature("com.example.snapconnect.MainActivity.storyCleanupService")
  public static void injectStoryCleanupService(MainActivity instance,
      StoryCleanupService storyCleanupService) {
    instance.storyCleanupService = storyCleanupService;
  }
}
