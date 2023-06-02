package com.akvelon.wallpaper.controller

import com.akvelon.wallpaper.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeController @Inject constructor(
    @DefaultDispatcher private val coroutineScope: CoroutineScope
) {

    private val _date = MutableStateFlow(LocalDate.now())
    val date = _date.asStateFlow()

    init {
        coroutineScope.launch {
            while (true) {
                delay(1000)
                _date.value = LocalDate.now()
            }
        }
    }

}