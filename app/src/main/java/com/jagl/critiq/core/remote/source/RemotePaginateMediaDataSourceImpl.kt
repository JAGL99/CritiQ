package com.jagl.critiq.core.remote.source

import com.jagl.critiq.core.model.ApiResult
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.remote.api.MovieApi
import com.jagl.critiq.core.remote.model.TrendingResponse
import com.jagl.critiq.core.remote.utils.RequestData.safeCall
import javax.inject.Inject

class RemotePaginateMediaDataSourceImpl @Inject constructor(
    private val movieApi: MovieApi
) : RemotePaginateMediaDataSource {

    override suspend fun getTrendings(
        page: Int?,
        language: String?
    ): ApiResult<List<Media>> {
        return safeCall {
            val response = movieApi.getTrendings(
                page = page,
                language = language
            )
            val body = response.body()!!
            val mediaList = body.results.mapNotNull(TrendingResponse.Result::toDomain)
            mediaList
        }
    }
}