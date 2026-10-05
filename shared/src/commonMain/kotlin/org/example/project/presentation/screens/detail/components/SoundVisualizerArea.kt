package org.example.project.presentation.screens.detail.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import haircutprank.shared.generated.resources.Res
import haircutprank.shared.generated.resources.ic_hair
import haircutprank.shared.generated.resources.ic_pause
import haircutprank.shared.generated.resources.ic_play
import haircutprank.shared.generated.resources.ic_ring
import haircutprank.shared.generated.resources.ic_unring
import haircutprank.shared.generated.resources.time_out
import org.example.project.domain.model.Sound
import org.example.project.presentation.screens.home.CategoryResourceMapper
import org.example.project.presentation.theme.ColorWave
import org.jetbrains.compose.resources.painterResource

@Composable
fun SoundVisualizerArea(
    sound: Sound?,
    isPlaying: Boolean,
    isCountingDown: Boolean,
    countdownRemaining: Int,
    isVibrationEnabled: Boolean,
    onTogglePlay: () -> Unit,
    onToggleVibrate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryKey = sound?.idCategory?.lowercase()?.replace(" ", "_").orEmpty()
    val localIconRes = remember(categoryKey) {
        CategoryResourceMapper.getLocalIcon(categoryKey) ?: Res.drawable.ic_hair
    }

    // Ripple wave animation when sound is playing
    val infiniteTransition = rememberInfiniteTransition(label = "WaveAnimation")
    val waveScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "waveScale"
    )
    val waveAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "waveAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Countdown indicator (Top Start)
        if (isCountingDown) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.time_out),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                val minutes = countdownRemaining / 60
                val seconds = countdownRemaining % 60
                val formatted = "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
                Text(
                    text = formatted,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    ),
                    color = Color(0xFF1B0A3A)
                )
            }
        }

        // Vibrate button (Top End)
        IconButton(
            onClick = onToggleVibrate,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(48.dp)
        ) {
            Icon(
                painter = painterResource(if (isVibrationEnabled) Res.drawable.ic_ring else Res.drawable.ic_unring),
                contentDescription = "Vibrate",
                tint = Color.Unspecified
            )
        }

        // Center Content (Icon + Play Button)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onTogglePlay
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isPlaying) {
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .graphicsLayer {
                                scaleX = waveScale
                                scaleY = waveScale
                                alpha = waveAlpha
                            }
                            .clip(CircleShape)
                            .background(ColorWave)
                    )
                }

                Image(
                    painter = painterResource(localIconRes),
                    contentDescription = sound?.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(190.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val buttonColor = if (isCountingDown) Color(0xFFE53935) else Color(0xFF8A38F5)
            val iconRes = when {
                isCountingDown -> Res.drawable.ic_pause
                isPlaying -> Res.drawable.ic_pause
                else -> Res.drawable.ic_play
            }
            val buttonText = when {
                isCountingDown -> "CANCEL"
                isPlaying -> "PAUSE"
                else -> "PLAY"
            }

            Row(
                modifier = Modifier
                    .width(160.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(buttonColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = true, color = Color.White),
                        onClick = onTogglePlay
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = buttonText,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    ),
                    color = Color.White
                )
            }
        }
    }
}
