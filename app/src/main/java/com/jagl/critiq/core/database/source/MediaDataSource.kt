package com.jagl.critiq.core.database.source

import com.jagl.critiq.domain.data.MediaDomain
import kotlinx.coroutines.flow.Flow

interface MediaDataSource {
    fun getAll(): Flow<List<MediaDomain>>
    fun getById(id: Long): Flow<MediaDomain?>
    suspend fun insertAll(domain: List<MediaDomain>)
    suspend fun insert(domain: MediaDomain)
    suspend fun deleteAll()
    suspend fun delete(domain: MediaDomain)
}