package org.example.project.presentation.screens.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import haircutprank.shared.generated.resources.Res
import haircutprank.shared.generated.resources.ic_hair
import org.example.project.domain.model.Sound
import org.example.project.presentation.screens.home.CategoryResourceMapper
import org.jetbrains.compose.resources.painterResource

@Composable
fun OtherSoundsRow(
    sounds: List<Sound>,
    currentSoundPath: String,
    onSoundSelected: (Sound) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Others sound",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = Color.White,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = sounds,
                key = { it.pathSound }
            ) { sound ->
                val isSelected = sound.pathSound == currentSoundPath
                val categoryKey = sound.idCategory.lowercase().replace(" ", "_")
                val localIconRes = remember(categoryKey) {
                    CategoryResourceMapper.getLocalIcon(categoryKey) ?: Res.drawable.ic_hair
                }

                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = if (isSelected) 0.35f else 0.15f))
                        .then(
                            if (isSelected) {
                                Modifier.border(2.dp, Color(0xFFC048FB), RoundedCornerShape(14.dp))
                            } else {
                                Modifier
                            }
                        )
                        .clickable { onSoundSelected(sound) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(localIconRes),
                        contentDescription = sound.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(46.dp)
                    )
                }
            }
        }
    }
}
