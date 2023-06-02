package com.akvelon.wallpaper.presentation.main.settings

import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.controller.enums.UpdateTime

data class SettingsState(
    val selectedLanguage: Language = Language.ENGLISH,
    val selectedUpdateTime: UpdateTime = UpdateTime.ONE_DAY
) {

    val selectedLanguageTitle: Int
        get() = selectedLanguage.title

    val selectedUpdateTimeTitle: Int
        get() = selectedUpdateTime.title

}
