package com.arditakrasniqi.mymobilelife.domain.usecase

import androidx.paging.PagingData
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.domain.repository.ImagePagingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagingImagesUseCase @Inject constructor(private val imagePagingRepository: ImagePagingRepository) {
    fun getImagePagingList(): Flow<PagingData<Image>> = imagePagingRepository.getImagePagingList()
}