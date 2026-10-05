package org.example.project.data.local.pref

import platform.Foundation.NSUserDefaults

class IOSAppPreferences : AppPreferences {
    private val defaults = NSUserDefaults.standardUserDefaults

    companion object {
        private const val KEY_INTRO_DONE = "intro_done"
        private const val PREFIX_CATEGORY_VIEWED = "cat_viewed_"
        private const val PREFIX_SOUND_KNOWN = "sound_known_"
    }

    override fun isIntroDone(): Boolean = defaults.boolForKey(KEY_INTRO_DONE)

    override fun setIntroDone(done: Boolean) {
        defaults.setBool(done, forKey = KEY_INTRO_DONE)
    }

    override fun isNewCategoryViewed(categoryKey: String): Boolean =
        defaults.boolForKey(PREFIX_CATEGORY_VIEWED + categoryKey)

    override fun markNewCategoryViewed(categoryKey: String) {
        defaults.setBool(true, forKey = PREFIX_CATEGORY_VIEWED + categoryKey)
    }

    override fun isSoundKnown(key: String): Boolean =
        defaults.boolForKey(PREFIX_SOUND_KNOWN + key)

    override fun markSoundsKnown(keys: Collection<String>) {
        keys.forEach { defaults.setBool(true, forKey = PREFIX_SOUND_KNOWN + it) }
    }
}

private val iosPrefsInstance by lazy { IOSAppPreferences() }

actual fun getAppPreferences(): AppPreferences = iosPrefsInstance
