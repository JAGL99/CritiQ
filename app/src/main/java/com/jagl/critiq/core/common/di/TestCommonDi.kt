package com.jagl.critiq.core.common.di

import com.jagl.critiq.core.common.dispatcherProvider.DispatcherProvider
import com.jagl.critiq.core.test.TestDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CommonDi::class]
)
object TestCommonDi {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return TestDispatchers()
    }

}