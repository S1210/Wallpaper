package com.akvelon.wallpaper.domain.model

import com.akvelon.wallpaper.controller.enums.Language
import java.time.LocalDate

data class Event(
    val id: Long,
    val description: String,
    val date: LocalDate,
    val language: Language
)
