package com.jagl.critiq.core.test

import androidx.paging.PagingSource
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.model.Media
import com.jagl.critiq.core.remote.model.MovieDbApiError

fun media(): Media {
    return Media(
        id = 1,
        title = "Test Title",
        posterPath = "https://example.com/poster.jpg",
        backdropPath = "https://example.com/backdrop.jpg",
        type = "movie",
        rating = 8.5,
        releaseDate = "2023-01-01",
        description = "This is a test description."
    )
}

fun mediaEntity(): MediaEntity {
    return MediaEntity.toEntity(media())
}

fun mediaPairPage(page: Int): Pair<Int, List<Media>> {
    return Pair(
        page,
        (1..5).map {
            media().copy(
                id = (it + (page - 1) * 5).toLong(),
                title = "Test Title $it - Page $page",
                posterPath = "https://example.com/poster_$it.jpg",
                backdropPath = "https://example.com/backdrop_$it.jpg"
            )
        }
    )
}

fun movieDbApiError(): MovieDbApiError {
    return MovieDbApiError(
        statusCode = 22,
        statusMessage = "Invalid page: Pages start at 1 and max at 500. They are expected to be an integer.",
        success = false
    )
}

fun listOfLanguagePairMediaWithPage(): List<Pair<String, List<Pair<Int, List<Media>>>>> {
    return listOf(
        languagePairMediaWithPage("en-US", 1),
        languagePairMediaWithPage("es-ES", 2),
        languagePairMediaWithPage("fr-FR", 3),
        languagePairMediaWithPage("de-DE", 4),
        languagePairMediaWithPage("it-IT", 5)
    )
}

fun languagePairMediaWithPage(
    language: String,
    pages: Int
): Pair<String, List<Pair<Int, List<Media>>>> {
    val mutableList = mutableListOf<Pair<Int, List<Media>>>()
    repeat(pages) {
        mutableList.add(mediaPairPage(it + 1))
    }
    return Pair(
        language,
        mutableList
    )
}

fun paginSourceRefresh(): PagingSource.LoadParams.Refresh<Int> {
    return PagingSource.LoadParams.Refresh(
        key = null,
        loadSize = 0,
        placeholdersEnabled = false
    )
}

