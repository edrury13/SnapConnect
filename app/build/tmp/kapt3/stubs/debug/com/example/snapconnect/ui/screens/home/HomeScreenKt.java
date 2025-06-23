package com.example.snapconnect.ui.screens.home;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000X\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u001a\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a&\u0010\t\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a&\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a&\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001aP\u0010\u0017\u001a\u00020\u00012\u0018\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u001a0\u00192\u001e\u0010\u0012\u001a\u001a\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u001a\u0012\u0004\u0012\u00020\u00010\u001b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f\u001a\u0016\u0010 \u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u001d\u00a8\u0006\""}, d2 = {"EmptyStoriesState", "", "onAddStory", "Lkotlin/Function0;", "HomeScreen", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/example/snapconnect/ui/screens/home/HomeViewModel;", "StoriesContent", "uiState", "Lcom/example/snapconnect/ui/screens/home/HomeUiState;", "onRefresh", "StoryCard", "user", "Lcom/example/snapconnect/data/model/User;", "story", "Lcom/example/snapconnect/data/model/Story;", "onStoryClick", "StoryCircle", "hasUnseenStory", "", "onClick", "StoryCircles", "userStories", "", "", "Lkotlin/Function2;", "getTimeAgo", "", "instant", "Lkotlinx/datetime/Instant;", "hasUserSeenStory", "userId", "app_debug"})
public final class HomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.ui.screens.home.HomeViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StoriesContent(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.ui.screens.home.HomeUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StoryCircles(@org.jetbrains.annotations.NotNull()
    java.util.Map<com.example.snapconnect.data.model.User, ? extends java.util.List<com.example.snapconnect.data.model.Story>> userStories, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.example.snapconnect.data.model.User, ? super java.util.List<com.example.snapconnect.data.model.Story>, kotlin.Unit> onStoryClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddStory) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StoryCircle(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.User user, boolean hasUnseenStory, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void StoryCard(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.User user, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.Story story, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStoryClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptyStoriesState(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddStory) {
    }
    
    public static final boolean hasUserSeenStory(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.model.Story story, @org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getTimeAgo(@org.jetbrains.annotations.NotNull()
    kotlinx.datetime.Instant instant) {
        return null;
    }
}