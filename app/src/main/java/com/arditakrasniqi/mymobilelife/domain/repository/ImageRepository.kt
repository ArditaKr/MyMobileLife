package com.arditakrasniqi.mymobilelife.domain.repository

import com.arditakrasniqi.mymobilelife.data.dataSource.ImagesDataSource
import com.arditakrasniqi.mymobilelife.data.model.Image
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class ImageRepository @Inject constructor(private val imagesDataSource: ImagesDataSource) {
    suspend fun getImages(page: Int, limit: Int) = imagesDataSource.getImages(page, limit)

    suspend fun grayscalePhoto(imageId: String, width: Int, height: Int): ResponseBody? =
        imagesDataSource.getGrayscalePhoto(imageId,width, height)

    suspend fun blurPhoto(imageId: String, width: Int, height: Int): ResponseBody? =
        imagesDataSource.getBlurPhoto(imageId,width, height)

}