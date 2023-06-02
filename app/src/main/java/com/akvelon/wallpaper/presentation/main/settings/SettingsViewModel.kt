package com.akvelon.wallpaper.presentation.main.settings

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.akvelon.wallpaper.controller.AppState
import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.controller.enums.UpdateTime
import com.akvelon.wallpaper.controller.worker.WorkerController
import com.akvelon.wallpaper.domain.repository.EventRepository
import com.akvelon.wallpaper.extension.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import com.akvelon.wallpaper.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    app: Application,
    private val workerController: WorkerController,
    private val appState: AppState
) : BaseViewModel(app) {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        launchIO {
            appState.settingsPreferences.onEach {
                _state.update { state ->
                    state.copy(
                        selectedLanguage = it.language,
                        selectedUpdateTime = it.updateTime
                    )
                }
            }.launchIn(viewModelScope)
        }
    }

    fun selectLanguage(language: Language) = launchIO {
        appState.setLanguage(language)
    }

    fun selectUpdateTime(updateTime: UpdateTime) = launchIO {
        appState.setUpdateTime(updateTime)
        workerController.reloadWorker()
    }

}