package com.akvelon.wallpaper.presentation.main

import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.controller.enums.UpdateTime

data class MainState(
    val selectedLanguage: Language = Language.ENGLISH,
    val selectedUpdateTime: UpdateTime = UpdateTime.ONE_DAY
)
