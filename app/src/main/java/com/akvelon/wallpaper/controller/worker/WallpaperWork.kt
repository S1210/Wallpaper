package com.akvelon.wallpaper.controller.worker

import android.app.WallpaperManager
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.akvelon.wallpaper.R
import com.akvelon.wallpaper.domain.repository.EventRepository
import com.akvelon.wallpaper.extension.withIO
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WallpaperWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val eventRepository: EventRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withIO {
        try {
            eventRepository.loadEvents()
            Result.retry()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}