package com.example.snapconnect;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.snapconnect.data.repository.AuthRepository;
import com.example.snapconnect.data.repository.CommentsRepository;
import com.example.snapconnect.data.repository.FriendRepository;
import com.example.snapconnect.data.repository.MessagesRepository;
import com.example.snapconnect.data.repository.StorageRepository;
import com.example.snapconnect.data.repository.StoryRepository;
import com.example.snapconnect.data.repository.UserRepository;
import com.example.snapconnect.di.AppModule;
import com.example.snapconnect.di.AppModule_ProvideSupabaseClientFactory;
import com.example.snapconnect.ui.screens.auth.AuthViewModel;
import com.example.snapconnect.ui.screens.auth.AuthViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.snapconnect.ui.screens.camera.MediaSendViewModel;
import com.example.snapconnect.ui.screens.camera.MediaSendViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.snapconnect.ui.screens.camera.StoryPostViewModel;
import com.example.snapconnect.ui.screens.camera.StoryPostViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.snapconnect.ui.screens.chat.ChatViewModel;
import com.example.snapconnect.ui.screens.chat.ChatViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.snapconnect.ui.screens.friends.FriendsViewModel;
import com.example.snapconnect.ui.screens.friends.FriendsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.snapconnect.ui.screens.home.HomeViewModel;
import com.example.snapconnect.ui.screens.home.HomeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.snapconnect.ui.screens.messages.MessagesViewModel;
import com.example.snapconnect.ui.screens.messages.MessagesViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.snapconnect.ui.screens.notifications.NotificationsViewModel;
import com.example.snapconnect.ui.screens.notifications.NotificationsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.snapconnect.ui.screens.profile.ProfileViewModel;
import com.example.snapconnect.ui.screens.profile.ProfileViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.snapconnect.ui.screens.story.StoryViewViewModel;
import com.example.snapconnect.ui.screens.story.StoryViewViewModel_HiltModules_KeyModule_ProvideFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import io.github.jan.supabase.SupabaseClient;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class DaggerSnapConnectApplication_HiltComponents_SingletonC {
  private DaggerSnapConnectApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder appModule(AppModule appModule) {
      Preconditions.checkNotNull(appModule);
      return this;
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule(
        HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule) {
      Preconditions.checkNotNull(hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule);
      return this;
    }

    public SnapConnectApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements SnapConnectApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public SnapConnectApplication_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl(singletonCImpl);
    }
  }

  private static final class ActivityCBuilder implements SnapConnectApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public SnapConnectApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements SnapConnectApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public SnapConnectApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements SnapConnectApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SnapConnectApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements SnapConnectApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SnapConnectApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements SnapConnectApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public SnapConnectApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements SnapConnectApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public SnapConnectApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends SnapConnectApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends SnapConnectApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends SnapConnectApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends SnapConnectApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(AuthViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ChatViewModel_HiltModules_KeyModule_ProvideFactory.provide(), FriendsViewModel_HiltModules_KeyModule_ProvideFactory.provide(), HomeViewModel_HiltModules_KeyModule_ProvideFactory.provide(), MediaSendViewModel_HiltModules_KeyModule_ProvideFactory.provide(), MessagesViewModel_HiltModules_KeyModule_ProvideFactory.provide(), NotificationsViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ProfileViewModel_HiltModules_KeyModule_ProvideFactory.provide(), StoryPostViewModel_HiltModules_KeyModule_ProvideFactory.provide(), StoryViewViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends SnapConnectApplication_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<ChatViewModel> chatViewModelProvider;

    private Provider<FriendsViewModel> friendsViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<MediaSendViewModel> mediaSendViewModelProvider;

    private Provider<MessagesViewModel> messagesViewModelProvider;

    private Provider<NotificationsViewModel> notificationsViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private Provider<StoryPostViewModel> storyPostViewModelProvider;

    private Provider<StoryViewViewModel> storyViewViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.chatViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.friendsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.mediaSendViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.messagesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.notificationsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.storyPostViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.storyViewViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
    }

    @Override
    public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, Provider<ViewModel>>builderWithExpectedSize(10).put("com.example.snapconnect.ui.screens.auth.AuthViewModel", ((Provider) authViewModelProvider)).put("com.example.snapconnect.ui.screens.chat.ChatViewModel", ((Provider) chatViewModelProvider)).put("com.example.snapconnect.ui.screens.friends.FriendsViewModel", ((Provider) friendsViewModelProvider)).put("com.example.snapconnect.ui.screens.home.HomeViewModel", ((Provider) homeViewModelProvider)).put("com.example.snapconnect.ui.screens.camera.MediaSendViewModel", ((Provider) mediaSendViewModelProvider)).put("com.example.snapconnect.ui.screens.messages.MessagesViewModel", ((Provider) messagesViewModelProvider)).put("com.example.snapconnect.ui.screens.notifications.NotificationsViewModel", ((Provider) notificationsViewModelProvider)).put("com.example.snapconnect.ui.screens.profile.ProfileViewModel", ((Provider) profileViewModelProvider)).put("com.example.snapconnect.ui.screens.camera.StoryPostViewModel", ((Provider) storyPostViewModelProvider)).put("com.example.snapconnect.ui.screens.story.StoryViewViewModel", ((Provider) storyViewViewModelProvider)).build();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.snapconnect.ui.screens.auth.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.authRepositoryProvider.get());

          case 1: // com.example.snapconnect.ui.screens.chat.ChatViewModel 
          return (T) new ChatViewModel(singletonCImpl.messagesRepositoryProvider.get(), singletonCImpl.userRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get(), viewModelCImpl.savedStateHandle);

          case 2: // com.example.snapconnect.ui.screens.friends.FriendsViewModel 
          return (T) new FriendsViewModel(singletonCImpl.friendRepositoryProvider.get(), singletonCImpl.messagesRepositoryProvider.get());

          case 3: // com.example.snapconnect.ui.screens.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.storyRepositoryProvider.get(), singletonCImpl.userRepositoryProvider.get());

          case 4: // com.example.snapconnect.ui.screens.camera.MediaSendViewModel 
          return (T) new MediaSendViewModel(singletonCImpl.friendRepositoryProvider.get(), singletonCImpl.messagesRepositoryProvider.get(), singletonCImpl.storageRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get());

          case 5: // com.example.snapconnect.ui.screens.messages.MessagesViewModel 
          return (T) new MessagesViewModel(singletonCImpl.messagesRepositoryProvider.get(), singletonCImpl.userRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get());

          case 6: // com.example.snapconnect.ui.screens.notifications.NotificationsViewModel 
          return (T) new NotificationsViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.example.snapconnect.ui.screens.profile.ProfileViewModel 
          return (T) new ProfileViewModel(singletonCImpl.authRepositoryProvider.get(), singletonCImpl.userRepositoryProvider.get(), singletonCImpl.storyRepositoryProvider.get(), singletonCImpl.friendRepositoryProvider.get(), singletonCImpl.storageRepositoryProvider.get());

          case 8: // com.example.snapconnect.ui.screens.camera.StoryPostViewModel 
          return (T) new StoryPostViewModel(singletonCImpl.storyRepositoryProvider.get(), singletonCImpl.storageRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get(), singletonCImpl.messagesRepositoryProvider.get());

          case 9: // com.example.snapconnect.ui.screens.story.StoryViewViewModel 
          return (T) new StoryViewViewModel(singletonCImpl.storyRepositoryProvider.get(), singletonCImpl.userRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get(), singletonCImpl.commentsRepositoryProvider.get(), viewModelCImpl.savedStateHandle);

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends SnapConnectApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends SnapConnectApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends SnapConnectApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<SupabaseClient> provideSupabaseClientProvider;

    private Provider<AuthRepository> authRepositoryProvider;

    private Provider<MessagesRepository> messagesRepositoryProvider;

    private Provider<UserRepository> userRepositoryProvider;

    private Provider<FriendRepository> friendRepositoryProvider;

    private Provider<StoryRepository> storyRepositoryProvider;

    private Provider<StorageRepository> storageRepositoryProvider;

    private Provider<CommentsRepository> commentsRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideSupabaseClientProvider = DoubleCheck.provider(new SwitchingProvider<SupabaseClient>(singletonCImpl, 1));
      this.authRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 0));
      this.messagesRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<MessagesRepository>(singletonCImpl, 2));
      this.userRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UserRepository>(singletonCImpl, 3));
      this.friendRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<FriendRepository>(singletonCImpl, 4));
      this.storyRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<StoryRepository>(singletonCImpl, 5));
      this.storageRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<StorageRepository>(singletonCImpl, 6));
      this.commentsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<CommentsRepository>(singletonCImpl, 7));
    }

    @Override
    public void injectSnapConnectApplication(SnapConnectApplication arg0) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.snapconnect.data.repository.AuthRepository 
          return (T) new AuthRepository(singletonCImpl.provideSupabaseClientProvider.get());

          case 1: // io.github.jan.supabase.SupabaseClient 
          return (T) AppModule_ProvideSupabaseClientFactory.provideSupabaseClient();

          case 2: // com.example.snapconnect.data.repository.MessagesRepository 
          return (T) new MessagesRepository(singletonCImpl.provideSupabaseClientProvider.get());

          case 3: // com.example.snapconnect.data.repository.UserRepository 
          return (T) new UserRepository(singletonCImpl.provideSupabaseClientProvider.get());

          case 4: // com.example.snapconnect.data.repository.FriendRepository 
          return (T) new FriendRepository(singletonCImpl.provideSupabaseClientProvider.get());

          case 5: // com.example.snapconnect.data.repository.StoryRepository 
          return (T) new StoryRepository(singletonCImpl.provideSupabaseClientProvider.get());

          case 6: // com.example.snapconnect.data.repository.StorageRepository 
          return (T) new StorageRepository(singletonCImpl.provideSupabaseClientProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.example.snapconnect.data.repository.CommentsRepository 
          return (T) new CommentsRepository(singletonCImpl.provideSupabaseClientProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
