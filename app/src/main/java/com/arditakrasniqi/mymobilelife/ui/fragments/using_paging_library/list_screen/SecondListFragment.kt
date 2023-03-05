package com.arditakrasniqi.mymobilelife.ui.fragments.using_paging_library.list_screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.arditakrasniqi.mymobilelife.databinding.FragmentSecondListBinding
import com.arditakrasniqi.mymobilelife.ui.fragments.using_paging_library.PagingAdapter
import com.arditakrasniqi.mymobilelife.ui.fragments.using_paging_library.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class SecondListFragment : Fragment() {

    private lateinit var binding: FragmentSecondListBinding
    private val secondListViewModel by viewModels<SecondListViewModel>()
    private lateinit var imageAdapter: PagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        makeApiCall()
    }


    //get data from viewModel and submit to adapter
    private fun makeApiCall() {
        lifecycleScope.launch {
            secondListViewModel.getImagesFromPagingSource().collect {
                imageAdapter.submitData(it)
            }
        }
    }

    //initialize data and manage its behaviour
    private fun initAdapter() {
        imageAdapter = PagingAdapter()
        with(imageAdapter) {
            binding.imageListRV.apply {
                layoutManager = LinearLayoutManager(requireContext())

                //manage top and end of shown list
                // if we don't have internet connection or another error on top or bottom of shown list PagingLoadState will manage this,
                adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(imageAdapter),
                    footer = PagingLoadStateAdapter(imageAdapter)
                )

                //change indicator color of swipeRefreshLayout
                binding.swipeRefreshLayout.setColorSchemeColors(Color.RED)
                //try to load after an error such as network disconnection
                binding.swipeRefreshLayout.setOnRefreshListener { refresh() }

                // listen for any changes, manage and present loading states
                imageAdapter.addLoadStateListener { loadState ->
                    val state = loadState.refresh
                    binding.swipeRefreshLayout.isRefreshing = state is LoadState.Loading
                    // check for error, if exists show to user
                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error
                        ?: loadState.source.refresh as? LoadState.Error
                    errorState?.let {
                        when (it.error) {
                            is ConnectException, is UnknownHostException -> {
                                binding.swipeRefreshLayout.isRefreshing = false
                                Toast.makeText(
                                    requireContext(),
                                    "Nuk keni internet",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is HttpException -> {
                                binding.swipeRefreshLayout.isRefreshing = false
                                Toast.makeText(
                                    requireContext(),
                                    "Problem me server",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is SocketTimeoutException -> {
                                binding.swipeRefreshLayout.isRefreshing = false
                                Toast.makeText(requireContext(), "TimeOut", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            is SocketException -> {
                                binding.swipeRefreshLayout.isRefreshing = false
                                Toast.makeText(
                                    requireContext(),
                                    "Lidhja me internet u ndërpre!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                binding.swipeRefreshLayout.isRefreshing = false
                                Toast.makeText(
                                    requireContext(),
                                    "Problem i panjohur!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                // when we click on any image item, navigate to next screen with image details via navController component
                // Also, we send image object we clicked to next fragment as argument
                imageAdapter.setOnItemClickListener { it, position ->
                    findNavController().navigate(
                        SecondListFragmentDirections.actionSecondListFragmentToSecondImageFragment(
                            it
                        )
                    )
                }
            }
        }
    }
}





