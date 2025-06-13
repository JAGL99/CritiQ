package com.jagl.critiq.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.feature.home.composables.MovieItem
import kotlinx.coroutines.flow.flow

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val items = viewModel.items.collectAsLazyPagingItems()
    HomeContent(modifier, items)
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier.fillMaxSize(),
    items: LazyPagingItems<Media>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.then(modifier)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
        ) {

            items(items.itemCount) { index ->
                items[index]?.let { item -> MovieItem(media = item) }
            }

            items.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item {
                            Text(
                                text = "Error loading more items",
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeContent(
        items = flow {
            emit(
                PagingData.from(
                    listOf(
                        Media(
                            1,
                            "Movie 1",
                            "https://example.com/poster1.jpg",
                            "https://example.com/backdrop1.jpg",
                            "movie",
                            8.5,
                            "2023-01-01",
                            "Description 1"
                        ),
                        Media(
                            2,
                            "Movie 2",
                            "https://example.com/poster2.jpg",
                            "https://example.com/backdrop2.jpg",
                            "movie",
                            7.0,
                            "2023-02-01",
                            "Description 2"
                        ),
                        Media(
                            3,
                            "Movie 3",
                            "https://example.com/poster3.jpg",
                            "https://example.com/backdrop3.jpg",
                            "movie",
                            9.0,
                            "2023-03-01",
                            "Description 3"
                        )
                    )
                )
            )
        }.collectAsLazyPagingItems(),
    )
}

/**
 * Test ideas
 *
 * - Test that the HomeScreen show the 500 pages of the endpoint
 */