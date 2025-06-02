package com.jagl.critiq.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jagl.critiq.core.ui.extensions.loader

@Composable
fun Loading(
    modifier: Modifier = Modifier.loader()
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(75.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 10.dp,
            trackColor = Color.Transparent,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    Loading(
        modifier = Modifier.loader()
    )
}