package com.akvelon.wallpaper.controller.enums

import androidx.annotation.StringRes
import com.akvelon.wallpaper.R

enum class UpdateTime(@StringRes val title: Int, val internal: Long) {

    ONE_MINUTE(R.string.one_minute, 1),
    TEN_MINUTES(R.string.ten_minute, 10),
    ONE_HOUR(R.string.one_hour, 60),
    TWO_HOURS(R.string.two_hours, 120),
    TEN_HOURS(R.string.ten_hours, 600),
    TWELVE_HOURS(R.string.twelve_hours, 720),
    ONE_DAY(R.string.one_day, 1440)

}