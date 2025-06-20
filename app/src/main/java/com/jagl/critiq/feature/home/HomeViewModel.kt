package com.jagl.critiq.feature.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jagl.critiq.core.repository.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    mediaRepository: MediaRepository
) : ViewModel() {

    //TODO: Move to a separate file

    sealed class UiEvent {
        data class OnMediaClick(val id: Long) : UiEvent()
    }

    private var _uiEvents: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvents: SharedFlow<UiEvent>
        get() = _uiEvents.asSharedFlow()

    private val language: MutableLiveData<String?> = MutableLiveData(null)

    val items = mediaRepository.getPagingMedia(language.value).cachedIn(viewModelScope)


    fun onEvent(event: UiEvent) = viewModelScope.launch {
        when (event) {
            is UiEvent.OnMediaClick -> _uiEvents.emit(event)
        }
    }
}