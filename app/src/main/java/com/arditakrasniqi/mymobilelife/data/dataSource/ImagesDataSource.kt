package com.arditakrasniqi.mymobilelife.data.dataSource

import com.arditakrasniqi.mymobilelife.api.ServiceAPI
import com.arditakrasniqi.mymobilelife.data.model.Image
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * ImagesDataSource will be a source to communicate with API to get the data we need
 * */
class ImagesDataSource @Inject constructor(private val serviceAPI: ServiceAPI) {

    suspend fun getImages(page: Int, limit: Int): MutableList<Image>? =
        serviceAPI.getImages(page, limit).body()

    suspend fun getGrayscalePhoto(imageId: String,width: Int, height: Int): ResponseBody? =
        serviceAPI.getGrayscalePhoto(imageId,width,height).body()

    suspend fun getBlurPhoto(imageId: String,width: Int, height: Int): ResponseBody? =
        serviceAPI.getBlurPhoto(imageId,width,height).body()

}