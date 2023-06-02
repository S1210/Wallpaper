package com.akvelon.wallpaper.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.data.local.entity.EventEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
abstract class EventDao : CommonDao<EventEntity> {

    @Query("SELECT * FROM events WHERE date = :date")
    abstract fun getEventsFlow(date: LocalDate): Flow<List<EventEntity>>

    @Query("SELECT EXISTS(SELECT * FROM events WHERE date = :date AND language = :language)")
    abstract suspend fun haveEvents(date: LocalDate, language: Language): Boolean

    @Query("DELETE FROM events")
    abstract override suspend fun deleteAll()

}