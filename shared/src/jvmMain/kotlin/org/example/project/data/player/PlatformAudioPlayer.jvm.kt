package org.example.project.data.player

import javazoom.jl.player.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class JvmAudioPlayer(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : PlatformAudioPlayer {

    @Volatile
    private var activePlayer: Player? = null
    private var playbackJob: Job? = null
    private var isLooping: Boolean = false
    private var currentFilePath: String = ""

    override fun play(filePath: String, loop: Boolean, onCompletion: (() -> Unit)?) {
        stop()
        isLooping = loop
        currentFilePath = filePath

        val file = resolveFile(filePath)
        if (file == null || !file.exists()) {
            println("JvmAudioPlayer: File not found: $filePath")
            onCompletion?.invoke()
            return
        }

        playbackJob = scope.launch {
            try {
                do {
                    val stream: InputStream = BufferedInputStream(FileInputStream(file))
                    val player = Player(stream)
                    activePlayer = player

                    player.play()

                } while (isLooping && isActive)
            } catch (e: Exception) {
                // Playback stopped or stream closed
            } finally {
                activePlayer = null
                if (!isLooping && isActive) {
                    onCompletion?.invoke()
                }
            }
        }
    }

    override fun stop() {
        playbackJob?.cancel()
        playbackJob = null
        try {
            activePlayer?.close()
        } catch (_: Exception) {}
        activePlayer = null
    }

    override fun setLoop(loop: Boolean) {
        isLooping = loop
    }

    override fun setVibrationEnabled(enabled: Boolean) {
        // Desktop does not support physical vibration
    }

    override fun release() {
        stop()
    }

    private fun resolveFile(path: String): File? {
        val direct = File(path)
        if (direct.exists()) return direct

        val candidateRoots = listOf(
            File("D:/test/HaircutPrank/sounds/taymay_haircut_version_4/4"),
            File(System.getProperty("user.home"), ".haircutprank/sounds/taymay_haircut_version_4/4"),
            File("./sounds/taymay_haircut_version_4/4")
        )

        val targetName = direct.name
        for (root in candidateRoots) {
            if (root.exists()) {
                val found = root.walkTopDown().firstOrNull { it.isFile && it.name.equals(targetName, ignoreCase = true) }
                if (found != null) return found
            }
        }

        return null
    }
}

actual fun createPlatformAudioPlayer(): PlatformAudioPlayer = JvmAudioPlayer()
