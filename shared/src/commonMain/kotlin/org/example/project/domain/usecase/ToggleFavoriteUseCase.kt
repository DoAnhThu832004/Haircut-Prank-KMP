package org.example.project.domain.usecase

import org.example.project.data.repository.SoundRepositoryImpl
import org.example.project.domain.model.Sound
import org.example.project.domain.repository.SoundRepository
import kotlin.time.TimeSource

class ToggleFavoriteUseCase(
    private val soundRepository: SoundRepository = SoundRepositoryImpl.getInstance()
) {
    suspend operator fun invoke(sound: Sound) {
        val newFavoriteState = !sound.checkFavorite
        val newFavTime = if (newFavoriteState) TimeSource.Monotonic.markNow().elapsedNow().inWholeMilliseconds else 0L
        soundRepository.updateFavorite(
            path = sound.pathSound,
            isFavorite = newFavoriteState,
            favTime = newFavTime
        )
    }
}
