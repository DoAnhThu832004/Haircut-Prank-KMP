package org.example.project.data.local.pref

interface AppPreferences {
    fun isIntroDone(): Boolean
    fun setIntroDone(done: Boolean)
    fun isNewCategoryViewed(categoryKey: String): Boolean
    fun markNewCategoryViewed(categoryKey: String)
    fun isSoundKnown(key: String): Boolean
    fun markSoundsKnown(keys: Collection<String>)
}

expect fun getAppPreferences(): AppPreferences
