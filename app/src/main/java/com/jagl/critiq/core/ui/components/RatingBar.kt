package com.jagl.critiq.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
                tint = MaterialTheme.colorScheme.primary,
                modifier = modifier.padding(2.dp)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingBarPreview() {
    RatingBar(
        rating = 5.0,
        modifier = Modifier.padding(16.dp)
    )
}