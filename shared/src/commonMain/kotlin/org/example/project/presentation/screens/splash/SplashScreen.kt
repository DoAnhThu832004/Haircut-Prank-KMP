package org.example.project.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import haircutprank.shared.generated.resources.Res
import haircutprank.shared.generated.resources.retry
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.flow.collectLatest
import org.example.project.presentation.theme.ScreenGradientEnd
import org.example.project.presentation.theme.ScreenGradientStart
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel { SplashViewModel() },
    onNavigateNext: (navigationToIntro: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentOnNavigateNext by rememberUpdatedState(onNavigateNext)

    LaunchedEffect(viewModel.effectFlow) {
        viewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is SplashUiEffect.NavigateNext -> {
                    currentOnNavigateNext(effect.navigationToIntro)
                }
            }
        }
    }

    SplashContent(
        uiState = uiState,
        onRetry = { viewModel.startPreparation() },
        modifier = modifier
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashContent(
    uiState: SplashUiState,
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val mainComposition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/haircut_splash.json").decodeToString()
        )
    }

    val loadingComposition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/loading_haircut.json").decodeToString()
        )
    }

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
            .safeDrawingPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberLottiePainter(
                    composition = mainComposition,
                    iterations = Compottie.IterateForever
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (uiState) {
                is SplashUiState.Error -> {
                    Text(
                        text = uiState.errorMessage,
                        color = Color(0xFFFF6B6B),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = onRetry
                    ) {
                        Text(
                            text = stringResource(Res.string.retry)
                        )
                    }
                }
                is SplashUiState.Loading -> {
                    Image(
                        painter = rememberLottiePainter(
                            composition = loadingComposition,
                            iterations = Compottie.IterateForever
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(180.dp)
                            .height(80.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SplashScreenLoadingPreview() {
    SplashContent(
        uiState = SplashUiState.Loading,
        onRetry = {}
    )
}

@Preview
@Composable
private fun SplashScreenErrorPreview() {
    SplashContent(
        uiState = SplashUiState.Error("Không thể khởi tạo dữ liệu âm thanh"),
        onRetry = {}
    )
}
