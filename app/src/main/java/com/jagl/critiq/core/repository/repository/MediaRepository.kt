package com.jagl.critiq.core.repository.repository

import com.jagl.critiq.core.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    fun getMediaByPageAndLanguage(page: Int?, language: String?): Flow<List<Media>>
}