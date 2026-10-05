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

    @Serializable
    data object Setting : Screen

    @Serializable
    data class ListSound(val categoryName: String) : Screen

    @Serializable
    data class DetailSound(val categoryName: String, val soundPath: String) : Screen
}