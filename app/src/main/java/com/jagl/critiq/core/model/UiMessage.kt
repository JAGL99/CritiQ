package com.jagl.critiq.core.model

import android.content.Context
import androidx.core.content.ContextCompat

sealed class UiMessage {
    data class Resource(val idMessage: Int) : UiMessage()
    data class Text(val message: String) : UiMessage()
}

fun UiMessage.getMessage(context: Context): String {
    return when (this) {
        is UiMessage.Resource -> ContextCompat.getString(context, idMessage)
        is UiMessage.Text -> message
    }
}