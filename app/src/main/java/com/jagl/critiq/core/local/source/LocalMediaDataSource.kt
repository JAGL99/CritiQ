package com.jagl.critiq.core.local.source

import com.jagl.critiq.core.local.entities.MediaEntity

interface LocalMediaDataSource {
    fun getAll(): List<MediaEntity>
    fun getById(id: Long): MediaEntity?
    suspend fun insertAll(entities: List<MediaEntity>)
    suspend fun insert(entity: MediaEntity)
    suspend fun deleteAll()
    suspend fun delete(entity: MediaEntity)
}