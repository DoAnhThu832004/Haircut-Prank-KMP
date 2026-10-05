package org.example.project.presentation.screens.listsound

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import org.example.project.domain.model.Sound
import org.example.project.presentation.screens.listsound.components.ListSoundTopBar
import org.example.project.presentation.screens.listsound.components.SoundGridItem
import org.example.project.presentation.theme.ScreenGradientEnd
import org.example.project.presentation.theme.ScreenGradientStart

@Composable
fun ListSoundScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onSoundClick: (Sound) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: ListSoundViewModel = viewModel(key = categoryName) { ListSoundViewModel(categoryName = categoryName) }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effectFlow) {
        viewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is ListSoundEffect.NavigateBack -> onBackClick()
                is ListSoundEffect.NavigateToDetail -> onSoundClick(effect.sound)
            }
        }
    }

    ListSoundContent(
        uiState = uiState,
        onBackClick = viewModel::onBackClick,
        onSoundClick = viewModel::onSoundClick,
        modifier = modifier
    )
}

@Composable
fun ListSoundContent(
    uiState: ListSoundState,
    onBackClick: () -> Unit,
    onSoundClick: (Sound) -> Unit,
    modifier: Modifier = Modifier
) {
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
            .statusBarsPadding()
    ) {
        when (uiState) {
            is ListSoundState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            is ListSoundState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.message,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is ListSoundState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 1200.dp)
                        .align(Alignment.TopCenter)
                ) {
                    ListSoundTopBar(
                        title = uiState.categoryName,
                        onBackClick = onBackClick
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 160.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = uiState.sounds,
                            key = { it.pathSound }
                        ) { sound ->
                            SoundGridItem(
                                sound = sound,
                                onClick = { onSoundClick(sound) }
                            )
                        }
                    }
                }
            }
        }
    }
}
