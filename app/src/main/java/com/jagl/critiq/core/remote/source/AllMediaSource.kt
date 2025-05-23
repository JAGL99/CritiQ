package com.jagl.critiq.core.remote.source

import com.jagl.critiq.domain.data.MediaDomain
import kotlinx.coroutines.flow.Flow

fun interface AllMediaSource {

    fun getTrendings(page: Int): Flow<List<MediaDomain>>

}