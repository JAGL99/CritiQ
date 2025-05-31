package com.jagl.critiq.feature.home

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import com.jagl.critiq.common.fake.AllMediaSourceFake
import com.jagl.critiq.common.fake.MediaDataSourceFake
import com.jagl.critiq.common.rules.junit5.MainCoroutineExtension
import com.jagl.critiq.core.database.source.MediaDataSource
import com.jagl.critiq.core.remote.source.AllMediaSource
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var allMediaSource: AllMediaSource
    private lateinit var mediaDataSource: MediaDataSource

    companion object {
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    @BeforeEach
    fun setUp() {
        TODO("Finish the test cases")
        allMediaSource = AllMediaSourceFake()
        mediaDataSource = MediaDataSourceFake()
        viewModel = HomeViewModel(
            allMediaSource = allMediaSource,
            mediaDataSource = mediaDataSource
        )
    }

    @Test
    fun `init state is loading`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state).isInstanceOf(HomeViewModel.UiState.Loading::class)
        }
    }

    @Test
    fun `test getAllMedia`() = runTest {
        viewModel.uiState.test {
            val initState = awaitItem()
            assertThat(initState).isInstanceOf(HomeViewModel.UiState.Loading::class)
            val state = awaitItem()
            assertThat(state).isInstanceOf(HomeViewModel.UiState.Success::class)
            state as HomeViewModel.UiState.Success
            val data = state.data
            assertThat(data).isNotEmpty()
            assertThat(data).hasSize(10)
        }
    }
}
