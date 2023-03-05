package com.arditakrasniqi.mymobilelife.ui.fragments.using_scroll_view.list_screen

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
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.databinding.FragmentListBinding
import com.arditakrasniqi.mymobilelife.ui.fragments.using_scroll_view.ImageAdapter
import com.arditakrasniqi.mymobilelife.utils.DataState
import com.arditakrasniqi.mymobilelife.utils.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment(){
    private val listViewModel by viewModels<ListViewModel>()
    lateinit var binding: FragmentListBinding
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }
    private val pageStart: Int = 1
    private var currentPage: Int = pageStart
    private var isLoading: Boolean = false
    private var limit = 6

    private var recyclerDataArrayList = mutableListOf<Image>()
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        listViewModel.getImagesFromAPI(currentPage, limit)

        listViewModel.imagesFromAPI.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                  //  loadingDialog.show()
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
                        it.error?.name.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }


    }

    private fun populateData() {
        imageAdapter = ImageAdapter()

        binding.imageListRV.apply {
            //init adapter
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(requireContext())
            //scroll listener
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                     //when recycler is not scrolling, after scrolling once, it means we can make an api call to get new data and load new page
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
        //when an item from list is clicked navigate to view with more details
        imageAdapter.setOnItemClickListener { it, position ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToImageFragment(it))
        }
    }
}