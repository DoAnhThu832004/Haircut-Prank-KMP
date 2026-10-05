package org.example.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.repository.SoundRepositoryImpl
import org.example.project.data.repository.UserPreferencesRepositoryImpl
import org.example.project.domain.model.Sound
import org.example.project.domain.repository.SoundRepository
import org.example.project.domain.repository.UserPreferencesRepository

class GetSoundsByCategoryUseCase(
    private val soundRepository: SoundRepository = SoundRepositoryImpl.getInstance(),
    private val userPreferencesRepository: UserPreferencesRepository = UserPreferencesRepositoryImpl()
) {
    operator fun invoke(categoryName: String): Flow<List<Sound>> {
        return soundRepository.getSoundsByCategory(categoryName).map { list ->
            val soundsWithTag = list.map { sound ->
                val isKnown = userPreferencesRepository.isSoundKnown(sound.stableKey)
                sound.copy(isNew = !isKnown)
            }
            val (newSounds, otherSounds) = soundsWithTag.partition { it.isNew }
            newSounds + otherSounds
        }
    }
}
