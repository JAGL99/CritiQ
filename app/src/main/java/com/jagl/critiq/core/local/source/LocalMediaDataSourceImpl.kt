package com.jagl.critiq.core.local.source

import com.jagl.critiq.core.local.daos.MediaDao
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalMediaDataSourceImpl @Inject constructor(
    private val mediaDao: MediaDao
) : LocalMediaDataSource {
    override fun getAll(): Flow<List<Media>> {
        return mediaDao.getAllMedia()
            .map { it.map { entity -> MediaEntity.toDomain(entity) } }
    }

    override fun getById(id: Long): Flow<Media?> {
        return mediaDao.getMediaById(id).map { entity -> entity?.let { MediaEntity.toDomain(it) } }
    }

    override suspend fun insertAll(domain: List<Media>) {
        val mediaEntities = domain.map { media -> MediaEntity.toEntity(media) }
        mediaDao.insertAllMedia(mediaEntities)
    }

    override suspend fun insert(domain: Media) {
        mediaDao.insertMedia(MediaEntity.toEntity(domain))
    }

    override suspend fun deleteAll() {
        mediaDao.deleteAllMedia()
    }

    override suspend fun delete(domain: Media) {
        mediaDao.deleteMedia(MediaEntity.toEntity(domain))
    }

}