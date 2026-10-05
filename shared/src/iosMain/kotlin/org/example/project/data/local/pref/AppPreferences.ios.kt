package org.example.project.data.local.pref

import platform.Foundation.NSUserDefaults

class IOSAppPreferences : AppPreferences {
    private val defaults = NSUserDefaults.standardUserDefaults

    companion object {
        private const val KEY_INTRO_DONE = "intro_done"
        private const val PREFIX_CATEGORY_VIEWED = "cat_viewed_"
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
}

private val iosPrefsInstance by lazy { IOSAppPreferences() }

actual fun getAppPreferences(): AppPreferences = iosPrefsInstance
