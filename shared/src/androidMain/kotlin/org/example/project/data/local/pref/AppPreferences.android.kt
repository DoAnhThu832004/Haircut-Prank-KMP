package org.example.project.data.local.pref

import android.content.Context
import android.content.SharedPreferences

object AndroidContextProvider {
    var applicationContext: Context? = null
}

class AndroidAppPreferences(context: Context) : AppPreferences {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_INTRO_DONE = "intro_done"
        private const val PREFIX_CATEGORY_VIEWED = "cat_viewed_"
    }

    override fun isIntroDone(): Boolean = prefs.getBoolean(KEY_INTRO_DONE, false)

    override fun setIntroDone(done: Boolean) {
        prefs.edit().putBoolean(KEY_INTRO_DONE, done).apply()
    }

    override fun isNewCategoryViewed(categoryKey: String): Boolean =
        prefs.getBoolean(PREFIX_CATEGORY_VIEWED + categoryKey, false)

    override fun markNewCategoryViewed(categoryKey: String) {
        prefs.edit().putBoolean(PREFIX_CATEGORY_VIEWED + categoryKey, true).apply()
    }
}

actual fun getAppPreferences(): AppPreferences {
    val context = checkNotNull(AndroidContextProvider.applicationContext) {
        "AndroidContextProvider.applicationContext must be initialized in Application or MainActivity!"
    }
    return AndroidAppPreferences(context)
}
