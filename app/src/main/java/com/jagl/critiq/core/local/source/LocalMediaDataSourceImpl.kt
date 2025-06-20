package com.jagl.critiq.core.local.source

import androidx.paging.PagingSource
import com.jagl.critiq.core.local.daos.MediaDao
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.model.Media
import javax.inject.Inject

class LocalMediaDataSourceImpl @Inject constructor(
    private val mediaDao: MediaDao
) : LocalMediaDataSource {

    override fun getAll(): PagingSource<Int, MediaEntity> {
        return mediaDao.getPagingSource()
    }

    override fun getAllByQuery(query: String): List<Media> {
        return mediaDao.getAllByQuery(query).map(MediaEntity::toDomain)
    }

    override fun getById(id: Long): Media? {
        return mediaDao.getMediaById(id)?.let { MediaEntity.toDomain(it) }
    }

    override suspend fun upsertAll(media: List<Media>) {
        mediaDao.upsertAll(media.map(MediaEntity::toEntity))
    }

    override suspend fun deleteAll() {
        mediaDao.clearAll()
    }
}