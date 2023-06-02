package com.akvelon.wallpaper.extension

fun <T : Enum<T>> enumToOrdinal(enum: T?) = enum?.ordinal

fun <T : Enum<T>> ordinalToEnum(values: Array<T>, ordinal: Int?) =
    ordinal?.let { values[it] }
