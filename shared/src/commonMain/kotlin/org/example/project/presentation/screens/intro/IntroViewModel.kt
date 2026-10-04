package org.example.project.presentation.screens.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.project.data.repository.UserPreferencesRepositoryImpl
import org.example.project.domain.usecase.CompleteIntroUseCase

class IntroViewModel(
    private val completeIntroUseCase: CompleteIntroUseCase = CompleteIntroUseCase(UserPreferencesRepositoryImpl())
) : ViewModel() {
    private val _effectChannel = Channel<IntroUiEffect>(Channel.BUFFERED)
    val effectFlow = _effectChannel.receiveAsFlow()

    fun onFinishIntro() {
        viewModelScope.launch {
            completeIntroUseCase()
            _effectChannel.send(IntroUiEffect.NavigateToMain)
        }
    }
}
