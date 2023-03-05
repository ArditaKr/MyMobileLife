package com.arditakrasniqi.mymobilelife.ui.fragments.using_paging_library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.databinding.ImageRowLayoutBinding
import com.arditakrasniqi.mymobilelife.utils.setOnSingleClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

class PagingAdapter @Inject constructor() : PagingDataAdapter<Image, PagingAdapter.ImageAdapterViewHolder>(
    DifferCallBack
) {

    var selectedItem= -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageAdapterViewHolder(ImageRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ImageAdapterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ImageAdapterViewHolder(private val binding: ImageRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            binding.author.text = image.author

            Glide.with(binding.authorImage)
                .load(image.download_url)
                .transition(withCrossFade())
                .apply(RequestOptions().override(250, 180))
                .transform(RoundedCorners(16))
                .into(binding.authorImage)

            binding.root.setOnSingleClickListener {
                selectedItem = position
                notifyDataSetChanged()
                onItemClickListener?.let {
                    it(image, position) } }
        }
    }

    object DifferCallBack : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Image, newItem: Image) =
            oldItem == newItem
    }

    private var onItemClickListener: ((Image, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Image, Int) -> Unit) {
        onItemClickListener = listener
    }
}