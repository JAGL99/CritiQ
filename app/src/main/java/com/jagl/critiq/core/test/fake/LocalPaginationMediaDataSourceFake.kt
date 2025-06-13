package com.jagl.critiq.core.test.fake

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.local.source.LocalPaginationMediaDataSource
import com.jagl.critiq.core.model.Media
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LocalPaginationMediaDataSourceFake : LocalPaginationMediaDataSource {

    private val mediaList = MutableStateFlow<List<Media>>(emptyList())

    override fun getAll(): PagingSource<Int, MediaEntity> {
        return object : PagingSource<Int, MediaEntity>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaEntity> {
                val startIndex = params.key ?: 0
                val endIndex = startIndex + params.loadSize
                val data =
                    mediaList.value.subList(startIndex, endIndex.coerceAtMost(mediaList.value.size))

                if (startIndex >= mediaList.value.size) {
                    return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
                }


                return LoadResult.Page(
                    data = data.map(MediaEntity::toEntity),
                    prevKey = if (startIndex == 0) null else startIndex - params.loadSize,
                    nextKey = if (endIndex >= mediaList.value.size) null else endIndex
                )
            }

            override fun getRefreshKey(state: PagingState<Int, MediaEntity>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: 0
                }
            }
        }
    }

    override fun getById(id: Long): Media? {
        return mediaList.value.find { it.id == id }
    }

    override suspend fun upsertAll(entities: List<Media>) {
        val ids = entities.map { it.id }.toSet()
        val newEntities = ids.map { id ->
            val entitiesWithSameId = entities.filter { it.id == id }
            if (entitiesWithSameId.count() == 1) {
                entitiesWithSameId.first()
            } else {
                val lastEntity = entitiesWithSameId.last()
                lastEntity
            }
        }
        mediaList.update { newEntities }

    }

    override suspend fun deleteAll() {
        mediaList.update { emptyList() }
    }
}