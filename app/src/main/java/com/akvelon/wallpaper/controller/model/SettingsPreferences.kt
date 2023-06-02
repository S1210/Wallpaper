package com.akvelon.wallpaper.controller.model

import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.controller.enums.UpdateTime

data class SettingsPreferences(
    val language: Language,
    val updateTime: UpdateTime
)
