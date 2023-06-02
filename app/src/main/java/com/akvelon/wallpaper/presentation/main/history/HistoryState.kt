package com.akvelon.wallpaper.presentation.main.history

import android.net.Uri

data class HistoryState(
    val images: List<Uri> = emptyList(),
    val isShowConfirmDeleteDialog: Boolean = false,
    val isPresentationModeEnabled: Boolean = false,
    val initialPage: Int = 0
)
