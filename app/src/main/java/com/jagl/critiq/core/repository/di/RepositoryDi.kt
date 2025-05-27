package com.jagl.critiq.core.repository.di

import com.jagl.critiq.core.common.dispatcherProvider.DispatcherProvider
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import com.jagl.critiq.core.remote.source.RemoteMediaDataSource
import com.jagl.critiq.core.repository.repository.MediaRepository
import com.jagl.critiq.core.repository.repository.MediaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryDi {

    @Singleton
    @Provides
    fun provideMovieSource(
        localMediaDataSource: LocalMediaDataSource,
        remoteMediaDataSource: RemoteMediaDataSource,
        dispatcherProvider: DispatcherProvider
    ): MediaRepository {
        return MediaRepositoryImpl(localMediaDataSource, remoteMediaDataSource, dispatcherProvider)
    }

}