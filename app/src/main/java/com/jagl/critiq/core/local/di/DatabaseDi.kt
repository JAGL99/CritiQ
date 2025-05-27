package com.jagl.critiq.core.local.di

import android.content.Context
import androidx.room.Room
import com.jagl.critiq.core.local.AppDatabase
import com.jagl.critiq.core.local.daos.PaginateMediaDao
import com.jagl.critiq.core.local.source.LocalPaginationMediaDataSourceImpl
import com.jagl.critiq.core.local.source.LocalPaginationMediaDataSource
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
    ): PaginateMediaDao {
        return appDatabase.mediaDao()
    }

    @Singleton
    @Provides
    fun provideMediaDataSource(
        paginateMediaDao: PaginateMediaDao
    ): LocalPaginationMediaDataSource {
        return LocalPaginationMediaDataSourceImpl(paginateMediaDao)
    }

}