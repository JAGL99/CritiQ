package com.jagl.critiq.feature.detail

sealed class MediaDetailUiEvents {
    data object OnFavoriteClick : MediaDetailUiEvents()
}