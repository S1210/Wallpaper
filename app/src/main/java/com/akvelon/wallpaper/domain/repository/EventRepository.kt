package com.akvelon.wallpaper.domain.repository

import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.data.local.entity.EventEntity
import com.akvelon.wallpaper.domain.model.Event
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EventRepository : CommonRepository<EventEntity> {

    fun getEventsFlow(): Flow<List<Event>>

    suspend fun haveEvents(date: LocalDate, language: Language): Boolean

    suspend fun loadEvents(date: LocalDate = LocalDate.now(), isLoadImages: Boolean = true)

}