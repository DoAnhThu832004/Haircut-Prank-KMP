package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import org.example.project.domain.model.Sound
import org.example.project.domain.repository.SoundRepository

class SoundRepositoryImpl : SoundRepository {
    companion object {
        private fun generateInitialSounds(): List<Sound> {
            val categories = CategoryRepositoryImpl.DEFAULT_CATEGORIES
            val list = mutableListOf<Sound>()
            categories.forEach { cat ->
                val count = when (cat.name) {
                    "Hair Clipper" -> 28
                    "Air Horn" -> 16
                    "Fart" -> 33
                    "Car" -> 22
                    "Animals" -> 25
                    "Gun" -> 17
                    "Burp" -> 20
                    else -> 15
                }
                for (i in 1..count) {
                    list.add(
                        Sound(
                            id = i,
                            idCategory = cat.name,
                            pathSound = "${cat.normalizedKey}_sound_$i.mp3",
                            name = "Sound $i",
                            checkFavorite = false
                        )
                    )
                }
            }
            return list
        }

        private val instance by lazy {
            SoundRepositoryImpl().apply {
                val realSounds = org.example.project.data.storage.PlatformSoundStorage.getInitialSounds()
                setSounds(if (realSounds.isNotEmpty()) realSounds else generateInitialSounds())
            }
        }
        fun getInstance(): SoundRepository = instance
    }

    private val _sounds = MutableStateFlow<List<Sound>>(emptyList())

    override fun getAllSounds(): Flow<List<Sound>> = _sounds.asStateFlow()

    override fun getSoundsByCategory(categoryId: String): Flow<List<Sound>> =
        _sounds.map { list ->
            list.filter {
                it.idCategory.equals(categoryId, ignoreCase = true) ||
                it.idCategory.replace(" ", "_").equals(categoryId.replace(" ", "_"), ignoreCase = true)
            }
        }

    override fun getFavoriteSounds(): Flow<List<Sound>> =
        _sounds.map { list -> list.filter { it.checkFavorite }.sortedByDescending { it.favoriteTime } }

    override suspend fun getSoundByPath(path: String): Sound? =
        _sounds.value.find { it.pathSound == path }

    override suspend fun updateFavorite(path: String, isFavorite: Boolean, favTime: Long) {
        _sounds.value = _sounds.value.map {
            if (it.pathSound == path) {
                it.copy(checkFavorite = isFavorite, favoriteTime = favTime)
            } else {
                it
            }
        }
    }

    fun setSounds(sounds: List<Sound>) {
        _sounds.value = sounds
    }
}
