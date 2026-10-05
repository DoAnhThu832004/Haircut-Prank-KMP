package org.example.project.data.storage

import org.example.project.domain.model.Sound

expect object PlatformSoundStorage {
    fun getInitialSounds(): List<Sound>
}
