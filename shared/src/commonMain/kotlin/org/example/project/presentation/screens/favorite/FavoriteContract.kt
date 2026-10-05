package org.example.project.presentation.screens.favorite

import org.example.project.domain.model.Sound

sealed interface FavoriteUiState {
    data object Loading : FavoriteUiState
    data object Empty : FavoriteUiState
    data class Success(val sounds: List<Sound>) : FavoriteUiState
}
