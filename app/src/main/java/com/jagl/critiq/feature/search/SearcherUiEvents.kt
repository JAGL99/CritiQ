package com.jagl.critiq.feature.search

sealed class SearcherUiEvents {
    data class SearchQueryChanged(val query: String) : SearcherUiEvents()
    data object Search : SearcherUiEvents()
    data object ClearSearch : SearcherUiEvents()
    data class MediaSelected(val mediaId: Long) : SearcherUiEvents()
}
