package com.jagl.critiq.core.remote.source

import com.jagl.critiq.core.remote.api.MovieApi
import com.jagl.critiq.core.remote.data.TrendingResponse
import com.jagl.critiq.domain.dispatcherProvider.DispatcherProvider
import com.jagl.critiq.domain.data.MediaDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AllMediaSourceImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val dispatcherProvider: DispatcherProvider
) : AllMediaSource {

    override fun getTrendings(page: Int): Flow<List<MediaDomain>> = flow {
        val response = movieApi.getTrendings(page = page)
        if (!response.isSuccessful) {
            emit(emptyList())
        }
        val results = response.body()?.results?.map { media ->
            TrendingResponse.Result.toDomain(media).copy(page = page)
        }
        emit(results)
    }.map { it?.takeIf { it.isNotEmpty() } ?: emptyList() }.flowOn(dispatcherProvider.io)
}