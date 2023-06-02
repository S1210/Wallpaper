package com.akvelon.wallpaper.extension

import com.akvelon.wallpaper.controller.AppController
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

suspend fun <T> withMain(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Main, block)

suspend fun <T> withIO(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.IO, block)

suspend fun <T> withDefault(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Default, block)

fun <T> tryCatch(catchBlock: (() -> T)? = null, tryBlock: () -> T): T? {
    return try {
        tryBlock()
    } catch (e: Exception) {
        catchBlock?.let { it() }
    }
}

fun CoroutineScope.launchMain(
    appController: AppController,
    isShowError: Boolean = true,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launchHandle(
    appController = appController,
    isShowError = isShowError,
    context = Dispatchers.Main,
    start = start,
    block = block
)

fun CoroutineScope.launchIo(
    appController: AppController,
    isShowError: Boolean = true,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launchHandle(
    appController = appController,
    isShowError = isShowError,
    context = Dispatchers.IO,
    start = start,
    block = block
)

fun CoroutineScope.launchDefault(
    appController: AppController,
    isShowError: Boolean = true,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launchHandle(
    appController = appController,
    isShowError = isShowError,
    context = Dispatchers.Default,
    start = start,
    block = block
)

private fun CoroutineScope.launchHandle(
    appController: AppController,
    isShowError: Boolean,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launch(context = context, start = start) {
    try {
        block()
    } catch (e: Exception) {
        if (e !is CancellationException && isShowError) appController.sendError(e)
    }
}
