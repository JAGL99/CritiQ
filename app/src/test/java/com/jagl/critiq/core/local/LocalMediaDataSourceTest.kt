package com.jagl.critiq.core.local

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import com.jagl.critiq.core.test.fake.LocalMediaDataSourceFake
import com.jagl.critiq.core.test.mediaEntity
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocalMediaDataSourceTest {

    private lateinit var lmds: LocalMediaDataSource

    @BeforeEach
    fun setUp() {
        lmds = LocalMediaDataSourceFake()
    }

    @Test
    fun `Save data, get saved data`() = runBlocking {
        val dataToSave = (1..10).map { index ->
            mediaEntity().copy(
                id = index.toLong(),
                title = "Media $index",
                description = "Description for media $index"
            )
        }
        lmds.insertAll(dataToSave)
        val savedData = lmds.getAll()
        assertThat(savedData.count()).isEqualTo(dataToSave.count())
    }

    @Test
    fun `Save data on by one , get data one by one by id`(): Unit = runBlocking {
        val dataToSave = (1..3).map { index ->
            mediaEntity().copy(id = index.toLong())
        }
        dataToSave.forEach {
            lmds.insert(it)
        }
        dataToSave.forEach {
            val entity = lmds.getById(it.id)
            assertThat(entity).isNotNull()
            assertThat(entity).isEqualTo(it)
        }

    }

    @Test
    fun `Save data, delete single data`() = runBlocking {
        val dataToSave = (1..3).map { index ->
            mediaEntity().copy(id = index.toLong())
        }
        lmds.insertAll(dataToSave)
        val beforeDeleteData = lmds.getAll()
        assertThat(beforeDeleteData).isEqualTo(dataToSave)
        val entityToDelete = dataToSave.first()
        lmds.delete(entityToDelete)
        val afterDeleteData = lmds.getAll()
        assertThat(afterDeleteData).hasSize(2)
        assertThat(beforeDeleteData.size).isNotEqualTo(afterDeleteData.size)
    }

    @Test
    fun `Save data, delete all data`() = runBlocking {
        val dataToSave = (1..3).map { index ->
            mediaEntity().copy(id = index.toLong())
        }
        lmds.insertAll(dataToSave)
        val beforeDeleteData = lmds.getAll()
        assertThat(beforeDeleteData).hasSize(3)
        lmds.deleteAll()
        val afterDeleteData = lmds.getAll()
        assertThat(afterDeleteData).hasSize(0)
    }

    @Test
    fun `Save data, get by id, not found`() = runBlocking {
        val dataToSave = (1..3).map { index ->
            mediaEntity().copy(id = index.toLong())
        }
        lmds.insertAll(dataToSave)
        val notFoundId = 999L
        val entity = lmds.getById(notFoundId)
        assertThat(entity).isNull()
    }

    @Test
    fun `Save data with same id, last value`() = runBlocking {
        val dataToSave = (1..2).map { index ->
            mediaEntity()
                .copy(
                    id = index.toLong(),
                    title = "Media $index",
                    description = "Description for media $index"
                )
        }.map { it.copy(id = 1L) }
        lmds.insertAll(dataToSave)
        val entity = lmds.getById(1L)
        assertThat(entity).isNotNull()
        assertThat(entity).isEqualTo(dataToSave.last())
        val entities = lmds.getAll()
        assertThat(entities).hasSize(1)
    }

}