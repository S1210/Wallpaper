package com.akvelon.wallpaper.presentation.main.history.component

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.akvelon.wallpaper.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(
    images: List<Uri>,
    initialPage: Int,
    onClose: () -> Unit,
    onSetAsWallpaper: (uri: Uri) -> Unit,
    onOpenConfirmDeleteDialog: (uri: Uri) -> Unit
) {
    BackHandler { onClose() }
    val pagerState = rememberPagerState(initialPage = initialPage)
    val configuration = LocalConfiguration.current
    HorizontalPager(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        pageCount = images.size,
        state = pagerState
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val uri = images[it]
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                model = uri,
                contentDescription = null,
                contentScale = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    ContentScale.FillBounds
                } else {
                    ContentScale.FillHeight
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Center
            ) {
                IconButton(onClick = { onSetAsWallpaper(uri) }) {
                    Icon(
                        imageVector = Icons.Default.Wallpaper,
                        contentDescription = stringResource(id = R.string.use_as_wallpaper),
                        tint = Color.White
                    )
                }
                IconButton(onClick = { onOpenConfirmDeleteDialog(uri) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete),
                        tint = Color.White
                    )
                }
            }
        }
    }
}