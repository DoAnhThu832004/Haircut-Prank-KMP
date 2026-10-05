package org.example.project.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.player.AudioPlayerManager
import org.example.project.domain.model.Sound
import org.example.project.domain.usecase.GetFavoriteSoundsUseCase
import org.example.project.domain.usecase.GetSoundByPathUseCase
import org.example.project.domain.usecase.GetSoundsByCategoryUseCase
import org.example.project.domain.usecase.ToggleFavoriteUseCase

class DetailSoundViewModel(
    val categoryName: String = "",
    val soundPath: String = "",
    private val getSoundByPathUseCase: GetSoundByPathUseCase = GetSoundByPathUseCase(),
    private val getSoundsByCategoryUseCase: GetSoundsByCategoryUseCase = GetSoundsByCategoryUseCase(),
    private val getFavoriteSoundsUseCase: GetFavoriteSoundsUseCase = GetFavoriteSoundsUseCase(),
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = ToggleFavoriteUseCase(),
    private val audioPlayerManager: AudioPlayerManager = AudioPlayerManager.getInstance()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailSoundUiState())
    val uiState: StateFlow<DetailSoundUiState> = _uiState.asStateFlow()

    private val _effectChannel = Channel<DetailSoundUiEffect>(Channel.BUFFERED)
    val effectFlow = _effectChannel.receiveAsFlow()

    private var countdownJob: Job? = null

    init {
        loadInitialData(soundPath)
        observePlayerState()
    }

    private fun observePlayerState() {
        viewModelScope.launch {
            audioPlayerManager.isPlaying.collect { playing ->
                _uiState.update { it.copy(isPlaying = playing) }
            }
        }
    }

    private fun loadInitialData(targetPath: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val sound = getSoundByPathUseCase(targetPath)
            if (sound != null) {
                _uiState.update {
                    it.copy(
                        currentSound = sound,
                        isFavorite = sound.checkFavorite,
                        isLoading = false
                    )
                }
            } else {
                val fallback = Sound(
                    id = 1,
                    idCategory = categoryName,
                    name = targetPath.substringBeforeLast(".mp3").replace("_", " "),
                    pathSound = targetPath
                )
                _uiState.update {
                    it.copy(
                        currentSound = fallback,
                        isFavorite = false,
                        isLoading = false
                    )
                }
            }

            if (categoryName == "FAVORITE") {
                getFavoriteSoundsUseCase().collect { list ->
                    _uiState.update { it.copy(otherSounds = list) }
                }
            } else {
                getSoundsByCategoryUseCase(categoryName).collect { list ->
                    _uiState.update { it.copy(otherSounds = list) }
                }
            }
        }
    }

    fun onTogglePlay() {
        val state = _uiState.value
        val sound = state.currentSound ?: return

        when {
            state.isCountingDown -> {
                cancelCountdown()
            }
            state.isPlaying -> {
                audioPlayerManager.stop()
            }
            state.selectedTimerSeconds > 0 -> {
                startCountdown(state.selectedTimerSeconds, sound.pathSound)
            }
            else -> {
                audioPlayerManager.play(sound.pathSound, state.isLooping)
            }
        }
    }

    private fun startCountdown(seconds: Int, path: String) {
        countdownJob?.cancel()
        _uiState.update {
            it.copy(
                isCountingDown = true,
                countdownRemainingSeconds = seconds
            )
        }

        countdownJob = viewModelScope.launch {
            var remaining = seconds
            while (remaining > 0) {
                delay(1000L)
                remaining--
                _uiState.update { it.copy(countdownRemainingSeconds = remaining) }
            }
            _uiState.update { it.copy(isCountingDown = false) }
            audioPlayerManager.play(path, _uiState.value.isLooping)
        }
    }

    private fun cancelCountdown() {
        countdownJob?.cancel()
        countdownJob = null
        _uiState.update {
            it.copy(
                isCountingDown = false,
                countdownRemainingSeconds = 0
            )
        }
    }

    fun onSelectTimer(seconds: Int) {
        cancelCountdown()
        _uiState.update { it.copy(selectedTimerSeconds = seconds) }
    }

    fun onToggleLoop(loop: Boolean) {
        _uiState.update { it.copy(isLooping = loop) }
        audioPlayerManager.setLoop(loop)
    }

    fun onToggleVibration() {
        val newVibrate = !_uiState.value.isVibrationEnabled
        _uiState.update { it.copy(isVibrationEnabled = newVibrate) }
        audioPlayerManager.setVibrationEnabled(newVibrate)
    }

    fun onToggleFavorite() {
        val sound = _uiState.value.currentSound ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(sound)
            val updated = sound.copy(checkFavorite = !sound.checkFavorite)
            _uiState.update {
                it.copy(
                    currentSound = updated,
                    isFavorite = updated.checkFavorite
                )
            }
        }
    }

    fun onSelectSound(newSound: Sound) {
        if (_uiState.value.currentSound?.pathSound == newSound.pathSound) return
        cancelCountdown()
        audioPlayerManager.stop()

        _uiState.update {
            it.copy(
                currentSound = newSound,
                isFavorite = newSound.checkFavorite
            )
        }
    }

    fun onBackClick() {
        cancelCountdown()
        audioPlayerManager.stop()
        viewModelScope.launch {
            _effectChannel.send(DetailSoundUiEffect.NavigateBack)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelCountdown()
        audioPlayerManager.release()
    }
}
