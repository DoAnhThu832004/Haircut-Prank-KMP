package org.example.project.presentation.screens.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import haircutprank.shared.generated.resources.Res
import haircutprank.shared.generated.resources.ic_arrow_left
import org.jetbrains.compose.resources.painterResource

@Composable
fun SoundControlsSection(
    isLooping: Boolean,
    selectedTimerSeconds: Int,
    onToggleLoop: (Boolean) -> Unit,
    onOpenTimerDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 1. Loop Switch
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Loop",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Switch(
                checked = isLooping,
                onCheckedChange = onToggleLoop,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF8A38F5),
                    uncheckedThumbColor = Color.White.copy(alpha = 0.7f),
                    uncheckedTrackColor = Color.White.copy(alpha = 0.2f)
                )
            )
        }

        // 2. Timer Control (Play after)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Play after",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                ),
                color = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))

            val timerLabel = when (selectedTimerSeconds) {
                5 -> "5s"
                10 -> "10s"
                30 -> "30s"
                60 -> "1m"
                300 -> "5m"
                else -> "Off"
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .clickable { onOpenTimerDialog() }
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = timerLabel,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(14.dp)
                        .rotate(180f)
                )
            }
        }
    }
}
