package org.example.project.domain.usecase

import org.example.project.domain.repository.UserPreferencesRepository

class CompleteIntroUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke() {
        userPreferencesRepository.setIntroDone(true)
    }
}
