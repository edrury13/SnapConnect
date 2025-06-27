package com.example.snapconnect.data.model;

@kotlinx.serialization.Serializable()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b7\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 Y2\u00020\u0001:\u0002XYB\u00c3\u0001\b\u0011\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u0010\b\u0001\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\f\u0012\b\b\u0001\u0010\r\u001a\u00020\u000e\u0012\n\b\u0001\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0001\u0010\u0011\u001a\u0004\u0018\u00010\u0010\u0012\u0010\b\u0001\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\f\u0012\n\b\u0001\u0010\u0013\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0001\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0015\u001a\u00020\u0003\u0012\n\b\u0001\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019\u00a2\u0006\u0002\u0010\u001aB\u0097\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\f\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\u0002\u0010\u001bJ\t\u0010=\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u00c6\u0003J\u000b\u0010?\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u0017H\u00c6\u0003J\t\u0010C\u001a\u00020\u0005H\u00c6\u0003J\t\u0010D\u001a\u00020\u0005H\u00c6\u0003J\t\u0010E\u001a\u00020\tH\u00c6\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u00c6\u0003J\t\u0010H\u001a\u00020\u000eH\u00c6\u0003J\t\u0010I\u001a\u00020\u0010H\u00c6\u0003J\t\u0010J\u001a\u00020\u0010H\u00c6\u0003J\u00a7\u0001\u0010K\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00102\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u00c6\u0001J\u0013\u0010L\u001a\u00020\u000e2\b\u0010M\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010N\u001a\u00020\u0003H\u00d6\u0001J\t\u0010O\u001a\u00020\u0005H\u00d6\u0001J&\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\u00002\u0006\u0010S\u001a\u00020T2\u0006\u0010U\u001a\u00020VH\u00c1\u0001\u00a2\u0006\u0002\bWR\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u001c\u0010\u000f\u001a\u00020\u00108\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b!\u0010\u001d\u001a\u0004\b\"\u0010#R\u001c\u0010\u0015\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b$\u0010\u001d\u001a\u0004\b%\u0010&R\u001c\u0010\u0011\u001a\u00020\u00108\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\'\u0010\u001d\u001a\u0004\b(\u0010#R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001fR\u001c\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b*\u0010\u001d\u001a\u0004\b\r\u0010+R\u001c\u0010\u0014\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b,\u0010\u001d\u001a\u0004\b-\u0010&R\u001c\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b.\u0010\u001d\u001a\u0004\b/\u00100R\u001c\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b1\u0010\u001d\u001a\u0004\b2\u0010\u001fR\"\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\f8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b3\u0010\u001d\u001a\u0004\b4\u00105R\u001c\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b6\u0010\u001d\u001a\u0004\b7\u0010\u001fR\u001e\u0010\u0016\u001a\u0004\u0018\u00010\u00178\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b8\u0010\u001d\u001a\u0004\b9\u0010:R\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b;\u0010\u001d\u001a\u0004\b<\u00105\u00a8\u0006Z"}, d2 = {"Lcom/example/snapconnect/data/model/Story;", "", "seen1", "", "id", "", "userId", "mediaUrl", "mediaType", "Lcom/example/snapconnect/data/model/MediaType;", "caption", "viewerIds", "", "isPublic", "", "createdAt", "Lkotlinx/datetime/Instant;", "expiresAt", "styleTags", "aiCaption", "likesCount", "dislikesCount", "userReaction", "Lcom/example/snapconnect/data/model/ReactionType;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/example/snapconnect/data/model/MediaType;Ljava/lang/String;Ljava/util/List;ZLkotlinx/datetime/Instant;Lkotlinx/datetime/Instant;Ljava/util/List;Ljava/lang/String;IILcom/example/snapconnect/data/model/ReactionType;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/example/snapconnect/data/model/MediaType;Ljava/lang/String;Ljava/util/List;ZLkotlinx/datetime/Instant;Lkotlinx/datetime/Instant;Ljava/util/List;Ljava/lang/String;IILcom/example/snapconnect/data/model/ReactionType;)V", "getAiCaption$annotations", "()V", "getAiCaption", "()Ljava/lang/String;", "getCaption", "getCreatedAt$annotations", "getCreatedAt", "()Lkotlinx/datetime/Instant;", "getDislikesCount$annotations", "getDislikesCount", "()I", "getExpiresAt$annotations", "getExpiresAt", "getId", "isPublic$annotations", "()Z", "getLikesCount$annotations", "getLikesCount", "getMediaType$annotations", "getMediaType", "()Lcom/example/snapconnect/data/model/MediaType;", "getMediaUrl$annotations", "getMediaUrl", "getStyleTags$annotations", "getStyleTags", "()Ljava/util/List;", "getUserId$annotations", "getUserId", "getUserReaction$annotations", "getUserReaction", "()Lcom/example/snapconnect/data/model/ReactionType;", "getViewerIds$annotations", "getViewerIds", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "write$Self$app_debug", "$serializer", "Companion", "app_debug"})
public final class Story {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String userId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String mediaUrl = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.model.MediaType mediaType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String caption = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> viewerIds = null;
    private final boolean isPublic = false;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.datetime.Instant createdAt = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.datetime.Instant expiresAt = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> styleTags = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String aiCaption = null;
    private final int likesCount = 0;
    private final int dislikesCount = 0;
    @org.jetbrains.annotations.Nullable()
    private final com.example.snapconnect.data.model.ReactionType userReaction = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.snapconnect.data.model.Story.Companion Companion = null;
    
    public Story(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String mediaUrl, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.MediaType mediaType, @org.jetbrains.annotations.Nullable()
    java.lang.String caption, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> viewerIds, boolean isPublic, @org.jetbrains.annotations.NotNull()
    kotlinx.datetime.Instant createdAt, @org.jetbrains.annotations.NotNull()
    kotlinx.datetime.Instant expiresAt, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> styleTags, @org.jetbrains.annotations.Nullable()
    java.lang.String aiCaption, int likesCount, int dislikesCount, @org.jetbrains.annotations.Nullable()
    com.example.snapconnect.data.model.ReactionType userReaction) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserId() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "user_id")
    @java.lang.Deprecated()
    public static void getUserId$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMediaUrl() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "media_url")
    @java.lang.Deprecated()
    public static void getMediaUrl$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.data.model.MediaType getMediaType() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "media_type")
    @java.lang.Deprecated()
    public static void getMediaType$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCaption() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getViewerIds() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "viewer_ids")
    @java.lang.Deprecated()
    public static void getViewerIds$annotations() {
    }
    
    public final boolean isPublic() {
        return false;
    }
    
    @kotlinx.serialization.SerialName(value = "is_public")
    @java.lang.Deprecated()
    public static void isPublic$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.datetime.Instant getCreatedAt() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "created_at")
    @java.lang.Deprecated()
    public static void getCreatedAt$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.datetime.Instant getExpiresAt() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "expires_at")
    @java.lang.Deprecated()
    public static void getExpiresAt$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getStyleTags() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "style_tags")
    @java.lang.Deprecated()
    public static void getStyleTags$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAiCaption() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "ai_caption")
    @java.lang.Deprecated()
    public static void getAiCaption$annotations() {
    }
    
    public final int getLikesCount() {
        return 0;
    }
    
    @kotlinx.serialization.SerialName(value = "likes_count")
    @java.lang.Deprecated()
    public static void getLikesCount$annotations() {
    }
    
    public final int getDislikesCount() {
        return 0;
    }
    
    @kotlinx.serialization.SerialName(value = "dislikes_count")
    @java.lang.Deprecated()
    public static void getDislikesCount$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.snapconnect.data.model.ReactionType getUserReaction() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "user_reaction")
    @java.lang.Deprecated()
    public static void getUserReaction$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final int component13() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.snapconnect.data.model.ReactionType component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.data.model.MediaType component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.datetime.Instant component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.datetime.Instant component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.data.model.Story copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String mediaUrl, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.MediaType mediaType, @org.jetbrains.annotations.Nullable()
    java.lang.String caption, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> viewerIds, boolean isPublic, @org.jetbrains.annotations.NotNull()
    kotlinx.datetime.Instant createdAt, @org.jetbrains.annotations.NotNull()
    kotlinx.datetime.Instant expiresAt, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> styleTags, @org.jetbrains.annotations.Nullable()
    java.lang.String aiCaption, int likesCount, int dislikesCount, @org.jetbrains.annotations.Nullable()
    com.example.snapconnect.data.model.ReactionType userReaction) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.jvm.JvmStatic()
    public static final void write$Self$app_debug(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.Story self, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.descriptors.SerialDescriptor serialDesc) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tH\u00d6\u0001\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u00d6\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"com/example/snapconnect/data/model/Story.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/example/snapconnect/data/model/Story;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_debug"})
    @java.lang.Deprecated()
    public static final class $serializer implements kotlinx.serialization.internal.GeneratedSerializer<com.example.snapconnect.data.model.Story> {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.snapconnect.data.model.Story.$serializer INSTANCE = null;
        
        private $serializer() {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] childSerializers() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.example.snapconnect.data.model.Story deserialize(@org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.Decoder decoder) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.descriptors.SerialDescriptor getDescriptor() {
            return null;
        }
        
        @java.lang.Override()
        public void serialize(@org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.Encoder encoder, @org.jetbrains.annotations.NotNull()
        com.example.snapconnect.data.model.Story value) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2 = {"Lcom/example/snapconnect/data/model/Story$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/example/snapconnect/data/model/Story;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlinx.serialization.KSerializer<com.example.snapconnect.data.model.Story> serializer() {
            return null;
        }
    }
}