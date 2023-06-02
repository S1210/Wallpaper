package com.akvelon.wallpaper.presentation.splash

import android.app.Application
import com.akvelon.wallpaper.extension.launch
import com.akvelon.wallpaper.presentation.common.BaseViewModel
import com.akvelon.wallpaper.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(app: Application) : BaseViewModel(app) {

    init {
        launch { popBackStackNavigate(Screen.MainScreen.route) }
    }

}

