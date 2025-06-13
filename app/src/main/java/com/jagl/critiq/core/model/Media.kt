package com.jagl.critiq.core.model

data class Media(
    val id: Long,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val type: String,
    val rating: Double,
    val language: String,
    val releaseDate: String,
    val description: String,
    val isFavorite: Boolean = false
)