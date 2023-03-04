package com.arditakrasniqi.mymobilelife.presentation.fragments.list_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.repository.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondListViewModel @Inject constructor(private val retrofitRepository: RetrofitRepository) :
    ViewModel() {

    private lateinit var _imageResponse: Flow<PagingData<Image>>
    val imageResponse: Flow<PagingData<Image>>
        get() = _imageResponse


    fun getImagesFromPagingSource(page: Int) = viewModelScope.launch {
        _imageResponse = retrofitRepository.getImagesFromPagingSource(page)
    }
}

