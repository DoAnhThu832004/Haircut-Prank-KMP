package org.example.project.data.local.pref

import java.util.prefs.Preferences

class JVMAppPreferences : AppPreferences {
    private val prefs: Preferences = Preferences.userRoot().node("haircut_prank")

    companion object {
        private const val KEY_INTRO_DONE = "intro_done"
        private const val PREFIX_CATEGORY_VIEWED = "cat_viewed_"
        private const val PREFIX_SOUND_KNOWN = "sound_known_"
    }

    override fun isIntroDone(): Boolean = prefs.getBoolean(KEY_INTRO_DONE, false)

    override fun setIntroDone(done: Boolean) {
        prefs.putBoolean(KEY_INTRO_DONE, done)
    }

    override fun isNewCategoryViewed(categoryKey: String): Boolean =
        prefs.getBoolean(PREFIX_CATEGORY_VIEWED + categoryKey, false)

    override fun markNewCategoryViewed(categoryKey: String) {
        prefs.putBoolean(PREFIX_CATEGORY_VIEWED + categoryKey, true)
    }

    override fun isSoundKnown(key: String): Boolean =
        prefs.getBoolean(PREFIX_SOUND_KNOWN + key, false)

    override fun markSoundsKnown(keys: Collection<String>) {
        keys.forEach { prefs.putBoolean(PREFIX_SOUND_KNOWN + it, true) }
    }
}

private val jvmPrefsInstance by lazy { JVMAppPreferences() }

actual fun getAppPreferences(): AppPreferences = jvmPrefsInstance
