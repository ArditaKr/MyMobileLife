package com.arditakrasniqi.mymobilelife.domain.usecase


import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.domain.repository.ImageRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend fun getImages(page: Int, limit: Int): MutableList<Image>? =
        imageRepository.getImages(page, limit)

    suspend fun getGrayscaleImage(imageId: String, width: Int, height: Int): ResponseBody? =
        imageRepository.grayscalePhoto(imageId, width, height)

    suspend fun getBlurImage(imageId: String, width: Int, height: Int): ResponseBody? =
        imageRepository.blurPhoto(imageId, width, height)

}