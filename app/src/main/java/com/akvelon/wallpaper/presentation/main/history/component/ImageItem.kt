package com.akvelon.wallpaper.presentation.main.history.component

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.akvelon.wallpaper.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageItem(
    uri: Uri,
    onSetAsWallpaper: (uri: Uri) -> Unit,
    onOpenConfirmDeleteDialog: (uri: Uri) -> Unit,
    onClick: (uri: Uri) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Box(modifier = Modifier
        .size(75.dp)
        .combinedClickable(
            onLongClick = { expanded = true },
            onClick = { onClick(uri) }
        )
    ) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                onSetAsWallpaper(uri)
                expanded = false
            }) {
                Text(text = stringResource(id = R.string.use_as_wallpaper))
            }
            DropdownMenuItem(onClick = {
                onOpenConfirmDeleteDialog(uri)
                expanded = false
            }) {
                Text(text = stringResource(id = R.string.delete))
            }
        }
    }
}