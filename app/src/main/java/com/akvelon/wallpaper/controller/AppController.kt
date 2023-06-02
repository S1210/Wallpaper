package com.akvelon.wallpaper.controller

import com.akvelon.wallpaper.presentation.common.dialog.error.ErrorData
import com.akvelon.wallpaper.presentation.common.dialog.error.MessageData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppController @Inject constructor() {

    private val _errorHandler = Channel<ErrorData>()
    val errorHandler = _errorHandler.receiveAsFlow()

    private val _appMessage = Channel<MessageData>()
    val appMessage = _appMessage.receiveAsFlow()

    fun sendError(throwable: Throwable, isShowError: Boolean = true) {
        _errorHandler.trySend(ErrorData(throwable, isShowError))
    }

    fun sendMessage(title: String = "", message: String) {
        _appMessage.trySend(MessageData(title, message))
    }

}