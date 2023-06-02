package com.akvelon.wallpaper.presentation.main.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akvelon.wallpaper.R
import com.akvelon.wallpaper.extension.collect
import com.akvelon.wallpaper.presentation.common.component.ToolbarScreen
import com.akvelon.wallpaper.presentation.common.dialog.AlertDialog
import com.akvelon.wallpaper.presentation.main.history.component.ImageItem
import com.akvelon.wallpaper.presentation.main.history.component.ImagePager

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    isShowBottomNav: MutableState<Boolean>,
    viewModel: HistoryViewModel = hiltViewModel(),
    state: HistoryState = viewModel.state.collect()
) {
    AlertDialog(
        isShow = state.isShowConfirmDeleteDialog,
        title = stringResource(id = R.string.delete),
        text = stringResource(id = R.string.delete_image_message),
        cancelButtonTextRes = R.string.no,
        okButtonTextRes = R.string.yes,
        onCancelClick = viewModel::closeConfirmDeleteDialog,
        onOkClick = viewModel::deleteFile
    )
    ToolbarScreen(
        viewModel = viewModel,
        navController = navController,
        title = stringResource(id = R.string.history),
        isShowIcon = state.isPresentationModeEnabled,
        icon = Icons.Default.Close,
        iconClick = {
            if (state.isPresentationModeEnabled) {
                viewModel.disablePresentationMode()
            } else {
                navController.popBackStack()
            }
        }
    ) {
        isShowBottomNav.value = !state.isPresentationModeEnabled
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    columns = StaggeredGridCells.Adaptive(75.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalItemSpacing = 4.dp
                ) {
                    items(state.images) {
                        ImageItem(
                            uri = it,
                            onSetAsWallpaper = viewModel::setAsWallpaper,
                            onOpenConfirmDeleteDialog = viewModel::openConfirmDeleteDialog,
                            onClick = viewModel::enablePresentationMode
                        )
                    }
                }
            }
            AnimatedVisibility(visible = state.isPresentationModeEnabled) {
                ImagePager(
                    images = state.images,
                    initialPage = state.initialPage,
                    onClose = viewModel::disablePresentationMode,
                    onSetAsWallpaper = viewModel::setAsWallpaper,
                    onOpenConfirmDeleteDialog = viewModel::openConfirmDeleteDialog
                )
            }
        }
    }
}