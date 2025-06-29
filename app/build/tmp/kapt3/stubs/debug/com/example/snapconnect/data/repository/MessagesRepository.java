package com.example.snapconnect.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J>\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u0012\u001a\u00020\b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\t2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016J*\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\t0\f2\u0006\u0010\u0019\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bJ \u0010\u001c\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\n0\u001e0\t0\u001dJ4\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f2\u0006\u0010 \u001a\u00020\b2\b\b\u0002\u0010!\u001a\u00020\"H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b#\u0010$J\u001a\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u001d2\u0006\u0010 \u001a\u00020\bJ0\u0010&\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\n0\u001e0\t0\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\'\u0010(J\u0016\u0010)\u001a\u00020*2\u0006\u0010 \u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0010JD\u0010+\u001a\b\u0012\u0004\u0012\u00020\n0\f2\u0006\u0010 \u001a\u00020\b2\u0006\u0010,\u001a\u00020\b2\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010.\u001a\u0004\u0018\u00010/H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b0\u00101J,\u00102\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010 \u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b3\u00104R&\u0010\u0005\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00065"}, d2 = {"Lcom/example/snapconnect/data/repository/MessagesRepository;", "", "supabase", "Lio/github/jan/supabase/SupabaseClient;", "(Lio/github/jan/supabase/SupabaseClient;)V", "messagesCache", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "", "Lcom/example/snapconnect/data/model/Message;", "createDirectMessageGroup", "Lkotlin/Result;", "Lcom/example/snapconnect/data/model/Group;", "friendId", "createDirectMessageGroup-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createGroup", "name", "memberIds", "avatarUrl", "createGroup-BWLJW6A", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGroupMembers", "Lcom/example/snapconnect/data/model/User;", "group", "getGroupMembers-gIAlu-s", "(Lcom/example/snapconnect/data/model/Group;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGroupsRealtime", "Lkotlinx/coroutines/flow/Flow;", "Lkotlin/Pair;", "getMessages", "groupId", "limit", "", "getMessages-0E7RQCE", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMessagesRealtime", "getMyGroups", "getMyGroups-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markMessagesAsRead", "", "sendMessage", "content", "mediaUrl", "mediaType", "Lcom/example/snapconnect/data/model/MediaType;", "sendMessage-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/example/snapconnect/data/model/MediaType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateGroupAvatar", "updateGroupAvatar-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MessagesRepository {
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.SupabaseClient supabase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, java.util.List<com.example.snapconnect.data.model.Message>>> messagesCache = null;
    
    @javax.inject.Inject()
    public MessagesRepository(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.SupabaseClient supabase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.snapconnect.data.model.Message>> getMessagesRealtime(@org.jetbrains.annotations.NotNull()
    java.lang.String groupId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<kotlin.Pair<com.example.snapconnect.data.model.Group, com.example.snapconnect.data.model.Message>>> getGroupsRealtime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markMessagesAsRead(@org.jetbrains.annotations.NotNull()
    java.lang.String groupId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}