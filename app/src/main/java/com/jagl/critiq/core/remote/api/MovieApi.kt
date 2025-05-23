package com.jagl.critiq.core.remote.api

import com.jagl.critiq.core.remote.data.TrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("trending/all/day")
    suspend fun getTrendings(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): Response<TrendingResponse>

}
