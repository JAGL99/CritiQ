package com.jagl.critiq.core.ui.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.fullScreen(padding: Dp = 16.dp): Modifier {
    return this.then(
        Modifier
            .fillMaxSize()
            .padding(padding)
    )
}

fun Modifier.loader(): Modifier {
    return this.then(
        Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .wrapContentSize(Alignment.Center)
    )
}