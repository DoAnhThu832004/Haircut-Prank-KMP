package org.example.project.presentation.screens.intro

sealed interface IntroUiEffect {
    data object NavigateToMain : IntroUiEffect
}
