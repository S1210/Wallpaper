package com.akvelon.wallpaper.domain.repository

import java.io.InputStream

interface WallpaperRepository {

    suspend fun setAsWallpaper(uri: String, inputStream: InputStream)

}