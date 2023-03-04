package com.arditakrasniqi.mymobilelife.presentation.fragments.image_details_screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arditakrasniqi.mymobilelife.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_image, container, false)
    }


}