package com.jagl.critiq.core.remote.model

import com.google.gson.annotations.SerializedName
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.remote.mappers.MapperData

data class TrendingResponse(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) {
    data class Result(
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String,
        @SerializedName("genre_ids")
        val genreIds: List<Int>?,
        val id: Long,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        val overview: String,
        val popularity: Double,
        @SerializedName("poster_path")
        val posterPath: String,
        @SerializedName("release_date")
        val releaseDate: String,
        val title: String,
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int
    ) {

        fun getFullPosterPath(): String {
            return "https://image.tmdb.org/t/p/w500$posterPath"
        }

        fun getFullBackdropPath(): String {
            return "https://image.tmdb.org/t/p/w500$backdropPath"
        }

        companion object : MapperData<Result, Media?> {

            override fun toDomain(data: Result): Media? {
                with(data) {
                    return if (mediaType != "movie") null
                    else
                        Media(
                            id = data.id,
                            title = title,
                            posterPath = data.getFullPosterPath(),
                            backdropPath = data.getFullBackdropPath(),
                            type = data.mediaType,
                            rating = data.voteAverage,
                            releaseDate = data.releaseDate,
                            description = data.overview
                        )
                }

            }
        }
    }
}