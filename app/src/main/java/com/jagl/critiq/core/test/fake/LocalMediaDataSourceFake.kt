package com.jagl.critiq.core.test.fake

import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class LocalMediaDataSourceFake : LocalMediaDataSource {

    private val mediaList = MutableStateFlow<List<MediaEntity>>(emptyList())


    override fun getAll(): List<MediaEntity> {
        return mediaList.value
    }

    override fun getById(id: Long): MediaEntity? {
        return mediaList.value.find { it.id == id }
    }

    override suspend fun insertAll(entities: List<MediaEntity>) {
        mediaList.update { it + entities }
    }

    override suspend fun insert(entity: MediaEntity) {
        mediaList.update { it + listOf(entity).toSet() }
    }

    override suspend fun deleteAll() {
        mediaList.update { emptyList() }
    }

    override suspend fun delete(entity: MediaEntity) {
        mediaList.filter { it.any { media -> media.id == entity.id } }
            .first()
            .let { media -> mediaList.update { it - media.toSet() } }
    }
}