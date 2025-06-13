package com.jagl.critiq.feature.home.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.test.media
import com.jagl.critiq.core.ui.composables.MovieImage

@Composable
fun MovieItem(media: Media) {
    Card(
        modifier = Modifier.padding(2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MovieImage(
                data = media.posterPath
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(media = media())
}
