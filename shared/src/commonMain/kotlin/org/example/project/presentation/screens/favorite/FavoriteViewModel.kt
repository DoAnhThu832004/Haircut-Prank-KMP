package org.example.project.presentation.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.domain.model.Sound
import org.example.project.domain.usecase.GetFavoriteSoundsUseCase
import org.example.project.domain.usecase.ToggleFavoriteUseCase

class FavoriteViewModel(
    getFavoriteSoundsUseCase: GetFavoriteSoundsUseCase = GetFavoriteSoundsUseCase(),
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = ToggleFavoriteUseCase()
) : ViewModel() {

    val uiState: StateFlow<FavoriteUiState> = getFavoriteSoundsUseCase()
        .map { sounds ->
            if (sounds.isEmpty()) {
                FavoriteUiState.Empty
            } else {
                FavoriteUiState.Success(sounds)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = FavoriteUiState.Loading
        )

    fun onToggleFavorite(sound: Sound) {
        viewModelScope.launch {
            toggleFavoriteUseCase(sound)
        }
    }
}
