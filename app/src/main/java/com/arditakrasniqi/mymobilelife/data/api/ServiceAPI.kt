package com.arditakrasniqi.mymobilelife.data.api

import com.arditakrasniqi.mymobilelife.data.model.Image
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceAPI {

    @GET("list")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<MutableList<Image>>

}