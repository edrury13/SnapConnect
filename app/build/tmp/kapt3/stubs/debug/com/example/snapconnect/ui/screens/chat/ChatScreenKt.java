package com.example.snapconnect.ui.screens.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001aJ\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0007\u001a\"\u0010\r\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007\u001a*\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\tH\u0007\u001a\"\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001b\u0010\u001c\u001a\u000e\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001f\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006 "}, d2 = {"ChatInput", "", "value", "", "onValueChange", "Lkotlin/Function1;", "onSend", "Lkotlin/Function0;", "isSending", "", "navController", "Landroidx/navigation/NavController;", "groupId", "ChatScreen", "viewModel", "Lcom/example/snapconnect/ui/screens/chat/ChatViewModel;", "MessageItem", "message", "Lcom/example/snapconnect/data/model/Message;", "sender", "Lcom/example/snapconnect/data/model/User;", "isCurrentUser", "showAvatar", "UserAvatar", "user", "size", "Landroidx/compose/ui/unit/Dp;", "UserAvatar-3ABfNKs", "(Lcom/example/snapconnect/data/model/User;F)V", "formatTime", "instant", "Lkotlinx/datetime/Instant;", "app_debug"})
public final class ChatScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ChatScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    java.lang.String groupId, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.ui.screens.chat.ChatViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MessageItem(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.Message message, @org.jetbrains.annotations.Nullable()
    com.example.snapconnect.data.model.User sender, boolean isCurrentUser, boolean showAvatar) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ChatInput(@org.jetbrains.annotations.NotNull()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSend, boolean isSending, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    java.lang.String groupId) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String formatTime(@org.jetbrains.annotations.NotNull()
    kotlinx.datetime.Instant instant) {
        return null;
    }
}