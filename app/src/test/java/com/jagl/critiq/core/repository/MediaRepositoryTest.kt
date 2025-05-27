package com.jagl.critiq.core.repository

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.repository.repository.MediaRepositoryImpl
import com.jagl.critiq.core.test.TestDispatchers
import com.jagl.critiq.core.test.fake.LocalMediaDataSourceFake
import com.jagl.critiq.core.test.fake.RemoteMediaDataSourceFake
import com.jagl.critiq.core.test.listOfLanguagePairMediaWithPage
import com.jagl.critiq.core.test.rule.MainCoroutineExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class MediaRepositoryTest {

    private lateinit var repository: MediaRepositoryImpl
    private lateinit var remoteDataSource: RemoteMediaDataSourceFake
    private lateinit var localDataSource: LocalMediaDataSourceFake
    private var mediaList = listOfLanguagePairMediaWithPage()

    @BeforeEach
    fun setUp() {
        remoteDataSource = RemoteMediaDataSourceFake(mediaList = mediaList)
        localDataSource = LocalMediaDataSourceFake()
        repository = MediaRepositoryImpl(
            remoteMediaDataSource = remoteDataSource,
            localMediaDataSource = localDataSource,
            dispatcherProvider = TestDispatchers()
        )
    }

    @Test
    fun `Get media by language, get all media by language`(): Unit = runTest {
        val expectedLanguage = mediaList.first().first
        val expectedData = mediaList.first().second.map { it.second }.flatten()
        repository.getMediaByPageAndLanguage(null, expectedLanguage).test {
            val emit1 = awaitItem()
            assertThat(emit1).isEmpty()
            val emit2 = awaitItem()
            assertThat(emit2).isNotEmpty()
            assertThat(emit2).hasSize(expectedData.size)
            val localData = localDataSource.getAll().map(MediaEntity::toDomain)
            assertThat(localData).hasSize(expectedData.size)
            assertThat(localData.first()).isEqualTo(expectedData.first())
            cancelAndIgnoreRemainingEvents()
        }
    }
}