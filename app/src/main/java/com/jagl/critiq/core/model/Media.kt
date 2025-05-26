package com.jagl.critiq.core.model

data class Media(
    val id: Long,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val type: String,
    val rating: Double,
    val releaseDate: String,
    val description: String
)