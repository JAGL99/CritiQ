package com.jagl.critiq.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jagl.critiq.core.utils.getMessage
import com.jagl.critiq.feature.home.HomeViewModel.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeContent(modifier, uiState = uiState)
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier.fillMaxSize(),
    uiState: UiState
) {

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.then(modifier)
    ) {

        when (uiState) {
            is UiState.Error -> {
                Text(
                    text = uiState.message.getMessage(context),
                    modifier = Modifier.padding(16.dp)
                )
            }

            UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                ) {
                    items(uiState.data) { item ->
                        Card(modifier = Modifier.padding(8.dp)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    modifier = Modifier.fillMaxWidth(),
                                    model = item.imageUrl,
                                    contentDescription = item.title,
                                )
                                Spacer(modifier = Modifier.padding(4.dp))
                                Text(text = item.title)
                                Spacer(modifier = Modifier.padding(4.dp))
                                RatingBar(
                                    modifier = Modifier.padding(4.dp),
                                    rating = item.rating
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double
) {
    Row {
        (1..5).forEach { index ->
            val star = if (index <= rating / 2) {
                Icons.Filled.Favorite
            } else {
                Icons.Outlined.FavoriteBorder
            }
            Icon(
                imageVector = star,
                contentDescription = null,
                tint = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                modifier = modifier.padding(2.dp)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val uiState = UiState.Success(emptyList())
    HomeContent(uiState = uiState)
}
