package com.jagl.critiq.feature.search

import com.jagl.critiq.core.model.UiMessage

data class SearcherUiState(
    val isLoading: Boolean = true,
    val errorMessage: UiMessage? = null,
    val results: List<String> = emptyList(),
    val searchQuery: String = ""
)
