package org.example.project.domain.usecase

import org.example.project.data.repository.UserPreferencesRepositoryImpl
import org.example.project.domain.repository.UserPreferencesRepository

class MarkCategoryViewedUseCase(
    private val userPreferencesRepository: UserPreferencesRepository = UserPreferencesRepositoryImpl()
) {
    operator fun invoke(categoryKey: String) {
        userPreferencesRepository.markNewCategoryViewed(categoryKey)
    }
}
