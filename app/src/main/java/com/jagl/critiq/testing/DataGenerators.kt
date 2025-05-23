package com.jagl.critiq.testing

import com.jagl.critiq.domain.data.MediaDomain

fun mediaDomain(): MediaDomain {
    return MediaDomain(
        id = 1,
        title = "Test Title",
        posterPath = "https://example.com/poster.jpg",
        backdropPath = "https://example.com/backdrop.jpg",
        type = "movie",
        rating = 8.5,
        releaseDate = "2023-01-01",
        description = "This is a test description.",
        page = 1
    )
}

