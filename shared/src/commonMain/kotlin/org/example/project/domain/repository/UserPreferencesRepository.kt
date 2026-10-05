package org.example.project.domain.repository

interface UserPreferencesRepository {
    fun isIntroDone(): Boolean
    fun setIntroDone(done: Boolean)
    fun isNewCategoryViewed(categoryKey: String): Boolean
    fun markNewCategoryViewed(categoryKey: String)
    fun isSoundKnown(key: String): Boolean
    fun markSoundsKnown(keys: Collection<String>)
}
