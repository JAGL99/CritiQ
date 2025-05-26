package com.jagl.critiq.core.test.fake

import com.jagl.critiq.core.repository.repository.MediaRepository
import com.jagl.critiq.core.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MediaRepositoryFake : MediaRepository {
    override var nextPage: Int = 0

    override fun getMediaList(): Flow<List<Media>> {
        return flow { emit(emptyList()) }
    }

    override fun loadNextPage(nextPage: Int): Flow<List<Media>> {
        return flow { emit(emptyList()) }
    }

}
