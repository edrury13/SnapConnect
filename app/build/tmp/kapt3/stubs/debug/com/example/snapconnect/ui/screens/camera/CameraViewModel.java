package com.example.snapconnect.ui.screens.camera;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0012"}, d2 = {"Lcom/example/snapconnect/ui/screens/camera/CameraViewModel;", "Landroidx/lifecycle/ViewModel;", "filtersRepository", "Lcom/example/snapconnect/data/repository/FiltersRepository;", "filterProcessor", "Lcom/example/snapconnect/utils/FilterProcessor;", "videoFilterProcessor", "Lcom/example/snapconnect/utils/VideoFilterProcessor;", "(Lcom/example/snapconnect/data/repository/FiltersRepository;Lcom/example/snapconnect/utils/FilterProcessor;Lcom/example/snapconnect/utils/VideoFilterProcessor;)V", "availableFilters", "", "Lcom/example/snapconnect/data/model/ARFilter;", "getAvailableFilters", "()Ljava/util/List;", "getFilterProcessor", "()Lcom/example/snapconnect/utils/FilterProcessor;", "getVideoFilterProcessor", "()Lcom/example/snapconnect/utils/VideoFilterProcessor;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CameraViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.FiltersRepository filtersRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.utils.FilterProcessor filterProcessor = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.utils.VideoFilterProcessor videoFilterProcessor = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.snapconnect.data.model.ARFilter> availableFilters = null;
    
    @javax.inject.Inject()
    public CameraViewModel(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.FiltersRepository filtersRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.utils.FilterProcessor filterProcessor, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.utils.VideoFilterProcessor videoFilterProcessor) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.utils.FilterProcessor getFilterProcessor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.utils.VideoFilterProcessor getVideoFilterProcessor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.snapconnect.data.model.ARFilter> getAvailableFilters() {
        return null;
    }
}