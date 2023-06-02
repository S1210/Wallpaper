package com.akvelon.wallpaper.presentation.common.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.akvelon.wallpaper.R

@Composable
fun AlertDialog(
    onCancelClick: () -> Unit,
    onOkClick: () -> Unit,
    title: String? = null,
    text: String = "",
    isCancelButtonVisible: Boolean = true,
    isShow: Boolean = false,
    properties: DialogProperties = DialogProperties(),
    okButtonColors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
    @StringRes cancelButtonTextRes: Int = R.string.cancel,
    @StringRes okButtonTextRes: Int = R.string.ok
) {
    Dialog(
        onCancelClick = onCancelClick,
        onOkClick = onOkClick,
        title = title,
        isCancelButtonVisible = isCancelButtonVisible,
        isShow = isShow,
        properties = properties,
        okButtonColors = okButtonColors,
        cancelButtonTextRes = cancelButtonTextRes,
        okButtonTextRes = okButtonTextRes
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = text, fontSize = 16.sp, color = LocalContentColor.current)
        }
    }
}