package org.example.project.presentation.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import haircutprank.shared.generated.resources.Res
import haircutprank.shared.generated.resources.favorite
import haircutprank.shared.generated.resources.home
import haircutprank.shared.generated.resources.ic_favorites
import haircutprank.shared.generated.resources.ic_home
import org.example.project.domain.model.Sound
import org.example.project.presentation.screens.favorite.FavoriteScreen
import org.example.project.presentation.screens.home.HomeScreen
import org.example.project.presentation.theme.ScreenGradientEnd
import org.example.project.presentation.theme.ScreenGradientStart
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

enum class MainTab { HOME, FAVORITE }

@Composable
fun MainScreen(
    onNavigateToListSound: (categoryName: String) -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToDetailSound: (Sound) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(MainTab.HOME) }
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
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                CustomBottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedTab) {
                    MainTab.HOME -> {
                        HomeScreen(
                            onCategoryClick = onNavigateToListSound,
                            onSettingsClick = onNavigateToSettings
                        )
                    }
                    MainTab.FAVORITE -> {
                        FavoriteScreen(
                            onSoundClick = onNavigateToDetailSound,
                            onSettingsClick = onNavigateToSettings
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomBottomNavBar(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
        )
        Row(
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BottomNavItem(
                modifier = Modifier.weight(1f),
                title = stringResource(Res.string.home),
                iconRes = Res.drawable.ic_home,
                isSelected = selectedTab == MainTab.HOME,
                onClick = { onTabSelected(MainTab.HOME) }
            )
            BottomNavItem(
                modifier = Modifier.weight(1f),
                title = stringResource(Res.string.favorite),
                iconRes = Res.drawable.ic_favorites,
                isSelected = selectedTab == MainTab.FAVORITE,
                onClick = { onTabSelected(MainTab.FAVORITE) }
            )
        }
    }
}

@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    title: String,
    iconRes: DrawableResource,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "bgAlpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )
    val iconTint by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
        animationSpec = tween(250),
        label = "iconTint"
    )
    Row(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .height(50.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.1f))
            .drawBehind {
                if (bgAlpha > 0f) {
                    drawRoundRect(
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFF8A38F5), Color(0xFFC048FB))
                        ),
                        alpha = bgAlpha,
                        cornerRadius = CornerRadius(24.dp.toPx(), 24.dp.toPx())
                    )
                }
            }
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(tween(200)) + expandHorizontally(
                animationSpec = tween(250),
                expandFrom = Alignment.Start
            ),
            exit = fadeOut(tween(150)) + shrinkHorizontally(
                animationSpec = tween(200),
                shrinkTowards = Alignment.Start
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                    color = Color.White
                )
            }
        }
    }
}
