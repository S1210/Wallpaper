package com.akvelon.wallpaper.presentation.common.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.UiComposable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import com.akvelon.wallpaper.extension.collect
import com.akvelon.wallpaper.extension.navigateIfPossible
import com.akvelon.wallpaper.presentation.common.BaseViewModel
import com.akvelon.wallpaper.presentation.common.dialog.error.ErrorDialog
import com.akvelon.wallpaper.presentation.common.dialog.error.ErrorState
import com.akvelon.wallpaper.presentation.common.dialog.progress_bar.ProgressBarDialog
import com.akvelon.wallpaper.presentation.common.dialog.progress_bar.ProgressDialogState
import com.akvelon.wallpaper.presentation.common.function.OnLifecycleEvent
import com.akvelon.wallpaper.presentation.navigation.NavigationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun <V : BaseViewModel> ViewModelScreen(
    viewModel: V,
    navController: NavController,
    parentNavController: NavController? = null,
    progressDialogState: ProgressDialogState = viewModel.progressDialogFlow.collect(),
    errorState: ErrorState = viewModel.errorFlow.collect(),
    content: @Composable @UiComposable () -> Unit
) {
    val context = LocalContext.current
    ProgressBarDialog(state = progressDialogState)
    ErrorDialog(
        onOkClick = { viewModel.closeErrorDialog() },
        state = errorState
    )
    OnLifecycleEvent { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            viewModel.set24Hours()
        }
    }
    LaunchedEffect(key1 = Unit) {
        launch(Dispatchers.Main) {
            viewModel.navigation.collect {
                when (it) {
                    is NavigationState.PopBackStack -> {
                        if (it.isParent && parentNavController != null) parentNavController.popBackStack()
                        else navController.popBackStack()
                    }
                    is NavigationState.PopBackStackNavigate -> {
                        if (it.isParent && parentNavController != null) {
                            parentNavController.popBackStack()
                            parentNavController.navigateIfPossible(it.route)
                        } else {
                            navController.popBackStack()
                            navController.navigateIfPossible(it.route)
                        }
                    }
                    is NavigationState.PopBackStackRoute -> {
                        if (it.isParent && parentNavController != null) {
                            parentNavController.popBackStack(it.popBackRoute, it.inclusive)
                            it.navigate?.let { navigate ->
                                parentNavController.navigateIfPossible(navigate.route)
                            }
                        } else {
                            navController.popBackStack(it.popBackRoute, it.inclusive)
                            it.navigate?.let { navigate ->
                                navController.navigateIfPossible(navigate.route)
                            }
                        }
                    }
                    is NavigationState.Navigate -> {
                        if (it.isParent && parentNavController != null) {
                            parentNavController.navigateIfPossible(it.route)
                        } else {
                            navController.navigateIfPossible(it.route)
                        }
                    }
                    is NavigationState.StartActivity -> context.startActivity(it.intent)
                }
            }
        }
    }
    content()
}
