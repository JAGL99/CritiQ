package com.jagl.critiq.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.critiq.R
import com.jagl.critiq.core.model.UiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
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

    private fun init() = viewModelScope.launch {
        println("MediaDetailViewModel initialized with mediaId: $mediaId")
        delay(5000L)
        _uiState.value = MediaDetailUiState(
            isLoading = false,
            errorMessage = UiMessage.Resource(R.string.generic_error)
        )
    }

    companion object {
        const val MEDIA_ID = "media_id"
    }

}
