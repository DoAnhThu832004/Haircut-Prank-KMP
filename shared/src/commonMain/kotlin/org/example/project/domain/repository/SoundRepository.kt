package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.Sound

interface SoundRepository {
    fun getAllSounds(): Flow<List<Sound>>
    fun getSoundsByCategory(categoryId: String): Flow<List<Sound>>
    fun getFavoriteSounds(): Flow<List<Sound>>
    suspend fun getSoundByPath(path: String): Sound?
    suspend fun updateFavorite(path: String, isFavorite: Boolean, favTime: Long)
}
