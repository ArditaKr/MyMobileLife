package com.arditakrasniqi.mymobilelife.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arditakrasniqi.mymobilelife.R
import com.arditakrasniqi.mymobilelife.databinding.ItemNetworkStateBinding
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PagingLoadStateAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T, VH>
) : LoadStateAdapter<PagingLoadStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder(
            ItemNetworkStateBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_network_state, parent, false)
            )
        ) { adapter.retry() }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class NetworkStateItemViewHolder(
        private val binding: ItemNetworkStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMsg.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()

                when ((loadState as? LoadState.Error)?.error) {
                    is ConnectException, is UnknownHostException -> {
                        errorMsg.text = "Nuk keni internet"
                    }
                    is HttpException -> {
                        errorMsg.text = "Problem me server"
                    }
                    is SocketTimeoutException -> {
                        errorMsg.text = "TimeOut"
                    }
                    is SocketException -> {
                        errorMsg.text = "Lidhja me internet u ndërpre!"
                    }
                    else -> {
                        errorMsg.text = "Problem i panjohur!"
                    }
                }
            }
        }
    }
}