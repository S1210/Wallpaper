package com.akvelon.wallpaper.data.local.entity

import androidx.room.PrimaryKey

abstract class RoomModel(
    @PrimaryKey(autoGenerate = true)
    open var id: Long = 0
)