package com.jagl.critiq.feature.detail

import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.model.UiMessage

data class MediaDetailUiState(
    val isLoading: Boolean = true,
    val errorMessage: UiMessage? = null,
    val media: Media? = null
)
