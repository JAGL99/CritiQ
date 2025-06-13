package com.jagl.critiq.core.repository.di

import com.jagl.critiq.core.common.dispatcherProvider.DispatcherProvider
import com.jagl.critiq.core.local.AppDatabase
import com.jagl.critiq.core.local.source.LocalPaginationMediaDataSource
import com.jagl.critiq.core.repository.repository.MediaRepository
import com.jagl.critiq.core.repository.repository.MediaRepositoryImpl
import com.jagl.critiq.core.test.fake.RemotePaginateMediaDataSourceFake
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryDi::class]
)
object TestRepositoryDi {

    @Singleton
    @Provides
    fun provideMovieSource(
        database: AppDatabase,
        localPaginationMediaDataSource: LocalPaginationMediaDataSource,
        dispatcherProvider: DispatcherProvider
    ): MediaRepository {
        val remotePaginateMediaDataSource = RemotePaginateMediaDataSourceFake()
        return MediaRepositoryImpl(
            database = database,
            localPaginationMediaDataSource = localPaginationMediaDataSource,
            remotePaginateMediaDataSource = remotePaginateMediaDataSource,
            dispatcherProvider = dispatcherProvider
        )
    }

}