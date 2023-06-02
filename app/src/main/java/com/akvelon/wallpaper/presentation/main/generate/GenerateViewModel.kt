package com.akvelon.wallpaper.presentation.main.generate

import android.app.Application
import com.akvelon.wallpaper.controller.AppState
import com.akvelon.wallpaper.controller.worker.WorkerController
import com.akvelon.wallpaper.domain.model.Event
import com.akvelon.wallpaper.domain.repository.EventRepository
import com.akvelon.wallpaper.domain.repository.ImageRepository
import com.akvelon.wallpaper.extension.launchIO
import com.akvelon.wallpaper.extension.launchWithHandle
import com.akvelon.wallpaper.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GenerateViewModel @Inject constructor(
    app: Application,
    appState: AppState,
    private val eventRepository: EventRepository,
    private val workerController: WorkerController,
    private val imageRepository: ImageRepository
) : BaseViewModel(app) {

    private val _state = MutableStateFlow(GenerateState())
    val state = _state.asStateFlow()

    init {
        combine(
            appState.generatePreferences,
            eventRepository.getEventsFlow()
        ) { preferences, events ->
            _state.update { state ->
                state.copy(
                    lastImageUri = preferences.lastImageUri,
                    events = events
                )
            }
        }.launchWithHandle(this)
    }

    fun getEvents() = launchIO {
        eventRepository.loadEvents(isLoadImages = false)
    }

    fun generate() = launchIO {
        eventRepository.loadEvents()
        workerController.installWallpaperWorker()
    }

    fun generate(event: Event) = launchIO {
        imageRepository.loadImage()
        workerController.installWallpaperWorker()
    }

}