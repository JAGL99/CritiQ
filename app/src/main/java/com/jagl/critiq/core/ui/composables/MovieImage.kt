package com.jagl.critiq.core.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun MovieImage(
    data: String,
    contentDescription: String? = null
) {
    println("Loading image with data: $data")
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .size(Size.ORIGINAL)
            .build()
    )
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier.fillMaxSize()
    )
}

