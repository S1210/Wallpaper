package com.akvelon.wallpaper.data.remote.api

import android.util.Size
import android.view.WindowManager
import com.akvelon.wallpaper.controller.AppState
import com.akvelon.wallpaper.data.remote.dto.event.ResponseEvent
import com.akvelon.wallpaper.extension.getScreenSize
import com.akvelon.wallpaper.extension.withIO
import okhttp3.ResponseBody
import retrofit2.Response
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiManager @Inject constructor(
    private val eventApi: IEventApi,
    private val imageApi: IImageApi,
    private val appState: AppState,
    private val windowManager: WindowManager
) {

    suspend fun getEvents(date: LocalDate): Response<ResponseEvent> = withIO {
        eventApi.getEvents(
            appState.getLanguage().code,
            String.format("%02d", date.monthValue),
            String.format("%02d", date.dayOfMonth)
        )
    }

    suspend fun getImage(size: Size = windowManager.getScreenSize()): ResponseBody = withIO {
        imageApi.getImage(
            size.width.toString(),
            size.height.toString()
        )
    }

}