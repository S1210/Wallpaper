package com.akvelon.wallpaper.data.repository

import android.app.Application
import android.net.Uri
import android.os.FileObserver
import com.akvelon.wallpaper.domain.repository.FileRepository
import com.akvelon.wallpaper.extension.getImagesDirectory
import com.akvelon.wallpaper.extension.withIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val app: Application
) : FileRepository {

    override val imagesFlow: Flow<List<Uri>>
        get() = callbackFlow {
            val callback = object : FileObserver(app.getImagesDirectory()) {
                override fun onEvent(event: Int, path: String?) {
                    trySend(getImageUris())
                }
            }
            callback.startWatching()
            trySend(getImageUris())
            awaitClose { callback.stopWatching() }
        }.flowOn(Dispatchers.IO)

    override suspend fun saveFile(inputStream: InputStream): String = withIO {
        "${
            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME).replace(':', '-')
        }.jpg".also { name ->
            app.getImagesDirectory().run {
                if (!exists()) mkdirs()
                FileOutputStream(File(this, name)).use { stream ->
                    stream.write(inputStream.readBytes())
                }
            }
        }
    }

    override suspend fun deleteFile(uri: Uri): Unit = withIO {
        uri.path?.let { File(it).delete() }
    }

    private fun getImageUris() = app.getImagesDirectory()
        .listFiles()
        ?.filter { it.isFile }
        ?.map { Uri.fromFile(it) }
        ?: emptyList()

}