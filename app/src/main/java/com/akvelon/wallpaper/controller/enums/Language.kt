package com.akvelon.wallpaper.controller.enums

import androidx.annotation.StringRes
import com.akvelon.wallpaper.R

enum class Language(val code: String, @StringRes val title: Int) {

    ENGLISH(code = "en", title = R.string.english),
    DEUTSCH(code = "de", title = R.string.german),
    FRENCH(code = "fr", title = R.string.french),
    SWEDISH(code = "sv", title = R.string.english),
    PORTUGUESE(code = "pt", title = R.string.portuguese),
    RUSSIAN(code = "ru", title = R.string.russian),
    SPANISH(code = "es", title = R.string.spanish),
    ARABIC(code = "ar", title = R.string.arabic),
    BOSNIAN(code = "bs", title = R.string.bosnian)

}