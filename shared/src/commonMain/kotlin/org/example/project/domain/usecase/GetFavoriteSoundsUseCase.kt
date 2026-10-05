package org.example.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.project.data.repository.SoundRepositoryImpl
import org.example.project.domain.model.Sound
import org.example.project.domain.repository.SoundRepository

class GetFavoriteSoundsUseCase(
    private val soundRepository: SoundRepository = SoundRepositoryImpl.getInstance()
) {
    operator fun invoke(): Flow<List<Sound>> {
        return soundRepository.getFavoriteSounds()
    }
}
