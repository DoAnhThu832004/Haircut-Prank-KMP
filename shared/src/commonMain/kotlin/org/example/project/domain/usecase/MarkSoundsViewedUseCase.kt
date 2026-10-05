package org.example.project.domain.usecase

import org.example.project.data.repository.UserPreferencesRepositoryImpl
import org.example.project.domain.repository.UserPreferencesRepository

class MarkSoundsViewedUseCase(
    private val userPreferencesRepository: UserPreferencesRepository = UserPreferencesRepositoryImpl()
) {
    operator fun invoke(soundKeys: Collection<String>) {
        userPreferencesRepository.markSoundsKnown(soundKeys)
    }
}
