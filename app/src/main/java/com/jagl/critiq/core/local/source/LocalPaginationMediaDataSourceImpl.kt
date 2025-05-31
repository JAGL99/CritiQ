package com.jagl.critiq.core.local.source

import androidx.paging.PagingSource
import com.jagl.critiq.core.local.daos.PaginateMediaDao
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.model.Media
import javax.inject.Inject

class LocalPaginationMediaDataSourceImpl @Inject constructor(
    private val paginateMediaDao: PaginateMediaDao
) : LocalPaginationMediaDataSource {

    override fun getAll(): PagingSource<Int, MediaEntity> {
        return paginateMediaDao.getPagingSource()
    }

    override fun getById(id: Long): Media? {
        return paginateMediaDao.getMediaById(id)?.let { MediaEntity.toDomain(it) }
    }

    override suspend fun upsertAll(media: List<Media>) {
        paginateMediaDao.upsertAll(media.map(MediaEntity::toEntity))
    }

    override suspend fun deleteAll() {
        paginateMediaDao.clearAll()
    }
}