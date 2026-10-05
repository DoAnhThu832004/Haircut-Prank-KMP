package org.example.project.data.player

interface PlatformAudioPlayer {
    fun play(filePath: String, loop: Boolean, onCompletion: (() -> Unit)?)
    fun stop()
    fun setLoop(loop: Boolean)
    fun setVibrationEnabled(enabled: Boolean)
    fun release()
}

expect fun createPlatformAudioPlayer(): PlatformAudioPlayer
