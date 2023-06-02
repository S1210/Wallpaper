package com.akvelon.wallpaper.presentation.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.akvelon.wallpaper.R

sealed class MainBottomNavItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {

    companion object {
        fun isMainRoute(route: String?): Boolean {
            return route == Generate.route || route == History.route || route == Settings.route
        }
    }

    object Generate : MainBottomNavItem(
        R.string.generate,
        Icons.Default.Image,
        "generate"
    )

    object History : MainBottomNavItem(
        R.string.history,
        Icons.Default.History,
        "history"
    )

    object Settings : MainBottomNavItem(
        R.string.settings,
        Icons.Default.Settings,
        "settings"
    )

}
