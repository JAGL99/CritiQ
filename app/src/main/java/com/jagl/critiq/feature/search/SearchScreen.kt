package com.jagl.critiq.feature.search

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import com.jagl.critiq.core.ui.components.Loading
import com.jagl.critiq.core.ui.components.NotAvable
import com.jagl.critiq.core.ui.extensions.fullScreen

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier.fullScreen(),
    viewModel: SearchViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                SearcherUiEvents.Search -> viewModel.onEvent(SearcherUiEvents.Search)
                is SearcherUiEvents.MediaSelected -> {
                    Toast.makeText(
                        context,
                        "Media selected: ${event.mediaId}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> Unit
            }
        }
    }
    BackHandler { onBackPressed() }
    SearchContent(
        modifier = modifier,
        uiState = uiState.value,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SearchContent(
    modifier: Modifier,
    uiState: SearcherUiState,
    onEvent: (SearcherUiEvents) -> Unit
) {
    Box {

        if (uiState.isLoading) {
            Loading(modifier)
        } else if (uiState.errorMessage != null) {
            NotAvable(
                modifier = modifier,
                text = uiState.errorMessage
            )
        } else {
            Column(
                modifier = modifier
            ) {
                Text(
                    text = "Searcher",
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = uiState.searchQuery,
                    onValueChange = { query ->
                        if (query.isBlank()) {
                            onEvent(SearcherUiEvents.ClearSearch)
                        } else {
                            onEvent(SearcherUiEvents.SearchQueryChanged(query))
                        }

                    },
                    placeholder = { Text("Search for media...") },
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (uiState.results.isEmpty()) {
                    Text(
                        text = "Without results",
                        modifier = Modifier
                    )
                } else uiState.results.forEach { result ->
                    if (result != uiState.results.first()) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Text(
                        text = result,
                        modifier = Modifier.clickable {
                            onEvent(SearcherUiEvents.MediaSelected(result.hashCode().toLong()))
                        }
                    )
                }

            }


        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    val uiState = SearcherUiState().copy(
        searchQuery = "Search Query",
        results = listOf(
            "Result 1", "Result 2", "Result 3"
        ),
        isLoading = false
    )
    SearchContent(
        modifier = Modifier.fullScreen(),
        uiState = uiState,
        onEvent = {}
    )
}
