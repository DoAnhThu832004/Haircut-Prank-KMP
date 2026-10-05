package org.example.project.presentation.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.domain.model.Sound
import org.example.project.presentation.screens.favorite.components.FavoriteEmptyView
import org.example.project.presentation.screens.favorite.components.FavoriteItemCard
import org.example.project.presentation.screens.favorite.components.FavoriteTopBar
import org.example.project.presentation.theme.ScreenGradientEnd
import org.example.project.presentation.theme.ScreenGradientStart

@Composable
fun FavoriteScreen(
    onSoundClick: (Sound) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    viewModel: FavoriteViewModel = viewModel { FavoriteViewModel() },
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteContent(
        uiState = uiState,
        onToggleFavorite = viewModel::onToggleFavorite,
        onSoundClick = onSoundClick,
        onSettingsClick = onSettingsClick,
        modifier = modifier
    )
}

@Composable
fun FavoriteContent(
    uiState: FavoriteUiState,
    onToggleFavorite: (Sound) -> Unit,
    onSoundClick: (Sound) -> Unit,
    onSettingsClick: () -> Unit,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 1200.dp)
                .align(Alignment.TopCenter)
        ) {
            FavoriteTopBar(
                onSettingClick = onSettingsClick
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                when (uiState) {
                    is FavoriteUiState.Loading -> {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }
                    is FavoriteUiState.Empty -> {
                        FavoriteEmptyView()
                    }
                    is FavoriteUiState.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 340.dp),
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            items(
                                items = uiState.sounds,
                                key = { it.pathSound }
                            ) { sound ->
                                FavoriteItemCard(
                                    sound = sound,
                                    onClick = { onSoundClick(sound) },
                                    onToggleFavorite = { onToggleFavorite(sound) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
