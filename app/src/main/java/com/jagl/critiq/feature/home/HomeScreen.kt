package com.jagl.critiq.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.model.UiMessage
import com.jagl.critiq.core.test.media
import com.jagl.critiq.core.ui.composables.Loading
import com.jagl.critiq.core.ui.composables.NotAvable
import com.jagl.critiq.core.ui.extensions.fullScreen
import com.jagl.critiq.feature.home.composables.MovieItem
import kotlinx.coroutines.flow.flow

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (Long) -> Unit
) {
    val items = viewModel.items.collectAsLazyPagingItems()
    HomeContent(modifier, items, onNavigateToDetail)
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier.fullScreen(),
    items: LazyPagingItems<Media>,
    onClick: (Long) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
        ) {

            items(items.itemCount) { index ->
                /*
                .fullScreen(padding = 4.dp)
                            .clickable { onClick(item.id) },
                 */
                items[index]?.let { item -> MovieItem(media = item) }
            }

            items.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item {
                            Loading(modifier = modifier)
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item {
                            NotAvable(
                                modifier = modifier,
                                text = UiMessage.Text("Error loading more items")
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
    val medias: List<Media> = listOf(
        media(), media(),
        media(), media()
    )
    val items = flow { emit(PagingData.from(medias)) }.collectAsLazyPagingItems()
    HomeContent(
        items = items,
        onClick = {}
    )
}

/**
 * Test ideas
 *
 * - Test that the HomeScreen show the 500 pages of the endpoint
 */