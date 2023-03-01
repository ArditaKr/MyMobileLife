package com.arditakrasniqi.mymobilelife.data.api

import com.arditakrasniqi.mymobilelife.data.model.Image
import retrofit2.Response
import retrofit2.http.GET

interface ServiceAPI {

    @GET("v2/list")
    suspend fun getImages(): Response<List<Image>>

}