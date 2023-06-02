package com.akvelon.wallpaper.di

import com.akvelon.wallpaper.data.repository.EventRepositoryImpl
import com.akvelon.wallpaper.data.repository.FileRepositoryImpl
import com.akvelon.wallpaper.data.repository.ImageRepositoryImpl
import com.akvelon.wallpaper.data.repository.WallpaperRepositoryImpl
import com.akvelon.wallpaper.domain.repository.EventRepository
import com.akvelon.wallpaper.domain.repository.FileRepository
import com.akvelon.wallpaper.domain.repository.ImageRepository
import com.akvelon.wallpaper.domain.repository.WallpaperRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEventRepository(
        eventRepositoryImpl: EventRepositoryImpl
    ): EventRepository

    @Binds
    @Singleton
    abstract fun bindFileRepository(
        fileRepositoryImpl: FileRepositoryImpl
    ): FileRepository

    @Binds
    @Singleton
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    @Singleton
    abstract fun bindWallpaperRepository(
        wallpaperRepositoryImpl: WallpaperRepositoryImpl
    ): WallpaperRepository

}