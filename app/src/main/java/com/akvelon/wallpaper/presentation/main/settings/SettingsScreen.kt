package com.akvelon.wallpaper.presentation.main.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akvelon.wallpaper.R
import com.akvelon.wallpaper.controller.enums.Language
import com.akvelon.wallpaper.controller.enums.UpdateTime
import com.akvelon.wallpaper.extension.collect
import com.akvelon.wallpaper.presentation.common.component.Spinner
import com.akvelon.wallpaper.presentation.common.component.ToolbarScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel(),
    state: SettingsState = viewModel.state.collect()
) {
    ToolbarScreen(
        viewModel = viewModel,
        navController = navController,
        title = stringResource(id = R.string.settings),
        isShowIcon = false
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            Spinner(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.language),
                items = Language.values().toList(),
                selectedOptionText = stringResource(id = state.selectedLanguageTitle),
                itemContent = { Text(text = stringResource(id = it.title)) },
                onClick = viewModel::selectLanguage
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spinner(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.update_time),
                items = UpdateTime.values().toList(),
                selectedOptionText = stringResource(id = state.selectedUpdateTimeTitle),
                itemContent = { Text(text = stringResource(id = it.title)) },
                onClick = viewModel::selectUpdateTime
            )
        }
    }
}