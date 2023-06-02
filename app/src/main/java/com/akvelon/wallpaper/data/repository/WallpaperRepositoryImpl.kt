package com.akvelon.wallpaper.data.repository

import android.app.WallpaperManager
import com.akvelon.wallpaper.controller.AppState
import com.akvelon.wallpaper.domain.repository.WallpaperRepository
import com.akvelon.wallpaper.extension.withIO
import java.io.InputStream
import javax.inject.Inject

class WallpaperRepositoryImpl @Inject constructor(
    private val wallpaperManager: WallpaperManager,
    private val appState: AppState
) : WallpaperRepository {

    override suspend fun setAsWallpaper(uri: String, inputStream: InputStream): Unit = withIO {
        wallpaperManager.setStream(inputStream)
        appState.setLastImageUri(uri)
    }

}