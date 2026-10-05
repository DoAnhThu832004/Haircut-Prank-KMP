package org.example.project.data.storage

import org.example.project.domain.model.Sound

actual object PlatformSoundStorage {
    actual fun getInitialSounds(): List<Sound> {
        return emptyList()
    }
}
