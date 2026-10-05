package org.example.project.data.player

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AudioPlayerManager(
    private val platformPlayer: PlatformAudioPlayer = createPlatformAudioPlayer()
) {
    companion object {
        private val INSTANCE by lazy { AudioPlayerManager() }
        fun getInstance(): AudioPlayerManager = INSTANCE
    }

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isLooping = MutableStateFlow(false)
    val isLooping: StateFlow<Boolean> = _isLooping.asStateFlow()

    fun setVibrationEnabled(enabled: Boolean) {
        platformPlayer.setVibrationEnabled(enabled)
    }

    fun play(
        filePath: String,
        loop: Boolean = _isLooping.value,
        onFinished: (() -> Unit)? = null
    ) {
        stop()
        _isLooping.value = loop
        _isPlaying.value = true

        platformPlayer.play(filePath, loop) {
            _isPlaying.value = false
            onFinished?.invoke()
        }
    }

    fun setLoop(loop: Boolean) {
        _isLooping.value = loop
        platformPlayer.setLoop(loop)
    }

    fun stop() {
        platformPlayer.stop()
        _isPlaying.value = false
    }

    fun release() {
        platformPlayer.release()
        _isPlaying.value = false
    }
}
