package com.jagl.critiq.testing.fake

import com.jagl.critiq.core.remote.source.AllMediaSource
import com.jagl.critiq.domain.data.MediaDomain
import com.jagl.critiq.testing.mediaDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AllMediaSourceFake : AllMediaSource {

    val mediaList: MutableStateFlow<Map<Int, List<MediaDomain>>> = MutableStateFlow(
        (1..5).associateWith { page ->
            (1..10).map {
                mediaDomain().copy(
                    id = it.toLong(),
                    page = page
                )
            }
        }
    )

    private var isError = false


    override fun getTrendings(page: Int): Flow<List<MediaDomain>> {
        return flow {
            val data = mediaList.first { it.containsKey(page) }.values.toList().first()
            if (isError) {
                throw Exception("Error fetching data")
            }
            emit(data)
        }

    }

}