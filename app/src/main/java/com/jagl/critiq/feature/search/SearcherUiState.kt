package com.jagl.critiq.feature.search

import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.model.UiMessage

data class SearcherUiState(
    val isLoading: Boolean = true,
    val errorMessage: UiMessage? = null,
    val results: List<Media> = emptyList(),
    val searchQuery: String = "",
    val searchMessage: UiMessage = UiMessage.Text("Start typing to search...")
)
