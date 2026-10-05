package org.example.project.data.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import java.io.File

class AndroidAudioPlayer : PlatformAudioPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private var isLooping: Boolean = false

    override fun play(filePath: String, loop: Boolean, onCompletion: (() -> Unit)?) {
        stop()
        val file = File(filePath)
        if (!file.exists()) {
            onCompletion?.invoke()
            return
        }

        try {
            this.isLooping = loop
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setDataSource(filePath)
                this.isLooping = loop
                setOnCompletionListener {
                    if (!this@AndroidAudioPlayer.isLooping) {
                        onCompletion?.invoke()
                    }
                }
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            stop()
        }
    }

    override fun stop() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) stop()
                reset()
                release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mediaPlayer = null
        }
    }

    override fun setLoop(loop: Boolean) {
        this.isLooping = loop
        mediaPlayer?.isLooping = loop
    }

    override fun setVibrationEnabled(enabled: Boolean) {
        // Handled via system Vibrator if needed
    }

    override fun release() {
        stop()
    }
}

actual fun createPlatformAudioPlayer(): PlatformAudioPlayer = AndroidAudioPlayer()
