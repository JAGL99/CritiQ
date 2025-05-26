package com.jagl.critiq.core.local.source

import com.jagl.critiq.core.model.Media
import kotlinx.coroutines.flow.Flow

interface LocalMediaDataSource {
    fun getAll(): Flow<List<Media>>
    fun getById(id: Long): Flow<Media?>
    suspend fun insertAll(domain: List<Media>)
    suspend fun insert(domain: Media)
    suspend fun deleteAll()
    suspend fun delete(domain: Media)
}