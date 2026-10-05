package org.example.project.data.local.pref

interface AppPreferences {
    fun isIntroDone(): Boolean
    fun setIntroDone(done: Boolean)
    fun isNewCategoryViewed(categoryKey: String): Boolean
    fun markNewCategoryViewed(categoryKey: String)
}

expect fun getAppPreferences(): AppPreferences
