package org.example.project.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.TimeSource

class SplashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    private val _effectChannel = Channel<SplashUiEffect>(Channel.BUFFERED)
    val effectFlow = _effectChannel.receiveAsFlow()

    private var hasStarted = false

    init {
        startPreparation()
    }

    fun startPreparation() {
        if (hasStarted) return
        hasStarted = true
        viewModelScope.launch {
            _uiState.value = SplashUiState.Loading
            val timeSource = TimeSource.Monotonic
            val startMark = timeSource.markNow()

            val result = withContext(Dispatchers.Default) {
                runCatching {
                    true
                }
            }

            val elapsedMs = startMark.elapsedNow().inWholeMilliseconds
            val minDisplayTimeMs = 1800L
            if (elapsedMs < minDisplayTimeMs) {
                delay(minDisplayTimeMs - elapsedMs)
            }

            result.fold(
                onSuccess = { navigationToIntro ->
                    _effectChannel.send(SplashUiEffect.NavigateNext(navigationToIntro))
                },
                onFailure = { error ->
                    _uiState.value = SplashUiState.Error(
                        errorMessage = error.message ?: "Không thể khởi tạo dữ liệu âm thanh"
                    )
                }
            )
        }
    }
}
