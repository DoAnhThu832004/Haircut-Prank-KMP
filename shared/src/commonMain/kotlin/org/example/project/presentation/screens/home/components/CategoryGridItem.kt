package org.example.project.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import haircutprank.shared.generated.resources.Res
import haircutprank.shared.generated.resources.tag_new
import org.example.project.domain.model.SoundCategory
import org.example.project.presentation.screens.home.CategoryResourceMapper
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoryGridItem(
    category: SoundCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val key = category.normalizedKey

    val localBgRes = remember(key) { CategoryResourceMapper.getLocalBackground(key) }
    val localIconRes = remember(key) { CategoryResourceMapper.getLocalIcon(key) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(170f / 191f)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, color = Color.White.copy(alpha = 0.3f)),
                onClick = onClick
            )
    ) {
        if (localBgRes != null) {
            Image(
                painter = painterResource(localBgRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF8A38F5), Color(0xFFC048FB))
                        )
                    )
            )
        }

        if (localIconRes != null) {
            Image(
                painter = painterResource(localIconRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center)
                    .offset(y = (-10).dp)
            )
        }

        if (category.isNew) {
            Image(
                painter = painterResource(Res.drawable.tag_new),
                contentDescription = "NEW Category",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 2.dp, end = 2.dp)
                    .width(42.dp)
            )
        }

        Text(
            text = category.name,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 8.dp, vertical = 12.dp)
        )
    }
}
