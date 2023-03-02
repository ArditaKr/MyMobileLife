package com.arditakrasniqi.mymobilelife.presentation.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arditakrasniqi.mymobilelife.R
import com.arditakrasniqi.mymobilelife.adapter.ImageAdapter
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.databinding.FragmentListBinding
import com.arditakrasniqi.mymobilelife.utils.DataState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val listViewModel  by viewModels<ListViewModel>()

    private lateinit var recyclerDataArrayList: List<Image>
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeApiCall()
        populateData()


    }

    private fun makeApiCall() {
        listViewModel.getImagesFromAPI()
        listViewModel.imagesFromAPI.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    //loadingDialog.show()
                }

                is DataState.Success -> {
                   // loadingDialog.dismiss()
                    recyclerDataArrayList = it.data!!

                    Log.d(TAG, "GLIDE: ${it.data[0].url}")


                    imageAdapter.differ.submitList(recyclerDataArrayList)
                }

                is DataState.Error -> {
                  //  loadingDialog.dismiss()
                    Log.d("TAG", "makeApiCall: error ${it.error.toString()}")
                   // it.message?.let { it1 -> requireContext().error(it1) }
                }
            }
        }
    }

    private fun populateData() {

        imageAdapter = ImageAdapter(requireContext())

        binding.imageListRV.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        imageAdapter.setOnItemClickListener { it, position ->
            findNavController().navigate(R.id.action_listFragment_to_imageFragment)
        }
    }

    fun test() {



    }
}