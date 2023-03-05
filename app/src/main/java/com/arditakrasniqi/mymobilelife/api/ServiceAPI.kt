package com.arditakrasniqi.mymobilelife.api

import com.arditakrasniqi.mymobilelife.data.model.Image
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceAPI {
   //get request to get list with images from API
    @GET("v2/list")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<MutableList<Image>>

    //get request to get grayscale image from API
    @GET("id/{id}/{width}/{height}?grayscale")
    suspend fun getGrayscalePhoto(
        @Path("id") id: String,
        @Path("width") width: Int,
        @Path("height") height: Int
    ): Response<ResponseBody>

    //get request to get blur image from API
    @GET("id/{id}/{width}/{height}?blur")
    suspend fun getBlurPhoto(
        @Path("id") id: String,
        @Path("width") width: Int,
        @Path("height") height: Int
    ): Response<ResponseBody>
}