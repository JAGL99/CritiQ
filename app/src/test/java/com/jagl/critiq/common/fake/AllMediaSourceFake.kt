package com.jagl.critiq.common.fake

import com.jagl.critiq.common.mediaDomain
import com.jagl.critiq.core.remote.source.AllMediaSource
import com.jagl.critiq.domain.data.MediaDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AllMediaSourceFake : AllMediaSource {

    private val mediaList: MutableStateFlow<Map<Int, List<MediaDomain>>> = MutableStateFlow(
        (1..5).associateWith { page ->
            (1..10).map {
                mediaDomain().copy(
                    id = it.toLong(),
                    page = page
                )
            }
        }
    )

    private var shouldReturnError = false


    override fun getTrendings(page: Int): Flow<List<MediaDomain>> {
        return flow {
            val data = mediaList.first { it.containsKey(page) }.values.toList().first()
            emit(
                if (shouldReturnError) {
                    throw Exception("Error fetching data")
                } else {
                    data
                }
            )
        }

    }

}