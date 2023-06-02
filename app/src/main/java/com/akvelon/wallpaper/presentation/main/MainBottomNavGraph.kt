package com.akvelon.wallpaper.presentation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.akvelon.wallpaper.presentation.main.generate.GenerateScreen
import com.akvelon.wallpaper.presentation.main.history.HistoryScreen
import com.akvelon.wallpaper.presentation.main.settings.SettingsScreen

@Composable
fun MainBottomNavGraph(
    appNavController: NavController,
    navController: NavHostController,
    isShowBottomNav: MutableState<Boolean>,
    paddingValues: PaddingValues
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = MainBottomNavItem.Generate.route
    ) {

        composable(MainBottomNavItem.Generate.route) {
            GenerateScreen(navController = navController)
        }

        composable(MainBottomNavItem.History.route) {
            HistoryScreen(navController = navController, isShowBottomNav = isShowBottomNav)
        }

        composable(MainBottomNavItem.Settings.route) {
            SettingsScreen(navController = navController)
        }

    }
}