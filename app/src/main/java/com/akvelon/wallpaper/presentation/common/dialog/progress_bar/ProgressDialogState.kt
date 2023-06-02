package com.akvelon.wallpaper.presentation.common.dialog.progress_bar

data class ProgressDialogState(
    val isShow: Boolean = false,
    val text: String = "",
    val tag: String = ""
) {

    companion object {
        const val LOADING_TAG = "Loading"
        const val AUTHENTICATING_TAG = "Authenticating"
    }

}
