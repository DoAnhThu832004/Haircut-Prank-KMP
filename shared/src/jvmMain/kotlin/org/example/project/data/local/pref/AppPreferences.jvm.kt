package org.example.project.data.local.pref

import java.util.prefs.Preferences

class JVMAppPreferences : AppPreferences {
    private val prefs: Preferences = Preferences.userRoot().node("haircut_prank")

    companion object {
        private const val KEY_INTRO_DONE = "intro_done"
    }

    override fun isIntroDone(): Boolean = prefs.getBoolean(KEY_INTRO_DONE, false)

    override fun setIntroDone(done: Boolean) {
        prefs.putBoolean(KEY_INTRO_DONE, done)
    }
}

private val jvmPrefsInstance by lazy { JVMAppPreferences() }

actual fun getAppPreferences(): AppPreferences = jvmPrefsInstance
