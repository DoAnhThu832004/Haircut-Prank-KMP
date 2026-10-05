package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.SoundCategory

interface CategoryRepository {
    fun getAllCategories(): Flow<List<SoundCategory>>
    suspend fun insertAll(categories: List<SoundCategory>)
}
