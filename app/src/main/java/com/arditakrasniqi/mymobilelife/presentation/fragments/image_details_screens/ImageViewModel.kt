package com.arditakrasniqi.mymobilelife.presentation.fragments.image_details_screens

import androidx.lifecycle.ViewModel
import com.arditakrasniqi.mymobilelife.repository.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ImageViewModel @Inject constructor(private val retrofitRepository: RetrofitRepository) :
    ViewModel() {

}