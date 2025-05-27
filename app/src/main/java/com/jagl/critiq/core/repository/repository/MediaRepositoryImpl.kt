package com.jagl.critiq.core.repository.repository

import com.jagl.critiq.core.common.dispatcherProvider.DispatcherProvider
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import com.jagl.critiq.core.model.ApiResult
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.remote.source.RemoteMediaDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val localMediaDataSource: LocalMediaDataSource,
    private val remoteMediaDataSource: RemoteMediaDataSource,
    private val dispatcherProvider: DispatcherProvider
) : MediaRepository {

    override fun getMediaByPageAndLanguage(
        page: Int?,
        language: String?
    ): Flow<List<Media>> = flow {
        val localMedia = localMediaDataSource.getAll().map(MediaEntity::toDomain)
        emit(localMedia)
        val result = remoteMediaDataSource.getTrendings(page, language)
        when (result) {
            is ApiResult.Error -> throw Exception(result.message)
            is ApiResult.Success -> {
                val mediaList = result.data
                emit(mediaList)
                localMediaDataSource.insertAll(mediaList.map(MediaEntity::toEntity))
            }
        }
    }.flowOn(dispatcherProvider.io)

}
