package com.akvelon.wallpaper.domain.repository

interface ImageRepository {

    suspend fun loadImage()

}