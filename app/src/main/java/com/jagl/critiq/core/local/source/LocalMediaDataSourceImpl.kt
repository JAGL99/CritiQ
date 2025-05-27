package com.jagl.critiq.core.local.source

import com.jagl.critiq.core.local.daos.MediaDao
import com.jagl.critiq.core.local.entities.MediaEntity
import javax.inject.Inject

class LocalMediaDataSourceImpl @Inject constructor(
    private val mediaDao: MediaDao
) : LocalMediaDataSource {
    override fun getAll(): List<MediaEntity> {
        return mediaDao.getAllMedia()
    }

    override fun getById(id: Long): MediaEntity? {
        return mediaDao.getMediaById(id)
    }

    override suspend fun insertAll(entities: List<MediaEntity>) {
        mediaDao.insertAllMedia(entities)
    }

    override suspend fun insert(entity: MediaEntity) {
        mediaDao.insertMedia(entity)
    }

    override suspend fun deleteAll() {
        mediaDao.deleteAllMedia()
    }

    override suspend fun delete(entity: MediaEntity) {
        mediaDao.deleteMedia(entity)
    }

}