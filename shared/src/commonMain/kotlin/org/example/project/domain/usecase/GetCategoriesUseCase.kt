package org.example.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.repository.CategoryRepositoryImpl
import org.example.project.data.repository.UserPreferencesRepositoryImpl
import org.example.project.domain.model.SoundCategory
import org.example.project.domain.repository.CategoryRepository
import org.example.project.domain.repository.UserPreferencesRepository

class GetCategoriesUseCase(
    private val repository: CategoryRepository = CategoryRepositoryImpl.getInstance(),
    private val userPreferencesRepository: UserPreferencesRepository = UserPreferencesRepositoryImpl()
) {
    private val newTagCategories = setOf("siren", "taser", "scary", "animals")

    operator fun invoke(): Flow<List<SoundCategory>> {
        return repository.getAllCategories().map { list ->
            list.map { category ->
                val key = category.normalizedKey
                val shouldShowNew = key in newTagCategories && !userPreferencesRepository.isNewCategoryViewed(key)
                category.copy(isNew = shouldShowNew)
            }
        }
    }
}
