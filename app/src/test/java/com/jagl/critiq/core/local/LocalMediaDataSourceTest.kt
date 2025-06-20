package com.jagl.critiq.core.local

import androidx.paging.PagingSource
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import com.jagl.critiq.core.test.fake.LocalMediaDataSourceFake
import com.jagl.critiq.core.test.media
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocalMediaDataSourceTest {

    private lateinit var lmds: LocalMediaDataSource
    private lateinit var pagingSourceParams: PagingSource.LoadParams.Refresh<Int>

    @BeforeEach
    fun setUp() {
        lmds = LocalMediaDataSourceFake()
        pagingSourceParams = PagingSource.LoadParams.Refresh(
            key = null,
            loadSize = 0,
            placeholdersEnabled = false
        )
    }

    @Test
    fun `load returns empty when no data`() = runTest {
        val pagingSource = lmds.getAll()
        val result = pagingSource.load(pagingSourceParams)
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class)
        val page = result as PagingSource.LoadResult.Page<Int, MediaEntity>
        assertThat(page.data).isEmpty()
    }

    @Test
    fun `Save data in local, get all data`() = runBlocking {
        val dataToSave = (0..10).map { index ->
            media().copy(
                id = index.toLong(),
                title = "Media $index",
                description = "Description for media $index"
            )
        }
        lmds.upsertAll(dataToSave)
        val pagingSource = lmds.getAll()
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )
        val page = result as PagingSource.LoadResult.Page<Int, MediaEntity>
        assertThat(page.data).isNotEmpty()
        assertThat(page.data).hasSize(10)
        assertThat(page.data.first()).isInstanceOf(MediaEntity::class)
        page.data.forEachIndexed { index, mediaEntity ->
            assertThat(MediaEntity.toDomain(mediaEntity)).isEqualTo(dataToSave[index])
        }

    }

    @Test
    fun `Save all data ,get data one by one by id`(): Unit = runBlocking {
        val dataToSave = (1..3).map { index ->
            media().copy(id = index.toLong())
        }
        lmds.upsertAll(dataToSave)
        dataToSave.forEach {
            val entity = lmds.getById(it.id)
            assertThat(entity).isNotNull()
            assertThat(entity).isEqualTo(it)
        }

    }

    @Test
    fun `Save all data, delete all data`() = runTest {
        val dataToSave = (1..3).map { index ->
            media().copy(id = index.toLong())
        }
        lmds.upsertAll(dataToSave)
        lmds.getAll()
        //assertThat(beforeDeleteData).hasSize(3)
        lmds.deleteAll()
        val afterDeleteData = lmds.getAll()
        //assertThat(afterDeleteData).hasSize(0)
    }

    @Test
    fun `Save all data, get by id, not found`() = runBlocking {
        val dataToSave = (1..3).map { index ->
            media().copy(id = index.toLong())
        }
        lmds.upsertAll(dataToSave)
        val notFoundId = 999L
        val entity = lmds.getById(notFoundId)
        assertThat(entity).isNull()
    }

    @Test
    fun `Save data with same id, last value`() = runBlocking {
        val dataToSave = (1..2).map { index ->
            media().copy(
                id = index.toLong(),
                title = "Media $index",
                description = "Description for media $index"
            )
        }.map { it.copy(id = 1L) }
        lmds.upsertAll(dataToSave)
        val entity = lmds.getById(1L)
        assertThat(entity).isNotNull()
        assertThat(entity).isEqualTo(dataToSave.last())
    }

    @Test
    fun `Save data, get by query`() = runBlocking {
        val dataToSave = (1..5).map { index ->
            media().copy(
                id = index.toLong(),
                title = "Media $index",
                description = "Description random $index"
            )
        }
        lmds.upsertAll(dataToSave)

        val titleSearch = lmds.getAllByQuery("Media")
        assertThat(titleSearch).isNotEmpty()
        assertThat(titleSearch).hasSize(dataToSave.size)
        assertThat(titleSearch.first().title).isEqualTo(dataToSave.first().title)
        assertThat(titleSearch.last().title).isEqualTo(dataToSave.last().title)

        val title = dataToSave.random().title
        val titleSearchSingle = lmds.getAllByQuery(title)
        assertThat(titleSearchSingle).isNotEmpty()
        assertThat(titleSearchSingle).hasSize(1)
        assertThat(titleSearchSingle.first().title).isEqualTo(title)

        val description = dataToSave.random().description
        println("Searching for description: $description")
        val descriptionSearch = lmds.getAllByQuery(description)
        println("Found description: $descriptionSearch")
        assertThat(descriptionSearch).isNotEmpty()
        assertThat(descriptionSearch).hasSize(1)
        assertThat(descriptionSearch.first().description).isEqualTo(description)

        val invalidSearch = lmds.getAllByQuery("Invalid Query")
        assertThat(invalidSearch).isEmpty()
    }
}