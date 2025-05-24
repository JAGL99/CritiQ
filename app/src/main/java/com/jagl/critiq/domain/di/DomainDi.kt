package com.jagl.critiq.domain.di

import com.jagl.critiq.domain.dispatcherProvider.DispatcherProvider
import com.jagl.critiq.domain.dispatcherProvider.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainDi {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }

}