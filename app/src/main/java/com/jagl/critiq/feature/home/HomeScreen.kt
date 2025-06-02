package com.jagl.critiq.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.model.UiMessage
import com.jagl.critiq.core.test.media
import com.jagl.critiq.core.ui.components.Loading
import com.jagl.critiq.core.ui.components.NotAvable
import com.jagl.critiq.core.ui.components.RatingBar
import com.jagl.critiq.core.ui.extensions.fullScreen
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
            columns = GridCells.Fixed(2),
        ) {

            items(items.itemCount) { index ->
                val item = items[index]!!
                Card(modifier = Modifier.padding(8.dp)) {
                    Column(
                        modifier = Modifier
                            .fullScreen(padding = 4.dp)
                            .clickable { onClick(item.id) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxWidth(),
                            model = item.backdropPath,
                            contentDescription = item.title,
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = item.title,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        RatingBar(rating = item.rating)

                    }
                }
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
