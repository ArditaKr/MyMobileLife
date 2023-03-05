package com.arditakrasniqi.mymobilelife.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arditakrasniqi.mymobilelife.api.ServiceAPI
import com.arditakrasniqi.mymobilelife.data.model.Image

class ImagesPagingSource(private val serviceAPI: ServiceAPI) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val currentPage = params.key ?: 1
            val response = serviceAPI.getImages(currentPage, 5)
            val data = response.body()!!
            val responseData = mutableListOf<Image>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return null
    }
}



