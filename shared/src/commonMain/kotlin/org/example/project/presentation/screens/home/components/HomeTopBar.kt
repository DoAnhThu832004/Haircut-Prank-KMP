package org.example.project.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import haircutprank.shared.generated.resources.Res
import haircutprank.shared.generated.resources.home_banner
import haircutprank.shared.generated.resources.ic_setting
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeTopBar(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_setting),
                contentDescription = "Settings",
                tint = Color.Unspecified,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(Res.drawable.home_banner),
            contentDescription = "Haircut Prank",
            modifier = Modifier
                .height(40.dp)
                .wrapContentWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(48.dp))
    }
}
