package com.example.snapconnect.ui.screens.camera;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/snapconnect/ui/screens/camera/CameraViewModel;", "Landroidx/lifecycle/ViewModel;", "filtersRepository", "Lcom/example/snapconnect/data/repository/FiltersRepository;", "filterProcessor", "Lcom/example/snapconnect/utils/FilterProcessor;", "(Lcom/example/snapconnect/data/repository/FiltersRepository;Lcom/example/snapconnect/utils/FilterProcessor;)V", "availableFilters", "", "Lcom/example/snapconnect/data/model/ARFilter;", "getAvailableFilters", "()Ljava/util/List;", "getFilterProcessor", "()Lcom/example/snapconnect/utils/FilterProcessor;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CameraViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.data.repository.FiltersRepository filtersRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.snapconnect.utils.FilterProcessor filterProcessor = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.snapconnect.data.model.ARFilter> availableFilters = null;
    
    @javax.inject.Inject()
    public CameraViewModel(@org.jetbrains.annotations.NotNull()
    com.example.snapconnect.data.repository.FiltersRepository filtersRepository, @org.jetbrains.annotations.NotNull()
    com.example.snapconnect.utils.FilterProcessor filterProcessor) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.snapconnect.utils.FilterProcessor getFilterProcessor() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.snapconnect.data.model.ARFilter> getAvailableFilters() {
        return null;
    }
}