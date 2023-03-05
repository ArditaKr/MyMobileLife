package com.arditakrasniqi.mymobilelife.ui.fragments.using_scroll_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.databinding.ImageRowLayoutBinding
import com.arditakrasniqi.mymobilelife.utils.setOnSingleClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions


class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


    //we need a call back to tell the difference what changed in a list
    private val differCallBack = object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean //Called to decide whether two objects represent the same item.
        {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean //Called to decide whether two items have the same data.
        // This information is used to detect if the contents of an item have changed.
        {
            return oldItem == newItem
        }
    }

    //differ initializes a AsyncListDiffer with 2 parameters :
    // this - refers to current instance of ImageAdapter
    //differCallBack - refers to the upper object and is used to calculate the difference between the old and new list
    // and to dispatch the necessary update events to any registered observers.
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    //return size of list
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)
    }

    inner class ImageViewHolder(private val binding: ImageRowLayoutBinding) :
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
                    it(image, position)
                }
            }
        }
    }

    companion object {
        var selectedItem = -1
    }

    private var onItemClickListener: ((Image, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Image, Int) -> Unit) {
        onItemClickListener = listener
    }
}