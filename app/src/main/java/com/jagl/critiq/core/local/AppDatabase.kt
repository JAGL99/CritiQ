package com.jagl.critiq.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jagl.critiq.core.local.daos.PaginateMediaDao
import com.jagl.critiq.core.local.entities.MediaEntity

@Database(
    entities = [MediaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mediaDao(): PaginateMediaDao

    companion object {
        const val DATABASE_NAME = "critiq_database"
    }
}