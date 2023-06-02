package com.akvelon.wallpaper.presentation.main

import android.Manifest
import android.os.Build
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.akvelon.wallpaper.extension.collect
import com.akvelon.wallpaper.presentation.common.component.ViewModelScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    state: MainState = viewModel.state.collect()
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permissionsState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
        LaunchedEffect(key1 = Unit) {
            if (!permissionsState.status.isGranted && !permissionsState.status.shouldShowRationale) {
                permissionsState.launchPermissionRequest()
            }
        }
    }
    ViewModelScreen(viewModel = viewModel, navController = navController) {
        val bottomNavController = rememberNavController()
        val isShowBottomNav = rememberSaveable { mutableStateOf(true) }
        Scaffold(
            bottomBar = {
                MainBottomNavigation(
                    navController = bottomNavController,
                    isShowBottomNav = isShowBottomNav.value
                )
            }
        ) {
            MainBottomNavGraph(
                appNavController = navController,
                navController = bottomNavController,
                isShowBottomNav = isShowBottomNav,
                paddingValues = it
            )
        }
    }
}