package com.jagl.critiq.core.remote.api

import com.jagl.critiq.core.remote.model.TrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("trending/movie/day")
    suspend fun getTrendings(
        @Query("language") language: String?,
        @Query("page") page: Int?,
    ): Response<TrendingResponse>

}
