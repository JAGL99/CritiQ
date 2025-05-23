package com.jagl.critiq.domain.data

data class MediaDomain(
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val type: String,
    val rating: Double,
    val releaseDate: String,
    val description: String,
    val page: Int
) : Domain