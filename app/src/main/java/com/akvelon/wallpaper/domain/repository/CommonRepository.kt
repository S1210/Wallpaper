package com.akvelon.wallpaper.domain.repository

import com.akvelon.wallpaper.data.local.entity.RoomModel

interface CommonRepository<T : RoomModel> {

    suspend fun insertItem(item: T): Long

    suspend fun insertItems(items: List<T>)

    suspend fun replaceItem(item: T): Long

    suspend fun replaceItems(items: List<T>)

    suspend fun updateItem(item: T)

    suspend fun updateItems(items: List<T>)

    suspend fun deleteItem(item: T)

    suspend fun deleteItems(items: List<T>)

    suspend fun deleteAll()

}