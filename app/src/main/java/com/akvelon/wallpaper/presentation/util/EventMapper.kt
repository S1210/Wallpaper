package com.akvelon.wallpaper.presentation.util

import com.akvelon.wallpaper.data.local.entity.EventEntity
import com.akvelon.wallpaper.domain.model.Event

fun EventEntity.toEvent(): Event {
    return Event(
        id = id,
        description = description,
        date = date,
        language = language
    )
}

fun Event.toEventEntity(): EventEntity {
    return EventEntity(
        id = id,
        description = description,
        date = date,
        language = language
    )
}