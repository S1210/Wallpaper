package com.akvelon.wallpaper.data.repository

import android.app.Application
import android.net.Uri
import com.akvelon.wallpaper.data.remote.api.ApiManager
import com.akvelon.wallpaper.domain.repository.FileRepository
import com.akvelon.wallpaper.domain.repository.ImageRepository
import com.akvelon.wallpaper.domain.repository.WallpaperRepository
import com.akvelon.wallpaper.extension.getImagesDirectory
import com.akvelon.wallpaper.extension.withIO
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val app: Application,
    private val apiManager: ApiManager,
    private val wallpaperRepository: WallpaperRepository,
    private val fileRepository: FileRepository
) : ImageRepository {

    override suspend fun loadImage(): Unit = withIO {
        apiManager.getImage().byteStream().also { stream ->
            val file = File(app.getImagesDirectory(), fileRepository.saveFile(stream))
            wallpaperRepository.setAsWallpaper(
                Uri.fromFile(file).toString(),
                file.inputStream()
            )
        }
    }

}