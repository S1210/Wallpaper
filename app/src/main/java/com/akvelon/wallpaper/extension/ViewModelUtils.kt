package com.akvelon.wallpaper.extension

import androidx.lifecycle.viewModelScope
import com.akvelon.wallpaper.presentation.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun BaseViewModel.launch(isShowError: Boolean = true, lambda: suspend () -> Unit) =
    viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
        sendReport(isShowError, lambda)
    }

fun BaseViewModel.launchIO(isShowError: Boolean = true, lambda: suspend () -> Unit) =
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        sendReport(isShowError, lambda)
    }

fun BaseViewModel.launchDefault(isShowError: Boolean = true, lambda: suspend () -> Unit) =
    viewModelScope.launch(Dispatchers.Default + exceptionHandler) {
        sendReport(isShowError, lambda)
    }

fun <T> BaseViewModel.async(isShowError: Boolean = true, lambda: suspend () -> T) =
    viewModelScope.async { sendReportAsync(isShowError, lambda) }

fun <T> BaseViewModel.asyncIO(isShowError: Boolean = true, lambda: suspend () -> T) =
    viewModelScope.async(context = Dispatchers.IO) { sendReportAsync(isShowError, lambda) }

fun <T> BaseViewModel.asyncDefault(
    isShowError: Boolean = true,
    lambda: suspend () -> T
) = viewModelScope.async(context = Dispatchers.Default) { sendReportAsync(isShowError, lambda) }

private suspend fun BaseViewModel.sendReport(
    isShowError: Boolean,
    lambda: suspend () -> Unit
) {
    try {
        lambda()
    } catch (e: Exception) {
        if (isShowError) errorEvents(e)
    }
}

private suspend fun <T> BaseViewModel.sendReportAsync(
    isShowError: Boolean,
    lambda: suspend () -> T
): T {
    try {
        return lambda()
    } catch (e: Exception) {
        if (isShowError) errorEvents(e)
        throw e
    }
}





