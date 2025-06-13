package com.jagl.critiq.core.repository.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import androidx.room.RoomDatabase
import com.jagl.critiq.core.common.dispatcherProvider.DispatcherProvider
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.remote.source.RemotePaginateMediaDataSource
import com.jagl.critiq.core.repository.remoteMediator.MediaRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val database: RoomDatabase,
    private val localMediaDataSource: LocalMediaDataSource,
    private val remotePaginateMediaDataSource: RemotePaginateMediaDataSource,
    private val dispatcherProvider: DispatcherProvider
) : MediaRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingMedia(language: String?): Flow<PagingData<Media>> {

        val pagingSourceFactory: () -> PagingSource<Int, MediaEntity> =
            { localMediaDataSource.getAll() }

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = MediaRemoteMediator(
                database = database,
                localMediaDataSource = localMediaDataSource,
                remotePaginateMediaDataSource = remotePaginateMediaDataSource,
                language = language
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { it.map(MediaEntity::toDomain) }.flowOn(dispatcherProvider.io)
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
