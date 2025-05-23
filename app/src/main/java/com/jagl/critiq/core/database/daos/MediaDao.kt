package com.jagl.critiq.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jagl.critiq.core.database.entities.MediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: MediaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMedia(mediaList: List<MediaEntity>)

    @Query("SELECT * FROM media WHERE id = :mediaId")
    fun getMediaById(mediaId: Long): Flow<MediaEntity?>

    @Query("SELECT * FROM media")
    fun getAllMedia(): Flow<List<MediaEntity>>

    @Delete
    suspend fun deleteMedia(media: MediaEntity)

    @Query("DELETE FROM media")
    suspend fun deleteAllMedia()
}