package com.jagl.critiq.core.remote.data

import com.google.gson.annotations.SerializedName
import com.jagl.critiq.core.utils.mappers.MapperData
import com.jagl.critiq.domain.data.MediaDomain

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
        @SerializedName("first_air_date")
        val firstAirDate: String,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        val id: Int,
        @SerializedName("media_type")
        val mediaType: String,
        val name: String,
        @SerializedName("origin_country")
        val originCountry: List<String>,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_name")
        val originalName: String,
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

        fun getCorrectName(): String {
            return if (mediaType == "movie") {
                title
            } else {
                name
            }
        }

        fun getFullPosterPath(): String {
            return "https://image.tmdb.org/t/p/w500$posterPath"
        }

        fun getFullBackdropPath(): String {
            return "https://image.tmdb.org/t/p/w500$backdropPath"
        }

        companion object : MapperData<Result, MediaDomain> {

            override fun toDomain(data: Result): MediaDomain {
                val name = data.getCorrectName()
                return MediaDomain(
                    id = data.id,
                    title = name,
                    posterPath = data.getFullPosterPath(),
                    backdropPath = data.getFullBackdropPath(),
                    type = data.mediaType,
                    rating = data.voteAverage,
                    releaseDate = if (data.mediaType == "movie") {
                        data.releaseDate
                    } else {
                        data.firstAirDate
                    },
                    description = data.overview,
                    page = -1
                )
            }
        }
    }
}