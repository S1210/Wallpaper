package com.akvelon.wallpaper.presentation.util

sealed class Screen(val route: String) {

    object SplashScreen : Screen("splash_screen")

    object MainScreen : Screen("main_screen")

}
