package com.akvelon.wallpaper.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface FileRepository {

    val imagesFlow: Flow<List<Uri>>

    suspend fun saveFile(inputStream: InputStream): String

    suspend fun deleteFile(uri: Uri)

}