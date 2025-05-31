package com.jagl.critiq.core.remote.source

import com.jagl.critiq.core.model.ApiResult
import com.jagl.critiq.core.model.Media

fun interface RemotePaginateMediaDataSource {


    suspend fun getTrendings(page: Int?, language: String?): ApiResult<List<Media>>

}