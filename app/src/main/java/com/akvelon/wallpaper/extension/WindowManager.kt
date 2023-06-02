package com.akvelon.wallpaper.extension

import android.graphics.Rect
import android.os.Build
import android.util.Size
import android.view.WindowInsets
import android.view.WindowManager

fun WindowManager.getScreenSize(): Size {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val metrics = currentWindowMetrics
        val windowInsets = metrics.windowInsets
        val insets = windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.navigationBars()
                    or WindowInsets.Type.displayCutout()
        )
        val insetsWidth = insets.right + insets.left
        val insetsHeight = insets.top + insets.bottom
        val bounds: Rect = metrics.bounds
        Size(
            bounds.width() - insetsWidth,
            bounds.height() - insetsHeight
        )
    } else {
        val display = defaultDisplay
        Size(display.width, display.height)
    }

}