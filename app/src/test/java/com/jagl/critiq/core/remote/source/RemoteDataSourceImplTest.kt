package com.jagl.critiq.core.remote.source

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.jagl.critiq.core.model.ApiResult
import com.jagl.critiq.core.remote.utils.RequestData
import com.jagl.critiq.core.test.fake.RemoteMediaDataSourceFake
import com.jagl.critiq.core.test.movieDbApiError
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RemoteMediaDataSourceTest {

    private lateinit var remoteMediaDataSource: RemoteMediaDataSource

    @BeforeEach
    fun setUp() {
        remoteMediaDataSource = RemoteMediaDataSourceFake()
    }

    @ParameterizedTest
    @CsvSource(
        "en-US, 5",
        "es-ES, 10",
        "fr-FR, 15",
        "de-DE, 20",
        "it-IT, 25"
    )
    fun `Request trendings with language and null page, get all the result asociated with the language`(
        language: String,
        resultSize: Int
    ): Unit = runBlocking {
        val result = remoteMediaDataSource.getTrendings(page = null, language = language)
        assertThat(result).isInstanceOf(ApiResult.Success::class)
        val data = (result as ApiResult.Success).data
        assertThat(data).hasSize(resultSize)
    }

    @Test
    fun `Request trendings with  null language and null page, get all the results`(): Unit =
        runBlocking {
            val result = remoteMediaDataSource.getTrendings(page = null, language = null)
            val data = (result as ApiResult.Success).data
            assertThat(data).hasSize(75)
        }

    @ParameterizedTest
    @CsvSource(
        "1, 25",
        "2, 20",
        "3, 15",
        "4, 10",
        "5, 5"
    )
    fun `Request trendings with page and null language, get the results asociate to the page`(
        page: Int,
        resultSize: Int
    ): Unit = runBlocking {
        val result = remoteMediaDataSource.getTrendings(page = page, language = null)
        val data = (result as ApiResult.Success).data
        assertThat(data).hasSize(resultSize)
    }

    @Test
    fun `Request trendings by page and language, get the result asociated to the page and language`()
    : Unit = runBlocking {
        val result = remoteMediaDataSource.getTrendings(page = 1, language = "it-IT")
        val data = (result as ApiResult.Success).data
        assertThat(data).hasSize(5)
    }

    @Test
    fun `Get trendings with invalid language and invalid page, get empty list`() = runBlocking {
        val result = remoteMediaDataSource.getTrendings(page = 100, language = "xx-XX")
        assertThat(result).isInstanceOf(ApiResult.Success::class)
        val data = (result as ApiResult.Success).data
        assertThat(data).isEmpty()
    }

    @Test
    fun `Get trendings with invalid language and null page, get full medias`() = runBlocking {
        val result = remoteMediaDataSource.getTrendings(page = null, language = "xx-XX")
        assertThat(result).isInstanceOf(ApiResult.Success::class)
        val data = (result as ApiResult.Success).data
        assertThat(data).hasSize(75)
    }

    @Test
    fun `Get trendings with null language and valid page, get empty list`() = runBlocking {
        val result = remoteMediaDataSource.getTrendings(page = 100, language = null)
        assertThat(result).isInstanceOf(ApiResult.Success::class)
        val data = (result as ApiResult.Success).data
        assertThat(data).isEmpty()
    }

    @Test
    fun `Get trendings with mapped error, should return mapped error message`() = runBlocking {
        RequestData.NETWORK_EXCEPTIONS.forEach { exception ->
            remoteMediaDataSource = RemoteMediaDataSourceFake(
                shouldReturnError = true,
                exception = exception
            )
            val result = remoteMediaDataSource.getTrendings(page = null, language = "en-US")
            val errorMessage = (result as ApiResult.Error).message
            assertThat(errorMessage).isEqualTo(exception.message)
        }
    }

    @Test
    fun `Get trendings with generic error, should return generic error message`() = runBlocking {
        remoteMediaDataSource = RemoteMediaDataSourceFake(shouldReturnError = true)
        val result = remoteMediaDataSource.getTrendings(page = null, language = "en-US")
        assertThat(result).isInstanceOf(ApiResult.Error::class)
        val errorMessage = (result as ApiResult.Error).message
        assertThat(errorMessage).isEqualTo(RequestData.DEFAULT_EXCEPTION_MESSAGE)
    }

    @Test
    fun `Get trendings with custom error, should return custom error message`() = runBlocking {
        val customError = Exception("Custom error message")
        remoteMediaDataSource = RemoteMediaDataSourceFake(
            shouldReturnError = true,
            exception = customError
        )
        val result = remoteMediaDataSource.getTrendings(page = null, language = "en-US")
        val errorMessage = (result as ApiResult.Error).message
        assertThat(errorMessage).isEqualTo(customError.message)
    }

    @Test
    fun `Get trendings with negative page, should return movie db error`() = runBlocking {
        val expectedErrorMessage = movieDbApiError().statusMessage
        val result = remoteMediaDataSource.getTrendings(page = -1, language = null)
        val errorMessage = (result as ApiResult.Error).message
        assertThat(errorMessage).isEqualTo(expectedErrorMessage)
    }
}