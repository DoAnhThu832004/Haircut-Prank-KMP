package org.example.project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.project.presentation.screens.intro.IntroScreen
import org.example.project.presentation.screens.main.MainScreen
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
            MainScreen()
        }
    }
}