package com.akvelon.wallpaper.data.remote.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface IImageApi {

    @GET("{width}/{height}")
    suspend fun getImage(
        @Path("width") width: String,
        @Path("height") height: String
    ): ResponseBody

}