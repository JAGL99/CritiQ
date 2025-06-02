package com.jagl.critiq.feature.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.test.media
import com.jagl.critiq.core.ui.components.RatingBar
import com.jagl.critiq.core.ui.extensions.fullScreen

@Composable
fun MediaDetailComponent(
    modifier: Modifier = Modifier,
    media: Media
) {
    var isFavorite = remember { false }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            text = "Media Title: ${media.title}"
        )
        Text(
            fontSize = 24.sp,
            text = "Description: ${media.description}"
        )
        Text(
            fontSize = 20.sp,
            text = "Release Date: ${media.releaseDate}"
        )
        RatingBar(rating = media.rating)
        IconButton(
            onClick = {
                println("Favorite button clicked")
                isFavorite = !isFavorite
                println("Is favorite: $isFavorite")
            },
            modifier = Modifier.align(Alignment.End)
        ) {
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
        media = media
    )
}