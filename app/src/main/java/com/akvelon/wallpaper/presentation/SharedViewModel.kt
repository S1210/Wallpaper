package com.akvelon.wallpaper.presentation

import android.app.Application
import com.akvelon.wallpaper.controller.AppController
import com.akvelon.wallpaper.extension.launchWithHandle
import com.akvelon.wallpaper.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    app: Application,
    appController: AppController
) : BaseViewModel(app) {

    init {
        appController.errorHandler.onEach {
            errorEvents(it.throwable, it.isShowError)
        }.launchWithHandle(this)
        appController.appMessage.onEach {
            showErrorDialog(title = it.title, message = it.message)
        }.launchWithHandle(this)
    }

}