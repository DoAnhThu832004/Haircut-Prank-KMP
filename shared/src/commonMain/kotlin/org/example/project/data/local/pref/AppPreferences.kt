package org.example.project.data.local.pref

interface AppPreferences {
    fun isIntroDone(): Boolean
    fun setIntroDone(done: Boolean)
}

expect fun getAppPreferences(): AppPreferences
