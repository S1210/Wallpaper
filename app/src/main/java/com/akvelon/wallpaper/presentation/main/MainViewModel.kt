package com.akvelon.wallpaper.presentation.main

import android.app.Application
import android.app.WallpaperManager
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.akvelon.wallpaper.data.remote.api.ApiManager
import com.akvelon.wallpaper.controller.AppState
import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.controller.enums.UpdateTime
import com.akvelon.wallpaper.controller.worker.WorkerController
import com.akvelon.wallpaper.domain.repository.EventRepository
import com.akvelon.wallpaper.extension.launchIO
import com.akvelon.wallpaper.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application
) : BaseViewModel(app) {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()


}