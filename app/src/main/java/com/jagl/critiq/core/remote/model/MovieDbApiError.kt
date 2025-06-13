package com.jagl.critiq.core.remote.model

import com.google.gson.annotations.SerializedName

data class MovieDbApiError(
    val success: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String
)
