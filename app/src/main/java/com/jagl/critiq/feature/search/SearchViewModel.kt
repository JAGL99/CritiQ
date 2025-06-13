package com.jagl.critiq.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.critiq.core.model.UiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private var job: Job? = null

    private val results = (1..100).map { "Result $it" }

    private val _uiState = MutableStateFlow(SearcherUiState())
    val uiState
        get() = _uiState
            .onStart { init() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = SearcherUiState()
            )

    private val _uiEvents = MutableSharedFlow<SearcherUiEvents>()
    val uiEvents get() = _uiEvents.asSharedFlow()

    fun onEvent(event: SearcherUiEvents) = viewModelScope.launch {
        when (event) {
            SearcherUiEvents.ClearSearch -> onClearSearch()
            is SearcherUiEvents.MediaSelected -> onMediaSelected(event.mediaId)
            SearcherUiEvents.Search -> onSearch()
            is SearcherUiEvents.SearchQueryChanged -> onSearchQueryChanged(event.query)
        }
    }

    private fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query, results = emptyList()) }
        job?.cancel()
        job = viewModelScope.launch {
            delay(2000)
            _uiEvents.emit(SearcherUiEvents.Search)
        }
    }

    private fun onSearch() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        val result = getFilteredResults()
        delay(2000L)
        val uiMessage = if (result.isEmpty()) {
            UiMessage.Text("No results found for '${_uiState.value.searchQuery}'")
        } else {
            UiMessage.Text("Found ${result.size} results")
        }
        _uiState.update { it.copy(isLoading = false, results = result, searchMessage = uiMessage) }
    }

    private fun getFilteredResults(): List<String> {
        val query = _uiState.value.searchQuery.lowercase()
        return results.toSet().filter { it.lowercase().contains(query) }
            .take(25) // Limit to 10 results

    }

    private suspend fun onMediaSelected(mediaId: Long) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        println("Media selected with ID: $mediaId")
        delay(1000) // Simulate network delay
        _uiState.update { it.copy(isLoading = false) }
        _uiEvents.emit(SearcherUiEvents.MediaSelected(mediaId))
    }

    private fun onClearSearch() {
        _uiState.update { it.copy(searchQuery = "", results = emptyList(), isLoading = false) }
        job?.cancel()
        job = null
    }

    private fun init() = viewModelScope.launch {
        delay(1000) // Simulate initial loading delay
        _uiState.update { it.copy(isLoading = false) }
    }

}
