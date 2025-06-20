package com.jagl.critiq.core.repository

import androidx.paging.PagingSource
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import com.jagl.critiq.core.common.CriticQAndroidTest
import com.jagl.critiq.core.local.AppDatabase
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.remote.source.RemotePaginateMediaDataSource
import com.jagl.critiq.core.repository.repository.MediaRepository
import com.jagl.critiq.core.repository.repository.MediaRepositoryImpl
import com.jagl.critiq.core.test.TestDispatchers
import com.jagl.critiq.core.test.fake.RemotePaginateMediaDataSourceFake
import com.jagl.critiq.core.test.listOfLanguagePairMediaWithPage
import com.jagl.critiq.core.test.paginSourceRefresh
import com.jagl.critiq.core.test.rule.MainCoroutineRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MediaRepositoryTest : CriticQAndroidTest() {


    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var localDataSource: LocalMediaDataSource

    @Inject
    lateinit var database: AppDatabase

    private lateinit var repository: MediaRepository
    private lateinit var pagingSourceParams: PagingSource.LoadParams.Refresh<Int>
    private lateinit var remoteDataSource: RemotePaginateMediaDataSource
    private lateinit var mediaList: List<Pair<String, List<Pair<Int, List<Media>>>>>

    override fun setUp() {
        super.setUp()
        mediaList = listOfLanguagePairMediaWithPage()
        pagingSourceParams = paginSourceRefresh()
        remoteDataSource = RemotePaginateMediaDataSourceFake(mediaList = mediaList)
        repository = MediaRepositoryImpl(
            remotePaginateMediaDataSource = remoteDataSource,
            localMediaDataSource = localDataSource,
            dispatcherProvider = TestDispatchers(),
            database = database
        )

    }


    @Test
    fun GetEmptyMediaWhenNoDataInLocal(): Unit = runTest {
        val pagingSource = localDataSource.getAll()
        val result = pagingSource.load(pagingSourceParams)
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class)
        val page = result as PagingSource.LoadResult.Page<Int, MediaEntity>
        assertThat(page.data).isEmpty()
    }

    @Test
    fun GetMediaByLanguageAndReturnAllMediaWithThatLanguageAndStoreInLocalDataSource(): Unit =
        runTest {
            val expectedLanguage = mediaList.first().first
            val expectedData = mediaList.first().second.map { it.second }.flatten()
            val paginDataFlow = repository.getPagingMedia(expectedLanguage)
            val itemsSnapshot = paginDataFlow.asSnapshot { scrollTo(20) }
            val first = itemsSnapshot.firstOrNull()
            val last = itemsSnapshot.lastOrNull()

            assertThat(first).isNotNull()
            assertThat(first).isEqualTo(expectedData.first())
            assertThat(last).isEqualTo(expectedData.last())
            assertThat(itemsSnapshot).hasSize(expectedData.size)

            val pagingSourceAfterLoad = localDataSource.getAll()
            val resultAfterLoad = pagingSourceAfterLoad.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = itemsSnapshot.size,
                    placeholdersEnabled = false
                )
            )
            val pageAfeterLoad = resultAfterLoad as PagingSource.LoadResult.Page<Int, MediaEntity>
            assertThat(pageAfeterLoad.data).isNotEmpty()
            assertThat(pageAfeterLoad.data).hasSize(expectedData.size)
        }

    @Test
    fun GetMediaByQueryAndReturnAllMediaWithThatQuery(): Unit = runTest {

        val data = mediaList.first().second.map { it.second }.flatten()
        val titleQuery = data.random().title
        val descriptionQuery = data.random().description

        localDataSource.upsertAll(data)

        val titleResult = repository.getMediaByQuery(titleQuery).first()
        assertThat(titleResult).isNotEmpty()
        assertThat(titleResult).hasSize(1)
        assertThat(titleResult.first().title).isEqualTo(titleQuery)

        val descriptionResult = repository.getMediaByQuery(descriptionQuery).first()
        assertThat(descriptionResult).isNotEmpty()
        assertThat(descriptionResult).hasSize(1)
        assertThat(descriptionResult.first().description).isEqualTo(descriptionQuery)

        val nonExistentQuery = "NonExistentQuery"
        val nonExistentResult = repository.getMediaByQuery(nonExistentQuery).first()
        assertThat(nonExistentResult).isEmpty()
    }
}