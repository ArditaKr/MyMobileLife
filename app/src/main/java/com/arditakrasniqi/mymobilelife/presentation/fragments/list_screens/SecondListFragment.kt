package com.arditakrasniqi.mymobilelife.presentation.fragments.list_screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.arditakrasniqi.mymobilelife.R
import com.arditakrasniqi.mymobilelife.adapter.PagingAdapter
import com.arditakrasniqi.mymobilelife.adapter.PagingLoadStateAdapter
import com.arditakrasniqi.mymobilelife.databinding.FragmentSecondListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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

        makeApiCall()
        initAdapter()


    }

    private fun makeApiCall() {
        secondListViewModel.getImagesFromPagingSource(1)
    }

    private fun initAdapter() {
        imageAdapter = PagingAdapter()
        with(imageAdapter) {
            binding.imageListRV.apply {
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
                layoutManager = LinearLayoutManager(requireContext())
                adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(imageAdapter),
                    footer = PagingLoadStateAdapter(imageAdapter)
                )
            }

            binding.swipeRefreshLayout.setColorSchemeColors(Color.RED)
            binding.swipeRefreshLayout.setOnRefreshListener { refresh() }

            with(secondListViewModel) {
                launchOnLifecycleScope {
                    imageResponse.collectLatest { submitData(it) }
                }
            }

            launchOnLifecycleScope {
                imageAdapter.loadStateFlow.collect { loadState ->
                    val state = loadState.refresh
                    binding.swipeRefreshLayout.isRefreshing = state is LoadState.Loading
                    // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
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
            }
        }
    }

    private fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        lifecycleScope.launchWhenCreated {
            execute()
        }
    }
}