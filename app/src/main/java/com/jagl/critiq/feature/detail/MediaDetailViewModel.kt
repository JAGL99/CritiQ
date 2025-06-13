package com.jagl.critiq.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.critiq.R
import com.jagl.critiq.core.common.dispatcherProvider.DispatcherProvider
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import com.jagl.critiq.core.model.UiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localMediaDataSource: LocalMediaDataSource,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val mediaId: Long = savedStateHandle.get<Long>(MEDIA_ID) ?: 0L

    private val _uiState = MutableStateFlow(MediaDetailUiState())
    val uiState
        get() = _uiState
            .onStart { init() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = MediaDetailUiState()
            )

    fun onEvent(event: MediaDetailUiEvents) = viewModelScope.launch {
        when (event) {
            MediaDetailUiEvents.OnFavoriteClick -> {
                val currentState = _uiState.value
                val media = currentState.media ?: return@launch
                println("Media favorite status before update: ${media.isFavorite}")
                val updatedMedia = media.copy(isFavorite = !media.isFavorite)
                println("Updating media favorite status: ${updatedMedia.isFavorite}")
                localMediaDataSource.upsertAll(listOf(updatedMedia))
                _uiState.update { currentState.copy(media = updatedMedia) }
            }
        }
    }


    private fun init() = viewModelScope.launch(dispatcherProvider.default) {
        println("MediaDetailViewModel initialized with mediaId: $mediaId")
        val media = localMediaDataSource.getById(mediaId)
        if (media != null) {
            _uiState.value = MediaDetailUiState(
                isLoading = false,
                media = media
            )
        } else {
            _uiState.value = MediaDetailUiState(
                isLoading = false,
                errorMessage = UiMessage.Resource(R.string.generic_error)
            )
        }

    }

    companion object {
        const val MEDIA_ID = "media_id"
    }

}
