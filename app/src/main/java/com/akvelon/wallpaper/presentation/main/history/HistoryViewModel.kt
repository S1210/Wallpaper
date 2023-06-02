package com.akvelon.wallpaper.presentation.main.history

import android.app.Application
import android.net.Uri
import com.akvelon.wallpaper.domain.repository.FileRepository
import com.akvelon.wallpaper.domain.repository.WallpaperRepository
import com.akvelon.wallpaper.extension.launchIO
import com.akvelon.wallpaper.extension.launchWithHandle
import com.akvelon.wallpaper.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    app: Application,
    private val fileRepository: FileRepository,
    private val wallpaperRepository: WallpaperRepository
) : BaseViewModel(app) {

    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()
    private var uri: Uri? = null

    init {
        launchIO {
            fileRepository.imagesFlow.onEach {
                _state.update { state -> state.copy(images = it) }
            }.launchWithHandle(this)
        }
    }

    fun setAsWallpaper(uri: Uri) = launchIO {
        uri.path?.let { wallpaperRepository.setAsWallpaper(it, File(it).inputStream()) }
    }

    fun openConfirmDeleteDialog(uri: Uri) {
        _state.update { state -> state.copy(isShowConfirmDeleteDialog = true) }
        this.uri = uri
    }

    fun closeConfirmDeleteDialog() {
        _state.update { state -> state.copy(isShowConfirmDeleteDialog = false) }
        uri = null
    }

    fun enablePresentationMode(uri: Uri) {
        _state.update { state ->
            state.copy(
                isPresentationModeEnabled = true,
                initialPage = state.images.indexOf(uri)
            )
        }
    }

    fun disablePresentationMode() {
        _state.update { state -> state.copy(isPresentationModeEnabled = false, initialPage = 0) }
    }

    fun deleteFile() = launchIO {
        uri?.let { fileRepository.deleteFile(it) }
        uri = null
        closeConfirmDeleteDialog()
    }

}