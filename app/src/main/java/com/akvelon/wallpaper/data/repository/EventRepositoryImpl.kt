package com.akvelon.wallpaper.data.repository

import com.akvelon.wallpaper.data.remote.api.ApiManager
import com.akvelon.wallpaper.controller.AppController
import com.akvelon.wallpaper.controller.AppState
import com.akvelon.wallpaper.controller.TimeController
import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.data.local.dao.EventDao
import com.akvelon.wallpaper.data.local.entity.EventEntity
import com.akvelon.wallpaper.domain.model.Event
import com.akvelon.wallpaper.domain.repository.EventRepository
import com.akvelon.wallpaper.domain.repository.ImageRepository
import com.akvelon.wallpaper.extension.withIO
import com.akvelon.wallpaper.presentation.util.toEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class EventRepositoryImpl @Inject constructor(
    private val eventDao: EventDao,
    private val apiManager: ApiManager,
    private val appController: AppController,
    private val appState: AppState,
    private val imageRepository: ImageRepository,
    private val timeController: TimeController
) : CommonRepositoryImpl<EventEntity, EventDao>(eventDao), EventRepository {

    override fun getEventsFlow(): Flow<List<Event>> {
        return timeController.date.flatMapLatest { eventDao.getEventsFlow(it) }.map { entities ->
            entities.map { it.toEvent() }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun haveEvents(date: LocalDate, language: Language): Boolean = withIO {
        eventDao.haveEvents(date, language)
    }

    override suspend fun loadEvents(date: LocalDate, isLoadImages: Boolean): Unit = withIO {
        apiManager.getEvents(date = date).run {
            if (isSuccessful) {
                val language = appState.getLanguage()
                if (!haveEvents(date, language)) {
                    eventDao.upsertItems(
                        body()?.selected?.map {
                            EventEntity(
                                description = it.text,
                                date = date,
                                language = language
                            )
                        } ?: emptyList()
                    )
                }
                if (isLoadImages) imageRepository.loadImage()
            } else {
                appController.sendError(throwable = Exception(errorBody()?.string()))
            }
        }
    }

}