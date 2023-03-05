package com.arditakrasniqi.mymobilelife.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arditakrasniqi.mymobilelife.api.ServiceAPI
import com.arditakrasniqi.mymobilelife.data.dataSource.ImagesPagingSource
import com.arditakrasniqi.mymobilelife.data.model.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImagePagingRepository @Inject constructor(private val serviceAPI: ServiceAPI) {

    fun getImagePagingList(): Flow<PagingData<Image>> {
        return Pager(
            PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagesPagingSource(serviceAPI) }
        ).flow
    }
}


