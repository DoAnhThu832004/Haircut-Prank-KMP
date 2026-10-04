package org.example.project

import kotlin.test.Test
import kotlin.test.assertFalse
import org.example.project.data.local.pref.getAppPreferences

class SharedLogicDesktopTest {

    @Test
    fun testPreferences() {
        val prefs = getAppPreferences()
        println(">>> TEST PREFERENCES isIntroDone initially: ${prefs.isIntroDone()}")
        assertFalse(prefs.isIntroDone())
    }
}