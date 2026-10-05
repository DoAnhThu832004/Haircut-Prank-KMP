package org.example.project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.example.project.presentation.screens.detail.DetailSoundScreen
import org.example.project.presentation.screens.intro.IntroScreen
import org.example.project.presentation.screens.listsound.ListSoundScreen
import org.example.project.presentation.screens.main.MainScreen
import org.example.project.presentation.screens.setting.SettingScreen
import org.example.project.presentation.screens.splash.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash
    ) {
        composable<Screen.Splash> {
            SplashScreen(
                onNavigateNext = { navigationToIntro ->
                    val destination = if (navigationToIntro) Screen.Intro else Screen.Main
                    navController.navigate(destination) {
                        popUpTo(Screen.Splash) { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.Intro> {
            IntroScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Main) {
                        popUpTo(Screen.Intro) { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.Main> {
            MainScreen(
                onNavigateToListSound = { categoryName ->
                    navController.navigate(Screen.ListSound(categoryName))
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Setting)
                },
                onNavigateToDetailSound = { sound ->
                    navController.navigate(
                        Screen.DetailSound(
                            categoryName = sound.idCategory,
                            soundPath = sound.pathSound
                        )
                    )
                }
            )
        }
        composable<Screen.Setting> {
            SettingScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable<Screen.ListSound> { backStackEntry ->
            val listSound = backStackEntry.toRoute<Screen.ListSound>()
            ListSoundScreen(
                categoryName = listSound.categoryName,
                onBackClick = {
                    navController.popBackStack()
                },
                onSoundClick = { sound ->
                    navController.navigate(
                        Screen.DetailSound(
                            categoryName = sound.idCategory,
                            soundPath = sound.pathSound
                        )
                    )
                }
            )
        }
        composable<Screen.DetailSound> { backStackEntry ->
            val detail = backStackEntry.toRoute<Screen.DetailSound>()
            DetailSoundScreen(
                categoryName = detail.categoryName,
                soundPath = detail.soundPath,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}