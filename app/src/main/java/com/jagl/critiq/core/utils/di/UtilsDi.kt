package com.jagl.critiq.core.utils.di

import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsDi {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }

}