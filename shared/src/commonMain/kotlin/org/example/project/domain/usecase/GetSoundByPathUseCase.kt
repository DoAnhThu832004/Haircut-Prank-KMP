package org.example.project.domain.usecase

import org.example.project.data.repository.SoundRepositoryImpl
import org.example.project.domain.model.Sound
import org.example.project.domain.repository.SoundRepository

class GetSoundByPathUseCase(
    private val soundRepository: SoundRepository = SoundRepositoryImpl.getInstance()
) {
    suspend operator fun invoke(path: String): Sound? {
        return soundRepository.getSoundByPath(path)
    }
}
