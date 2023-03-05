package com.arditakrasniqi.mymobilelife.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arditakrasniqi.mymobilelife.api.ServiceAPI
import com.arditakrasniqi.mymobilelife.data.model.Image

/** ImagesPagingSource is a page-keyed class used to loads pages
 * from network requests via Retrofit to a backend service(API)
 * Key type is Int, since we're using page number to load a page.
 * */
class ImagesPagingSource(private val serviceAPI: ServiceAPI) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image>
    {
        return try {
            val currentPage = params.key ?: 1 // Key(from now currentPage) may be null during a refresh
            //Use 0 or 1 as default, because our API starts from 0/1, same result
            val response = serviceAPI.getImages(currentPage, 5) //make a call to get data from API, limit 5 means only 5 items[objects] per page
            val data = response.body()!! // response from api is  Response<MutableList<Image>>, so we need only List of images
            val responseData = mutableListOf<Image>() //we need a mutable list to put all objects that will come later, after first call from API
            responseData.addAll(data)

            // after getting data from API we should show to user
            LoadResult.Page(
                data = responseData,  //list with images from API
                prevKey = if (currentPage == 1) null else -1, //prevKey means current page
                nextKey = currentPage.plus(1) // next key means next page (in our case +5 items)
            )
        } catch (e: Exception) {
            //if something from try fails, we should tell user what failed :)
            LoadResult.Error(e)
        }
    }
     //when user is scrolling the list and there is some change in the list(list updated, new elements, etc.)
     //Case getRefreshKey() returns null: As our data are updated, there will be a new pair of PagingSource and PagingData,
     // meaning that the paging lib. would need to reload the list.
     //not the best way but skipped it for now :D
    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return null
    }
}



