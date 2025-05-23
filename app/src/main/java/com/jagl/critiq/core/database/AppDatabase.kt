package com.jagl.critiq.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jagl.critiq.core.database.daos.MediaDao
import com.jagl.critiq.core.database.entities.MediaEntity

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