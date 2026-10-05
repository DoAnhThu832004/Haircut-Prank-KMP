package org.example.project.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.domain.model.SoundCategory
import org.example.project.domain.usecase.GetCategoriesUseCase
import org.example.project.domain.usecase.MarkCategoryViewedUseCase

class HomeViewModel(
    getCategoriesUseCase: GetCategoriesUseCase = GetCategoriesUseCase(),
    private val markCategoryViewedUseCase: MarkCategoryViewedUseCase = MarkCategoryViewedUseCase()
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = getCategoriesUseCase()
        .map { categories ->
            HomeUiState(isLoading = false, categories = categories)
        }
        .catch { throwable ->
            emit(HomeUiState(isLoading = false, errorMessage = throwable.message))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(isLoading = true)
        )

    private val _effectChannel = Channel<HomeUiEffect>(Channel.BUFFERED)
    val effectFlow = _effectChannel.receiveAsFlow()

    fun onCategoryClick(category: SoundCategory) {
        viewModelScope.launch {
            markCategoryViewedUseCase(category.normalizedKey)
            _effectChannel.send(HomeUiEffect.NavigateToListSound(category.name))
        }
    }

    fun onSettingsClick() {
        viewModelScope.launch {
            _effectChannel.send(HomeUiEffect.NavigateToSettings)
        }
    }
}
