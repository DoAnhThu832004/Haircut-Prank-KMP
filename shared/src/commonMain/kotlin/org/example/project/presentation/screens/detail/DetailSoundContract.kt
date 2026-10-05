package org.example.project.presentation.screens.detail

import org.example.project.domain.model.Sound

data class DetailSoundUiState(
    val currentSound: Sound? = null,
    val otherSounds: List<Sound> = emptyList(),
    val isPlaying: Boolean = false,
    val isLooping: Boolean = false,
    val isVibrationEnabled: Boolean = true,
    val isFavorite: Boolean = false,
    val selectedTimerSeconds: Int = 0,
    val countdownRemainingSeconds: Int = 0,
    val isCountingDown: Boolean = false,
    val isLoading: Boolean = true
)

sealed interface DetailSoundUiEffect {
    data object NavigateBack : DetailSoundUiEffect
}
