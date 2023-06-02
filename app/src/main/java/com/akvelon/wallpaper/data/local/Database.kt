package com.akvelon.wallpaper.data.local

import androidx.room.AutoMigration
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akvelon.wallpaper.data.local.converter.TypeConverter
import com.akvelon.wallpaper.data.local.dao.EventDao
import com.akvelon.wallpaper.data.local.entity.EventEntity

@androidx.room.Database(
    entities = [EventEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(TypeConverter::class)
abstract class Database : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "room_db.db"
    }

    abstract fun eventDao(): EventDao

}