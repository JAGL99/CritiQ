package com.jagl.critiq.core.local.di

import android.content.Context
import androidx.room.Room
import com.jagl.critiq.core.local.AppDatabase
import com.jagl.critiq.core.local.daos.PaginateMediaDao
import com.jagl.critiq.core.local.source.LocalPaginationMediaDataSource
import com.jagl.critiq.core.local.source.LocalPaginationMediaDataSourceImpl
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