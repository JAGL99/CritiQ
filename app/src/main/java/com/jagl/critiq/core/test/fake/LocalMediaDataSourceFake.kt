package com.jagl.critiq.core.test.fake

import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import com.jagl.critiq.core.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class LocalMediaDataSourceFake : LocalMediaDataSource {

    private val mediaList = MutableStateFlow<List<MediaEntity>>(emptyList())


    override fun getAll(): Flow<List<Media>> {
        return flow { mediaList.value }
    }

    override fun getById(id: Long): Flow<Media?> {
        return flow {
            val media = mediaList.value.find { it.id == id }
            if (media != null) {
                emit(MediaEntity.toDomain(media))
            } else {
                emit(null)
            }
        }
    }

    override suspend fun insertAll(domain: List<Media>) {
        mediaList.update { it + domain.map { media -> MediaEntity.toEntity(media) } }
    }

    override suspend fun insert(domain: Media) {
        mediaList.update { it + listOf(domain).map { media -> MediaEntity.toEntity(media) } }
    }

    override suspend fun deleteAll() {
        mediaList.update { emptyList() }
    }

    override suspend fun delete(domain: Media) {
        mediaList.filter { it.any { media -> media.id == domain.id } }
            .first()
            .let { media -> mediaList.update { it - media } }
    }
}