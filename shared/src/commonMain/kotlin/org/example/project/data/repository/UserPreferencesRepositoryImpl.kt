package org.example.project.data.repository

import org.example.project.data.local.pref.AppPreferences
import org.example.project.data.local.pref.getAppPreferences
import org.example.project.domain.repository.UserPreferencesRepository

class UserPreferencesRepositoryImpl(
    private val appPreferences: AppPreferences = getAppPreferences()
) : UserPreferencesRepository {
    override fun isIntroDone(): Boolean = appPreferences.isIntroDone()
    override fun setIntroDone(done: Boolean) = appPreferences.setIntroDone(done)
    override fun isNewCategoryViewed(categoryKey: String): Boolean =
        appPreferences.isNewCategoryViewed(categoryKey)
    override fun markNewCategoryViewed(categoryKey: String) =
        appPreferences.markNewCategoryViewed(categoryKey)
}
