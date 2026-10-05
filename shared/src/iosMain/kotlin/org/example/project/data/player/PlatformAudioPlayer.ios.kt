package org.example.project.data.player

class IosAudioPlayer : PlatformAudioPlayer {
    override fun play(filePath: String, loop: Boolean, onCompletion: (() -> Unit)?) {
        onCompletion?.invoke()
    }
    override fun stop() {}
    override fun setLoop(loop: Boolean) {}
    override fun setVibrationEnabled(enabled: Boolean) {}
    override fun release() {}
}

actual fun createPlatformAudioPlayer(): PlatformAudioPlayer = IosAudioPlayer()
