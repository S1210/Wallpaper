package com.akvelon.wallpaper.data.remote.dto.event

data class Selected(
    val pages: List<Page>,
    val text: String,
    val year: Int
)