package com.jagl.critiq.core.local.di

import android.content.Context
import androidx.room.Room
import com.jagl.critiq.core.local.AppDatabase
import com.jagl.critiq.core.local.daos.MediaDao
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import com.jagl.critiq.core.local.source.LocalMediaDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseDi::class]
)
object TestDatabaseDi {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
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
    ): LocalMediaDataSource {
        return LocalMediaDataSourceImpl(mediaDao)
    }

}