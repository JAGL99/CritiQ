package com.jagl.critiq.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jagl.critiq.core.local.daos.MediaDao
import com.jagl.critiq.core.local.entities.MediaEntity

@Database(
    entities = [MediaEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mediaDao(): MediaDao

    companion object {
        const val DATABASE_NAME = "critiq_database"
    }
}