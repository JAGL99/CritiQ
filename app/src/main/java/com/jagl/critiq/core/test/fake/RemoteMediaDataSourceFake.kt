package com.jagl.critiq.core.test.fake

import com.google.gson.Gson
import com.jagl.critiq.core.model.ApiResult
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.remote.model.MovieDbApiError
import com.jagl.critiq.core.remote.source.RemoteMediaDataSource
import com.jagl.critiq.core.remote.utils.RequestData
import com.jagl.critiq.core.test.listOfLanguagePairMediaWithPage
import com.jagl.critiq.core.test.movieDbApiError
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class RemoteMediaDataSourceFake(
    private val mediaList: List<Pair<String, List<Pair<Int, List<Media>>>>> = listOfLanguagePairMediaWithPage(),
    private val shouldReturnError: Boolean = false,
    private val exception: Exception = Exception()
) : RemoteMediaDataSource {


    override suspend fun getTrendings(
        page: Int?,
        language: String?
    ): ApiResult<List<Media>> {
        return RequestData.safeCall {
            if (shouldReturnError) {
                throw exception
            } else {
                if (language.isNullOrEmpty()) {
                    val medias = mediaList.map { mapPages(page, it) }.flatten()
                    medias
                } else {
                    mediaList.find { it.first == language }
                        ?.let { languagePair -> mapPages(page, languagePair) }
                        ?: mediaList.map { mapPages(page, it) }.flatten()
                }
            }
        }

    }

    private fun mapPages(
        page: Int?,
        pairPageMedia: Pair<String, List<Pair<Int, List<Media>>>>
    ) = if (page == null) {
        pairPageMedia.second.map { it.second }.flatten()
    } else {
        if (page > 0) {
            pairPageMedia.second.find { it.first == page }?.second ?: emptyList()
        } else throw HttpException(
            Response.error<MovieDbApiError>(
                400,
                Gson().toJson(movieDbApiError())
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
        )

    }


}