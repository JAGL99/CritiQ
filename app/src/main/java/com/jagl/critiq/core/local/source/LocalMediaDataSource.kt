package com.jagl.critiq.core.local.source

import androidx.paging.PagingSource
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.model.Media

interface LocalMediaDataSource {
    fun getAll(): PagingSource<Int, MediaEntity>
    fun getAllByQuery(query: String): List<Media>
    fun getById(id: Long): Media?
    suspend fun upsertAll(entities: List<Media>)
    suspend fun deleteAll()
}