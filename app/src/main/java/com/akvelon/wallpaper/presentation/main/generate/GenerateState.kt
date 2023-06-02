package com.akvelon.wallpaper.presentation.main.generate

import android.net.Uri
import com.akvelon.wallpaper.domain.model.Event

data class GenerateState(
    val lastImageUri: Uri? = null,
    val events: List<Event> = emptyList()
)
