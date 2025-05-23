package com.jagl.critiq.core.repository.repository

import com.jagl.critiq.core.database.source.MediaDataSource
import com.jagl.critiq.core.remote.source.AllMediaSource
import com.jagl.critiq.domain.data.MediaDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaDataSource: MediaDataSource,
    private val allMediaSource: AllMediaSource,
) : MediaRepository {

    override fun getMediaList(): Flow<List<MediaDomain>> = flow {
        val localMedia = mediaDataSource.getAll()
        emitAll(localMedia)
        allMediaSource.getTrendings(1)
            .collect { mediaList ->
                if (mediaList.isNotEmpty()) {
                    mediaDataSource.insertAll(mediaList)
                }

            }
    }

    override fun loadNextPage(nextPage: Int): Flow<List<MediaDomain>> = flow {
        allMediaSource.getTrendings(nextPage).collect {
            if (it.isNotEmpty()) {
                mediaDataSource.insertAll(it)
                emit(it)
            } else {
                emit(emptyList())
                emitAll(mediaDataSource.getAll())
            }

        }
    }
}