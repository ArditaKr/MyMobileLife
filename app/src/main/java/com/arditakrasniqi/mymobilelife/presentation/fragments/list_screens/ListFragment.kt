package com.arditakrasniqi.mymobilelife.presentation.fragments.list_screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arditakrasniqi.mymobilelife.adapter.ImageAdapter
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.databinding.FragmentListBinding
import com.arditakrasniqi.mymobilelife.utils.DataState
import com.arditakrasniqi.mymobilelife.utils.Errors
import com.arditakrasniqi.mymobilelife.utils.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val listViewModel by viewModels<ListViewModel>()
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }
    private val pageStart: Int = 1
    private var currentPage: Int = pageStart
    private var isLoading: Boolean = false
    private var limit = 6

    private var recyclerDataArrayList = mutableListOf<Image>()
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

        imageAdapter = ImageAdapter()
        makeApiCall()
        populateData()


    }


    private fun makeApiCall() {
        listViewModel.getImagesFromAPI(currentPage, limit)

        listViewModel.imagesFromAPI.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    loadingDialog.show()
                }

                is DataState.Success -> {
                    loadingDialog.dismiss()
                    recyclerDataArrayList.addAll(it.data!!)
                    imageAdapter.differ.submitList(recyclerDataArrayList)
                    imageAdapter.notifyDataSetChanged()
                    return@observe
                }

                is DataState.Error -> {
                    loadingDialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                it.error.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                }
            }
        }
    }

    private fun populateData() {

        binding.imageListRV.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(requireContext())

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!binding.imageListRV.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        isLoading = true
                        ++currentPage
                        Log.d("TAG", "currentPage: $currentPage")
                        listViewModel.getImagesFromAPI(currentPage, limit)
                    }
                }
            }
            )
        }
        imageAdapter.setOnItemClickListener { it, position ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToImageFragment(it))
        }
    }
}