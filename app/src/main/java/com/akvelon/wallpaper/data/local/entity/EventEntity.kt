package com.akvelon.wallpaper.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.data.local.TableNames.EVENTS
import java.time.LocalDate

@Entity(tableName = EVENTS)
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    @ColumnInfo(name = DESCRIPTION)
    val description: String = "",
    @ColumnInfo(name = DATE)
    val date: LocalDate = LocalDate.now(),
    @ColumnInfo(name = LANGUAGE, defaultValue = "0")
    val language: Language = Language.ENGLISH
) : RoomModel() {

    companion object {
        const val DESCRIPTION = "description"
        const val DATE = "date"
        const val LANGUAGE = "language"
    }

}
