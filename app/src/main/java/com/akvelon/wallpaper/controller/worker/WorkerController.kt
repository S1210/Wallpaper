package com.akvelon.wallpaper.controller.worker

import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.akvelon.wallpaper.controller.AppState
import com.akvelon.wallpaper.extension.withIO
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerController @Inject constructor(
    private val workManager: WorkManager,
    private val appState: AppState
) {

    suspend fun installWallpaperWorker() = withIO {
        val updateTime = appState.getUpdateTime()
        LocalDateTime.now()
            .plusMinutes(updateTime.internal)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
            .also {
                PeriodicWorkRequestBuilder<WallpaperWork>(
                    updateTime.internal,
                    TimeUnit.MINUTES
                ).setInitialDelay(
                    it - System.currentTimeMillis(),
                    TimeUnit.MILLISECONDS
                ).build().also { request ->
                    cancelWorker()
                    appState.setWorkId(request.id)
                    workManager.enqueue(request)
                }
            }
    }

    suspend fun cancelWorker() = withIO {
        appState.getWorkId()?.let { uuid -> workManager.cancelWorkById(uuid) }
    }

    suspend fun reloadWorker() = withIO {
        cancelWorker()
        installWallpaperWorker()
    }

}