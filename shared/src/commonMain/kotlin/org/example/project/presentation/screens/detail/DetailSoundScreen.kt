package org.example.project.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import org.example.project.domain.model.Sound
import org.example.project.presentation.screens.detail.components.DetailSoundTopBar
import org.example.project.presentation.screens.detail.components.OtherSoundsRow
import org.example.project.presentation.screens.detail.components.SoundControlsSection
import org.example.project.presentation.screens.detail.components.SoundVisualizerArea
import org.example.project.presentation.screens.detail.components.TimerSelectionDialog
import org.example.project.presentation.theme.ScreenGradientEnd
import org.example.project.presentation.theme.ScreenGradientStart

@Composable
fun DetailSoundScreen(
    categoryName: String,
    soundPath: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailSoundViewModel = viewModel(key = "$categoryName-$soundPath") {
        DetailSoundViewModel(categoryName = categoryName, soundPath = soundPath)
    }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effectFlow) {
        viewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is DetailSoundUiEffect.NavigateBack -> onBackClick()
            }
        }
    }

    DetailSoundContent(
        uiState = uiState,
        onBackClick = viewModel::onBackClick,
        onTogglePlay = viewModel::onTogglePlay,
        onToggleLoop = viewModel::onToggleLoop,
        onToggleVibrate = viewModel::onToggleVibration,
        onToggleFavorite = viewModel::onToggleFavorite,
        onSelectTimer = viewModel::onSelectTimer,
        onSelectSound = viewModel::onSelectSound,
        modifier = modifier
    )
}

@Composable
fun DetailSoundContent(
    uiState: DetailSoundUiState,
    onBackClick: () -> Unit,
    onTogglePlay: () -> Unit,
    onToggleLoop: (Boolean) -> Unit,
    onToggleVibrate: () -> Unit,
    onToggleFavorite: () -> Unit,
    onSelectTimer: (Int) -> Unit,
    onSelectSound: (Sound) -> Unit,
    modifier: Modifier = Modifier
) {
    var showTimerDialog by remember { mutableStateOf(false) }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            ScreenGradientStart,
            ScreenGradientEnd
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBrush)
            .statusBarsPadding(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(max = 680.dp)
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                DetailSoundTopBar(
                    title = uiState.currentSound?.name?.replace(".mp3", "").orEmpty(),
                    isFavorite = uiState.isFavorite,
                    onBackClick = onBackClick,
                    onFavoriteClick = onToggleFavorite
                )

                SoundVisualizerArea(
                    sound = uiState.currentSound,
                    isPlaying = uiState.isPlaying,
                    isCountingDown = uiState.isCountingDown,
                    countdownRemaining = uiState.countdownRemainingSeconds,
                    isVibrationEnabled = uiState.isVibrationEnabled,
                    onTogglePlay = onTogglePlay,
                    onToggleVibrate = onToggleVibrate,
                    modifier = Modifier.weight(1f)
                )

                SoundControlsSection(
                    isLooping = uiState.isLooping,
                    selectedTimerSeconds = uiState.selectedTimerSeconds,
                    onToggleLoop = onToggleLoop,
                    onOpenTimerDialog = { showTimerDialog = true },
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                OtherSoundsRow(
                    sounds = uiState.otherSounds,
                    currentSoundPath = uiState.currentSound?.pathSound.orEmpty(),
                    onSoundSelected = onSelectSound,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        // Timer selection dialog
        if (showTimerDialog) {
            TimerSelectionDialog(
                selectedSeconds = uiState.selectedTimerSeconds,
                onSelect = onSelectTimer,
                onDismiss = { showTimerDialog = false }
            )
        }
    }
}
