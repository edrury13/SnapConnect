package com.example.snapconnect.services;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\b\u0007\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u000bJ&\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/example/snapconnect/services/NotificationService;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "createNotificationChannels", "", "showFriendRequestNotification", "requesterName", "", "requesterId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "showMessageNotification", "senderName", "message", "groupId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "showStoryNotification", "userName", "userId", "Companion", "app_debug"})
public final class NotificationService {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_ID_MESSAGES = "snapconnect_messages";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_ID_FRIEND_REQUESTS = "snapconnect_friend_requests";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_ID_STORIES = "snapconnect_stories";
    public static final int NOTIFICATION_ID_MESSAGE = 1;
    public static final int NOTIFICATION_ID_FRIEND_REQUEST = 2;
    public static final int NOTIFICATION_ID_STORY = 3;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.snapconnect.services.NotificationService.Companion Companion = null;
    
    @javax.inject.Inject()
    public NotificationService(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final void createNotificationChannels() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object showMessageNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String senderName, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String groupId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object showFriendRequestNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String requesterName, @org.jetbrains.annotations.NotNull()
    java.lang.String requesterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object showStoryNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String userName, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/example/snapconnect/services/NotificationService$Companion;", "", "()V", "CHANNEL_ID_FRIEND_REQUESTS", "", "CHANNEL_ID_MESSAGES", "CHANNEL_ID_STORIES", "NOTIFICATION_ID_FRIEND_REQUEST", "", "NOTIFICATION_ID_MESSAGE", "NOTIFICATION_ID_STORY", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}