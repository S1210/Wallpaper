package com.akvelon.wallpaper.data.local.converter

import androidx.room.TypeConverter
import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.extension.enumToOrdinal
import com.akvelon.wallpaper.extension.ordinalToEnum
import java.time.LocalDate

object TypeConverter {

    @TypeConverter
    fun localDateToString(date: LocalDate?) = date?.toString()

    @TypeConverter
    fun stringToLocalDate(date: String?) = date?.let { LocalDate.parse(it) }

    @TypeConverter
    fun languageToOrdinal(language: Language) = enumToOrdinal(language)

    @TypeConverter
    fun ordinalToLanguage(language: Int) = ordinalToEnum(Language.values(), language)

}