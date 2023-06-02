package com.akvelon.wallpaper.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akvelon.wallpaper.R
import com.akvelon.wallpaper.presentation.common.component.ViewModelScreen
import com.akvelon.wallpaper.presentation.main.MainScreen
import com.akvelon.wallpaper.presentation.splash.SplashScreen
import com.akvelon.wallpaper.presentation.util.Screen
import com.akvelon.wallpaper.ui.theme.WallpaperTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Wallpaper)
        setContent {
            val viewModel = hiltViewModel<SharedViewModel>()
            WallpaperTheme {
                Surface {
                    val navController = rememberNavController()
                    ViewModelScreen(viewModel = viewModel, navController = navController) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.SplashScreen.route
                        ) {
                            composable(route = Screen.SplashScreen.route) {
                                SplashScreen(navController = navController)
                            }
                            composable(route = Screen.MainScreen.route) {
                                MainScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}