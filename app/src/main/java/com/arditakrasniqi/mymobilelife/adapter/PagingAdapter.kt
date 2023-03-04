package com.arditakrasniqi.mymobilelife.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arditakrasniqi.mymobilelife.R
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.databinding.ImageRowLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import javax.inject.Inject

class PagingAdapter @Inject constructor() : PagingDataAdapter<Image, PagingAdapter.ImageAdapterViewHolder>(DifferCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageAdapterViewHolder(ImageRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ImageAdapterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ImageAdapterViewHolder(private val binding: ImageRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            binding.author.text = image.author
            Glide.with(itemView.context)
                .load(image.download_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .downsample(DownsampleStrategy.AT_MOST)
                .placeholder(R.drawable.placeholder)
                .into(binding.authorImage)
        }
    }

    object DifferCallBack : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Image, newItem: Image) =
            oldItem == newItem
    }
}