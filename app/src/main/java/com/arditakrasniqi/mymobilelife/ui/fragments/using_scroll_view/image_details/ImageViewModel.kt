package com.arditakrasniqi.mymobilelife.ui.fragments.using_scroll_view.image_details

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
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
/** viewmodel based on MVVVM will communicate with use case to send data to fragment */

@HiltViewModel
class ImageViewModel @Inject constructor(private val getImagesUseCase: GetImagesUseCase) : ViewModel() {

    /** Retrofit*/
    var blurImageFromAPI: MutableLiveData<DataState<ResponseBody>> = MutableLiveData()
    var grayscaleImageFromAPI: MutableLiveData<DataState<ResponseBody>> = MutableLiveData()

    //handling errors could be a better way but wasn't mandatory for now :D


    fun getBlurImageFromAPI(photoId: String, width: Int, height: Int) = viewModelScope.launch {
        blurImageFromAPI.value = DataState.Loading()
        try {
            val response = getImagesUseCase.getBlurImage(photoId, width, height)
            Log.d("TAG", "getBlurImageFromAPI: $response")
            if (response != null) {
                blurImageFromAPI.value = DataState.Success(response)
            }
        } catch (e: Exception) {
            when (e) {
                is HttpException -> blurImageFromAPI.value =
                    DataState.Error(Errors.SERVER, "Problem i serverit ")
                is ConnectException, is UnknownHostException -> blurImageFromAPI.value =
                    DataState.Error(Errors.NETWORK, "Nuk keni internet")
                is SocketTimeoutException -> blurImageFromAPI.value =
                    DataState.Error(Errors.TIMEOUT, "Timeout")
                is SocketException -> blurImageFromAPI.value =
                    DataState.Error(Errors.TIMEOUT, "Lidhja me internet u ndërpre!")
                else -> {
                    blurImageFromAPI.value = DataState.Error(Errors.UNKNOWN, "Problem i panjohur!")
                }
            }
        }
    }

    fun getGrayscaleImageFromAPI(photoId: String, width: Int, height: Int) = viewModelScope.launch {
        grayscaleImageFromAPI.value = DataState.Loading()
        try {
            val response = getImagesUseCase.getGrayscaleImage(photoId, width, height)
            Log.d("TAG", "getGrayscaleImageFromAPI: $response")
            if (response != null) {
                grayscaleImageFromAPI.value = DataState.Success(response)
            }
        } catch (e: Exception) {
            when (e) {
                is HttpException -> grayscaleImageFromAPI.value =
                    DataState.Error(Errors.SERVER, "Problem i serverit ")
                is ConnectException, is UnknownHostException -> grayscaleImageFromAPI.value =
                    DataState.Error(Errors.NETWORK, "Nuk keni internet")
                is SocketTimeoutException -> grayscaleImageFromAPI.value =
                    DataState.Error(Errors.TIMEOUT, "Timeout")
                is SocketException -> grayscaleImageFromAPI.value =
                    DataState.Error(Errors.TIMEOUT, "Lidhja me internet u ndërpre!")
                else -> {
                    grayscaleImageFromAPI.value =
                        DataState.Error(Errors.UNKNOWN, "Problem i panjohur!")
                }
            }
        }
    }
}


