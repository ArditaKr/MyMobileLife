package com.arditakrasniqi.mymobilelife.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.databinding.ImageRowLayoutBinding
import com.arditakrasniqi.mymobilelife.utils.setOnSingleClickListener


class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Image,
            newItem: Image
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageRowLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)

    }

    inner class ImageViewHolder(
        private val binding: ImageRowLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.author.text = image.author
            binding.url.text = image.url.toString()
            binding.downloadUrl.text = image.download_url.toString()

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