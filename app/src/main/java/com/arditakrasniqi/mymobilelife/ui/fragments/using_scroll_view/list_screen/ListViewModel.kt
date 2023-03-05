package com.arditakrasniqi.mymobilelife.ui.fragments.using_scroll_view.list_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.domain.usecase.GetImagesUseCase
import com.arditakrasniqi.mymobilelife.utils.DataState
import com.arditakrasniqi.mymobilelife.utils.Errors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val imagesUseCase: GetImagesUseCase) : ViewModel() {

    /** Retrofit*/
    var imagesFromAPI: MutableLiveData<DataState<List<Image>>> = MutableLiveData()

    fun getImagesFromAPI(currentPage: Int, limit: Int) = viewModelScope.launch {
        imagesFromAPI.value = DataState.Loading()
        try {
            val response = imagesUseCase.getImages(currentPage, limit)
            Log.d("TAG", "getImagesFromAPI: $response")
            if (!response.isNullOrEmpty()) {
                imagesFromAPI.value = DataState.Success(response)
            }
        } catch (e: Exception) {
            when (e) {
                is HttpException -> imagesFromAPI.value =
                    DataState.Error(Errors.SERVER, "Problem i serverit ")
                is ConnectException, is UnknownHostException -> imagesFromAPI.value =
                    DataState.Error(Errors.NETWORK, "Nuk keni internet")
                is SocketTimeoutException -> imagesFromAPI.value =
                    DataState.Error(Errors.TIMEOUT, "Timeout")
                is SocketException -> imagesFromAPI.value =
                    DataState.Error(Errors.TIMEOUT, "Lidhja me internet u ndërpre!")
                else -> {
                    imagesFromAPI.value = DataState.Error(Errors.UNKNOWN, "Problem i panjohur!")
                }
            }
        }
    }
}


