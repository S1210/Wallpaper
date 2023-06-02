package com.akvelon.wallpaper.extension

fun String?.valueOrDefault(default: String) = if (isNullOrEmpty()) default else this