package org.example.project.presentation.screens.listsound

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.Sound
import org.example.project.domain.usecase.GetSoundsByCategoryUseCase
import org.example.project.domain.usecase.MarkSoundsViewedUseCase

class ListSoundViewModel(
    val categoryName: String = "",
    private val getSoundsByCategoryUseCase: GetSoundsByCategoryUseCase = GetSoundsByCategoryUseCase(),
    private val markSoundsViewedUseCase: MarkSoundsViewedUseCase = MarkSoundsViewedUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListSoundState>(ListSoundState.Loading)
    val uiState: StateFlow<ListSoundState> = _uiState.asStateFlow()

    private val _effectChannel = Channel<ListSoundEffect>(Channel.BUFFERED)
    val effectFlow = _effectChannel.receiveAsFlow()

    private var hasMarkedKnown = false

    init {
        if (categoryName.isNotEmpty()) {
            loadSounds(categoryName)
        }
    }

    fun loadSounds(targetCategory: String = categoryName) {
        viewModelScope.launch {
            _uiState.value = ListSoundState.Loading
            getSoundsByCategoryUseCase(targetCategory)
                .catch { e ->
                    _uiState.value = ListSoundState.Error(e.message ?: "Failed to load sounds")
                }
                .collect { sounds ->
                    _uiState.value = ListSoundState.Success(
                        categoryName = targetCategory,
                        sounds = sounds
                    )
                    if (!hasMarkedKnown && sounds.isNotEmpty()) {
                        hasMarkedKnown = true
                        val keys = sounds.map { it.stableKey }
                        markSoundsViewedUseCase(keys)
                    }
                }
        }
    }

    fun onSoundClick(sound: Sound) {
        viewModelScope.launch {
            _effectChannel.send(ListSoundEffect.NavigateToDetail(sound))
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _effectChannel.send(ListSoundEffect.NavigateBack)
        }
    }
}
