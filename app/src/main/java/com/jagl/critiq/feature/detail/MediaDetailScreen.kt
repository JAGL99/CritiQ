package com.jagl.critiq.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.critiq.core.ui.composables.Loading
import com.jagl.critiq.core.ui.composables.NotAvable
import com.jagl.critiq.core.ui.extensions.fullScreen
import com.jagl.critiq.feature.detail.components.MediaDetailComponent

@Composable
fun MediaDetailScreen(
    modifier: Modifier = Modifier.fullScreen(),
    viewModel: MediaDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    MediaDetailContent(
        modifier = modifier,
        uiState = uiState.value
    )
}

@Composable
fun MediaDetailContent(
    modifier: Modifier,
    uiState: MediaDetailUiState
) {
    Box {

        if (uiState.isLoading) {
            Loading()
        }

        uiState.media?.let { media ->
            MediaDetailComponent(
                modifier = modifier,
                media = media
            )
        } ?: NotAvable(
            modifier = modifier,
            text = uiState.errorMessage
        )


    }

}

@Preview(showBackground = true)
@Composable
fun MediaDetailScreenPreview() {
    val uiState = MediaDetailUiState().copy(isLoading = true)
    MediaDetailContent(
        modifier = Modifier.fullScreen(),
        uiState = uiState
    )
}
