package com.jagl.critiq.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.critiq.R
import com.jagl.critiq.core.database.source.MediaDataSourceImpl
import com.jagl.critiq.core.remote.source.AllMediaSourceImpl
import com.jagl.critiq.core.utils.UiMessage
import com.jagl.critiq.domain.data.MediaDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val allMediaSource: AllMediaSourceImpl,
    private val mediaDataSource: MediaDataSourceImpl,
) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: List<Item>) : UiState()
        data class Error(val message: UiMessage) : UiState()
    }


    init {
        init()
    }

    private fun init() {
        viewModelScope.launch {
            collectLocalMediaData { localData ->
                if (localData.isNotEmpty()) {
                    updateToSuccess(localData)
                    return@collectLocalMediaData
                }
                collectRemoteMediaData(1)
            }
        }
    }

    private fun collectRemoteMediaData(page: Int) = viewModelScope.launch {
        allMediaSource.getTrendings(page)
            .map {
                it.sortedBy { it.id }
            }.collect { data ->
                if (data.isEmpty()) {
                    _uiState.update {
                        UiState.Error(
                            UiMessage.Resource(R.string.featureHomeErrorFetchingData)
                        )
                    }
                    return@collect
                }

                mediaDataSource.insertAll(data)
                updateToSuccess(data)
            }
    }

    private fun collectLocalMediaData(
        onCollect: (List<MediaDomain>) -> Unit
    ) = viewModelScope.launch {
        mediaDataSource
            .getAll()
            .collect { onCollect(it) }
    }

    private fun mapToItem(media: MediaDomain): Item {
        return Item(
            id = media.id,
            title = media.title,
            imageUrl = media.backdropPath,
            rating = media.rating
        )
    }

    private fun updateToSuccess(data: List<MediaDomain>) {
        val items = data.map { mapToItem(it) }
        _uiState.update { UiState.Success(items) }
    }
}