package com.arditakrasniqi.mymobilelife.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arditakrasniqi.mymobilelife.data.api.ServiceAPI
import com.arditakrasniqi.mymobilelife.data.model.Image
import retrofit2.HttpException

class ImagesPagingSource(private val serviceAPI: ServiceAPI) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val currentPage = params.key ?: 1
            val response = serviceAPI.getImages(currentPage, 6)
            val data = response.body()!!
            val responseData = mutableListOf<Image>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            Log.d("TAG", "Error $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return null
    }
}



