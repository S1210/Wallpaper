package com.akvelon.wallpaper.presentation.common

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.text.format.DateFormat
import androidx.annotation.CallSuper
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.akvelon.wallpaper.R
import com.akvelon.wallpaper.extension.isDeviceIdleModeExtra
import com.akvelon.wallpaper.presentation.common.dialog.error.ErrorState
import com.akvelon.wallpaper.presentation.common.dialog.progress_bar.ProgressDialogState
import com.akvelon.wallpaper.presentation.navigation.NavigationState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.net.UnknownHostException

abstract class BaseViewModel(protected val app: Application) : ViewModel() {

    private val _progressDialogFlow = MutableStateFlow(ProgressDialogState())
    val progressDialogFlow = _progressDialogFlow.asStateFlow()

    private val _errorFlow = MutableStateFlow(ErrorState())
    val errorFlow = _errorFlow.asStateFlow()

    private val _navigation = Channel<NavigationState>()
    val navigation = _navigation.receiveAsFlow()

    private val _is24Hours = MutableStateFlow(DateFormat.is24HourFormat(app))
    val is24Hours = _is24Hours.asStateFlow()

    internal val exceptionHandler: CoroutineExceptionHandler
        get() = CoroutineExceptionHandler { _, throwable ->
            errorEvents(throwable)
        }

    @CallSuper
    open fun errorEvents(throwable: Throwable, isShowError: Boolean = true) {
        val pm = app.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (isShowError) {
            when (throwable) {
                is UnknownHostException -> {
                    if (!pm.isDeviceIdleModeExtra()) {
                        showErrorDialog(
                            titleRes = R.string.error,
                            messageRes = R.string.connection_error
                        )
                    }
                }
                else -> {
                    showErrorDialog(
                        title = getString(R.string.error),
                        message = throwable.localizedMessage
                    )
                }
            }
        }
        closeProgressDialog(_progressDialogFlow.value.tag)
    }

    protected fun getString(@StringRes stringRes: Int, vararg args: Any) =
        app.getString(stringRes, *args)

    protected fun getQuantityString(
        @PluralsRes pluralsRes: Int,
        quantity: Int,
        vararg formatArgs: Any
    ) = app.resources.getQuantityString(pluralsRes, quantity, *formatArgs)

    fun showProgressDialog(text: String = "", tag: String = "") {
        _progressDialogFlow.value = ProgressDialogState(isShow = true, text = text, tag = tag)
    }

    fun showProgressDialog(@StringRes textRes: Int, tag: String = "") {
        _progressDialogFlow.value =
            ProgressDialogState(isShow = true, text = getString(textRes), tag = tag)
    }

    fun closeProgressDialog(tag: String = "") {
        if (_progressDialogFlow.value.tag == tag) _progressDialogFlow.value = ProgressDialogState()
    }

    fun showErrorDialog(title: String = "", message: String?) {
        _errorFlow.value = ErrorState(
            isShow = true,
            title = title,
            message = message ?: getString(R.string.unknown_error)
        )
    }

    fun showErrorDialog(title: String = "", @StringRes messageRes: Int) {
        _errorFlow.value = ErrorState(isShow = true, title = title, message = getString(messageRes))
    }

    fun showErrorDialog(@StringRes titleRes: Int, @StringRes messageRes: Int) {
        _errorFlow.value = ErrorState(
            isShow = true,
            title = getString(titleRes),
            message = getString(messageRes)
        )
    }

    fun showErrorDialog(@StringRes titleRes: Int, message: String?) {
        _errorFlow.value = ErrorState(
            isShow = true,
            title = getString(titleRes),
            message = message ?: getString(R.string.unknown_error)
        )
    }

    fun closeErrorDialog() {
        _errorFlow.value = ErrorState()
    }

    suspend fun popBackStack(isParent: Boolean = false) {
        _navigation.send(NavigationState.PopBackStack(isParent))
    }

    suspend fun popBackStackNavigate(route: String, isParent: Boolean = false) {
        _navigation.send(NavigationState.PopBackStackNavigate(route, isParent))
    }

    suspend fun popBackStackRoute(
        popBackRoute: String,
        inclusive: Boolean = false,
        navigateRoute: String? = null,
        isParent: Boolean = false
    ) {
        _navigation.send(
            NavigationState.PopBackStackRoute(
                popBackRoute = popBackRoute,
                inclusive = inclusive,
                navigate = navigateRoute?.let { NavigationState.Navigate(it) },
                isParent = isParent
            )
        )
    }

    suspend fun navigate(route: String, isParent: Boolean = false) {
        _navigation.send(NavigationState.Navigate(route, isParent))
    }

    suspend fun startActivity(intent: Intent) {
        _navigation.send(NavigationState.StartActivity(intent))
    }

    fun set24Hours() {
        _is24Hours.value = DateFormat.is24HourFormat(app)
    }

}