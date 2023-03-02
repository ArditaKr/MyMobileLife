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
import androidx.recyclerview.widget.RecyclerView
import com.arditakrasniqi.mymobilelife.R
import com.arditakrasniqi.mymobilelife.adapter.ImageAdapter
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.databinding.FragmentListBinding
import com.arditakrasniqi.mymobilelife.utils.DataState
import com.arditakrasniqi.mymobilelife.utils.LoadingDialog
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val listViewModel  by viewModels<ListViewModel>()
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }


    private val imageUrls = mutableListOf<String>()
    private var isLoading = false
    private var currentPage = 0
    private lateinit var adapter: ImageAdapter

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
                    loadingDialog.show()
                }

                is DataState.Success -> {
                    recyclerDataArrayList = it.data!!
                    imageAdapter.differ.submitList(recyclerDataArrayList)
                    loadingDialog.dismiss()
                    return@observe
                }

                is DataState.Error -> {
                   loadingDialog.dismiss()
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
            binding.imageListRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
//                    val lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//                    val totalItemCount = (layoutManager as LinearLayoutManager).itemCount
//                    if (lastVisibleItemPosition == totalItemCount - 1) {
//                        loadingDialog.dismiss()
//                    }else{
//                        loadingDialog.show()
//                    }
                }
            })
        }

        imageAdapter.setOnItemClickListener { it, position ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToImageFragment(it))
        }
    }
}