package org.example.project.presentation.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import haircutprank.shared.generated.resources.Res
import haircutprank.shared.generated.resources.feed_back
import haircutprank.shared.generated.resources.ic_back
import haircutprank.shared.generated.resources.ic_feedback
import haircutprank.shared.generated.resources.ic_policy
import haircutprank.shared.generated.resources.ic_rate
import haircutprank.shared.generated.resources.privacy_policy
import haircutprank.shared.generated.resources.rate_app
import haircutprank.shared.generated.resources.settings
import org.example.project.presentation.screens.setting.components.SettingMenuItem
import org.example.project.presentation.theme.ScreenGradientEnd
import org.example.project.presentation.theme.ScreenGradientStart
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingScreen(
    onBackClick: () -> Unit
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            ScreenGradientStart,
            ScreenGradientEnd
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp)
                .align(Alignment.TopCenter)
        ) {
            Row(
                modifier = modifierTopBar(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = "Back",
                        tint = Color.Unspecified
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(Res.string.settings),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.size(56.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingMenuItem(
                    title = stringResource(Res.string.rate_app),
                    iconRes = Res.drawable.ic_rate,
                    onClick = {}
                )
                SettingMenuItem(
                    title = stringResource(Res.string.feed_back),
                    iconRes = Res.drawable.ic_feedback,
                    onClick = {}
                )
                SettingMenuItem(
                    title = stringResource(Res.string.privacy_policy),
                    iconRes = Res.drawable.ic_policy,
                    onClick = {}
                )
            }
        }
    }
}

private fun modifierTopBar(): Modifier = Modifier
    .fillMaxWidth()
    .height(56.dp)
    .padding(horizontal = 8.dp)
