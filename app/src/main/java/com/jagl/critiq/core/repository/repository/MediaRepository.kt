package com.jagl.critiq.core.repository.repository

import com.jagl.critiq.domain.data.MediaDomain
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getMediaList(): Flow<List<MediaDomain>>
    fun loadNextPage(nextPage: Int): Flow<List<MediaDomain>>
}