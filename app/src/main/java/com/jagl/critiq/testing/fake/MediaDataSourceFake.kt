package com.jagl.critiq.testing.fake

import com.jagl.critiq.core.database.entities.MediaEntity
import com.jagl.critiq.core.database.source.MediaDataSource
import com.jagl.critiq.domain.data.MediaDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class MediaDataSourceFake : MediaDataSource {

    private val mediaList = MutableStateFlow<List<MediaEntity>>(emptyList())


    override fun getAll(): Flow<List<MediaDomain>> {
        return flow { mediaList.value }
    }

    override fun getById(id: Long): Flow<MediaDomain?> {
        return flow {
            val media = mediaList.value.find { it.id == id }
            if (media != null) {
                emit(MediaEntity.toDomain(media))
            } else {
                emit(null)
            }
        }
    }

    override suspend fun insertAll(domain: List<MediaDomain>) {
        mediaList.update { it + domain.map { media -> MediaEntity.toEntity(media) } }
    }

    override suspend fun insert(domain: MediaDomain) {
        mediaList.update { it + listOf(domain).map { media -> MediaEntity.toEntity(media) } }
    }

    override suspend fun deleteAll() {
        mediaList.update { emptyList() }
    }

    override suspend fun delete(domain: MediaDomain) {
        mediaList.filter { it.any { media -> media.id == domain.id } }
            .first()
            .let { media -> mediaList.update { it - media } }
    }
}