package com.jagl.critiq.core.test.fake

import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import kotlinx.coroutines.flow.MutableStateFlow
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
        newEntities.forEach { entity -> insert(entity) }
    }

    override suspend fun insert(entity: MediaEntity) {
        val existingEntities = mediaList.value.filter { it.id != entity.id }
        mediaList.update { existingEntities + listOf(entity).toSet() }
    }

    override suspend fun deleteAll() {
        mediaList.update { emptyList() }
    }

    override suspend fun delete(entity: MediaEntity) {
        mediaList.value.filter { it.id != entity.id }
            .let { filteredList -> mediaList.update { filteredList } }
    }
}