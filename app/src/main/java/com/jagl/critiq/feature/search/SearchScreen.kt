package com.jagl.critiq.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.critiq.core.model.UiMessage
import com.jagl.critiq.core.model.getMessage
import com.jagl.critiq.core.test.media
import com.jagl.critiq.core.ui.composables.Loading
import com.jagl.critiq.core.ui.composables.NotAvable
import com.jagl.critiq.core.ui.extensions.fullScreen

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onMediaSelected: (Long) -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                SearcherUiEvents.Search -> viewModel.onEvent(SearcherUiEvents.Search)
                is SearcherUiEvents.MediaSelected -> onMediaSelected(event.mediaId)

                else -> Unit
            }
        }
    }
    SearchContent(
        modifier = modifier.fullScreen(),
        uiState = uiState.value,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    uiState: SearcherUiState,
    onEvent: (SearcherUiEvents) -> Unit
) {
    Box {

        if (uiState.isLoading) {
            Loading(modifier)
            return
        }

        if (uiState.errorMessage != null) {
            NotAvable(
                modifier = modifier,
                text = uiState.errorMessage
            )
            return
        }


        Column(
            modifier = modifier
        ) {
            TextField(
                value = uiState.searchQuery,
                onValueChange = { query ->
                    when (query.isBlank()) {
                        true -> onEvent(SearcherUiEvents.ClearSearch)
                        else -> onEvent(SearcherUiEvents.SearchQueryChanged(query))
                    }
                },
                placeholder = { Text("Search for media...") },
            )
            Spacer(modifier = Modifier.height(8.dp))
            SearchResultsMessage(
                modifier = Modifier,
                message = uiState.searchMessage,
            )
            Spacer(modifier = Modifier.height(8.dp))
            uiState.results.forEach { result ->
                if (result != uiState.results.first()) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = result.title,
                    modifier = Modifier.clickable {
                        onEvent(SearcherUiEvents.MediaSelected(result.id))
                    }
                )

            }
        }
    }
}

@Composable
fun SearchResultsMessage(
    modifier: Modifier,
    message: UiMessage,
) {
    val context = LocalContext.current
    Text(
        text = message.getMessage(context),
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    val results = (1..5).map { media().copy(title = "Title $it") }
    val uiState = SearcherUiState().copy(
        searchQuery = "title",
        results = results,
        isLoading = false
    )
    SearchContent(
        modifier = Modifier.fullScreen(),
        uiState = uiState,
        onEvent = {}
    )
}
