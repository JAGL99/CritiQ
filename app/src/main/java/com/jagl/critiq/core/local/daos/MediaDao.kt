package com.jagl.critiq.core.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jagl.critiq.core.local.entities.MediaEntity

@Dao
interface MediaDao {

    @Upsert
    suspend fun upsertAll(medias: List<MediaEntity>)

    @Query("SELECT * FROM media WHERE id = :mediaId")
    fun getMediaById(mediaId: Long): MediaEntity?

    @Query("SELECT * FROM media WHERE " +
            "title LIKE '%' || :query || '%' " +
            "OR description LIKE '%' || :query || '%'")
    fun getAllByQuery(query: String): List<MediaEntity>

    @Query("SELECT * FROM media")
    fun getPagingSource(): PagingSource<Int, MediaEntity>

    @Query("DELETE FROM media")
    suspend fun clearAll()
}