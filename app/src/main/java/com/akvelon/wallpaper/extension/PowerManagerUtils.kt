package com.akvelon.wallpaper.extension

import android.os.Build
import android.os.PowerManager

fun PowerManager.isDeviceIdleModeExtra() = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    !isDeviceIdleMode
} else {
    true
}