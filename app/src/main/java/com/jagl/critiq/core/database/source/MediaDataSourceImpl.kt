package com.jagl.critiq.core.database.source

import com.jagl.critiq.core.database.daos.MediaDao
import com.jagl.critiq.core.database.entities.MediaEntity
import com.jagl.critiq.domain.data.MediaDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaDataSourceImpl @Inject constructor(
    private val mediaDao: MediaDao
) : MediaDataSource {
    override fun getAll(): Flow<List<MediaDomain>> {
        return mediaDao.getAllMedia()
            .map { it.map { entity -> MediaEntity.toDomain(entity) } }
    }

    override fun getById(id: Long): Flow<MediaDomain?> {
        return mediaDao.getMediaById(id).map { entity -> entity?.let { MediaEntity.toDomain(it) } }
    }

    override suspend fun insertAll(domain: List<MediaDomain>) {
        val mediaEntities = domain.map { media -> MediaEntity.toEntity(media) }
        mediaDao.insertAllMedia(mediaEntities)
    }

    override suspend fun insert(domain: MediaDomain) {
        mediaDao.insertMedia(MediaEntity.toEntity(domain))
    }

    override suspend fun deleteAll() {
        mediaDao.deleteAllMedia()
    }

    override suspend fun delete(domain: MediaDomain) {
        mediaDao.deleteMedia(MediaEntity.toEntity(domain))
    }

}