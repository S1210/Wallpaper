package com.akvelon.wallpaper.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.akvelon.wallpaper.presentation.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.launchWithHandle(
    viewModel: BaseViewModel,
    onStartEvent: suspend FlowCollector<T>.() -> Unit = {}
) = onStart { onStartEvent() }.launchWithHandle(viewModel)

private fun <T> Flow<T>.launchWithHandle(viewModel: BaseViewModel) = viewModel.launchIO {
    catch { viewModel.errorEvents(it) }.flowOn(Dispatchers.IO).collect()
}

@Composable
fun <T> Flow<T>.collect(defValue: T) = collectAsState(initial = defValue).value

@Composable
fun <T> StateFlow<T>.collect() = collectAsState().value