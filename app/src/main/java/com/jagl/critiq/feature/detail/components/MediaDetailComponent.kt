package com.jagl.critiq.feature.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.test.media
import com.jagl.critiq.core.ui.composables.RatingBar
import com.jagl.critiq.core.ui.extensions.fullScreen
import com.jagl.critiq.feature.detail.MediaDetailUiEvents

@Composable
fun MediaDetailComponent(
    modifier: Modifier = Modifier,
    media: Media,
    onEvent: (MediaDetailUiEvents) -> Unit,
) {
    Column(
        modifier = Modifier
            .then(modifier)
            .verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = media.backdropPath,
            contentScale = ContentScale.FillWidth,
            contentDescription = media.title,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 34.sp,
            text = "Title:\n${media.title}"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            fontSize = 24.sp,
            lineHeight = 28.sp,
            text = "Description:\n${media.description}"
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            fontSize = 23.sp,
            lineHeight = 28.sp,
            text = "Release Date: ${media.releaseDate}"
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            fontSize = 20.sp,
            lineHeight = 24.sp,
            text = "Original language: ${media.language}"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                fontSize = 20.sp,
                lineHeight = 24.sp,
                text = "Rating:",
            )
            Spacer(modifier = Modifier.width(8.dp))
            RatingBar(rating = media.rating)
        }
        Spacer(modifier = Modifier.height(4.dp))


        IconButton(
            onClick = { onEvent(MediaDetailUiEvents.OnFavoriteClick) },
            modifier = Modifier.align(Alignment.End)
        ) {
            val isFavorite = media.isFavorite
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (isFavorite) "Unfavorite" else "Favorite"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaDetailComponentPreview() {
    val media = media()
    MediaDetailComponent(
        modifier = Modifier.fullScreen(),
        media = media,
        onEvent = {}
    )
}