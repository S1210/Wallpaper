package com.akvelon.wallpaper.presentation.main.generate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.akvelon.wallpaper.R
import com.akvelon.wallpaper.extension.collect
import com.akvelon.wallpaper.presentation.common.component.Headline
import com.akvelon.wallpaper.presentation.common.component.TextUpperCase
import com.akvelon.wallpaper.presentation.common.component.ToolbarScreen
import com.akvelon.wallpaper.presentation.main.generate.component.EventItem

@Composable
fun GenerateScreen(
    navController: NavController,
    viewModel: GenerateViewModel = hiltViewModel(),
    state: GenerateState = viewModel.state.collect()
) {
    ToolbarScreen(
        viewModel = viewModel,
        navController = navController,
        title = stringResource(id = R.string.generate),
        isShowIcon = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.lastImageUri != null) {
                AsyncImage(
                    modifier = Modifier.weight(1f),
                    model = state.lastImageUri,
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Headline(text = stringResource(id = R.string.today_events))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp)
                        .weight(1f)
                ) {
                    items(state.events) {
                        EventItem(event = it, onClick = viewModel::generate)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = viewModel::getEvents) {
                        TextUpperCase(text = stringResource(id = R.string.get_events))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = viewModel::generate) {
                        TextUpperCase(text = stringResource(id = R.string.generate))
                    }
                }
            }
        }
    }
}