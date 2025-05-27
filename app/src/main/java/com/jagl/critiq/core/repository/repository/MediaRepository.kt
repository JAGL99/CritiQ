package com.jagl.critiq.core.repository.repository

import androidx.paging.PagingData
import com.jagl.critiq.core.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    fun getPagingMedia(language: String?): Flow<PagingData<Media>>
}