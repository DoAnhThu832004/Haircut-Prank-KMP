package org.example.project.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Splash : Screen

    @Serializable
    data object Intro : Screen
    @Serializable
    data object Main : Screen
}