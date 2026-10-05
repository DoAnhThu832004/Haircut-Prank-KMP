package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.domain.model.SoundCategory
import org.example.project.domain.repository.CategoryRepository

class CategoryRepositoryImpl : CategoryRepository {
    companion object {
        val DEFAULT_CATEGORIES = listOf(
            SoundCategory(id = 1, name = "Air Horn"),
            SoundCategory(id = 2, name = "Hair Clipper"),
            SoundCategory(id = 3, name = "Fart"),
            SoundCategory(id = 4, name = "Burp"),
            SoundCategory(id = 5, name = "Toilet Flushing"),
            SoundCategory(id = 6, name = "Gun"),
            SoundCategory(id = 7, name = "Breaking"),
            SoundCategory(id = 8, name = "Car"),
            SoundCategory(id = 9, name = "Meme"),
            SoundCategory(id = 10, name = "Bomb"),
            SoundCategory(id = 11, name = "Scary"),
            SoundCategory(id = 12, name = "Animals"),
            SoundCategory(id = 13, name = "Taser"),
            SoundCategory(id = 14, name = "Siren")
        )

        private val instance by lazy { CategoryRepositoryImpl() }
        fun getInstance(): CategoryRepository = instance
    }

    private val _categories = MutableStateFlow<List<SoundCategory>>(DEFAULT_CATEGORIES)

    override fun getAllCategories(): Flow<List<SoundCategory>> = _categories.asStateFlow()

    override suspend fun insertAll(categories: List<SoundCategory>) {
        _categories.value = categories
    }
}
