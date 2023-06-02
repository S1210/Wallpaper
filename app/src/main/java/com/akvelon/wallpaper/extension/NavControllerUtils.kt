package com.akvelon.wallpaper.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController

@Composable
fun <T> NavController.getStateValue(tag: String, defValue: T) =
    currentBackStackEntry?.savedStateHandle?.getStateFlow(tag, defValue)?.collectAsState(defValue)

fun NavController.isStartDestination() = try {
    graph.startDestinationRoute == currentDestination?.route
} catch (e: Exception) {
    true
}

fun NavController.navigateIfPossible(route: String) {
    tryCatch { navigate(route) }
}
