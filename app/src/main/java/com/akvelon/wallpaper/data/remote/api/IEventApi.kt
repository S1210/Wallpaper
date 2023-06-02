package com.akvelon.wallpaper.data.remote.api

import com.akvelon.wallpaper.data.remote.dto.event.ResponseEvent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IEventApi {

    @GET("{language}/onthisday/selected/{month}/{day}")
    suspend fun getEvents(
        @Path("language") language: String,
        @Path("month") month: String,
        @Path("day") day: String
    ): Response<ResponseEvent>

}