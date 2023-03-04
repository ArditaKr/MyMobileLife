package com.arditakrasniqi.mymobilelife.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arditakrasniqi.mymobilelife.data.api.ServiceAPI
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.data.paging.ImagesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val serviceAPI: ServiceAPI) {
    suspend fun getImages(page: Int, limit: Int) = serviceAPI.getImages(page, limit)

    fun getImagesFromPagingSource(page: Int): Flow<PagingData<Image>> {
        return Pager(
            PagingConfig(pageSize = page , enablePlaceholders = false)
        ) {
            ImagesPagingSource(serviceAPI)
        }.flow
    }
}