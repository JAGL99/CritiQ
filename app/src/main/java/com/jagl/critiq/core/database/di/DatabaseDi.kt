package com.jagl.critiq.core.database.di

import android.content.Context
import androidx.room.Room
import com.jagl.critiq.core.database.AppDatabase
import com.jagl.critiq.core.database.daos.MediaDao
import com.jagl.critiq.core.database.source.MediaDataSourceImpl
import com.jagl.critiq.core.database.source.MediaDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseDi {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideMediaDao(
        appDatabase: AppDatabase
    ): MediaDao {
        return appDatabase.mediaDao()
    }

    @Singleton
    @Provides
    fun provideMediaDataSource(
        mediaDao: MediaDao
    ): MediaDataSource {
        return MediaDataSourceImpl(mediaDao)
    }

}