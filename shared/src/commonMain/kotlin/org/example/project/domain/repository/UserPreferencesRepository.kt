package org.example.project.domain.repository

interface UserPreferencesRepository {
    fun isIntroDone(): Boolean
    fun setIntroDone(done: Boolean)
    fun isNewCategoryViewed(categoryKey: String): Boolean
    fun markNewCategoryViewed(categoryKey: String)
}
