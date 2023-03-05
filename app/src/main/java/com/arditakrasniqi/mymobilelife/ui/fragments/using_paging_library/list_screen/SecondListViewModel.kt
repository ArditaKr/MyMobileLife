package com.arditakrasniqi.mymobilelife.ui.fragments.using_paging_library.list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.domain.usecase.GetPagingImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SecondListViewModel @Inject constructor(private val imagesUseCase: GetPagingImagesUseCase) :
    ViewModel() {

    fun getImagesFromPagingSource(): Flow<PagingData<Image>> {
        return imagesUseCase.getImagePagingList()
            .map { it }
            .cachedIn(viewModelScope)
    }
}

