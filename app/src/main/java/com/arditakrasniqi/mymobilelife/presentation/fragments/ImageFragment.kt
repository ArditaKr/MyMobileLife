package com.arditakrasniqi.mymobilelife.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arditakrasniqi.mymobilelife.R
import com.arditakrasniqi.mymobilelife.databinding.FragmentImageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: ImageFragmentArgs by navArgs()

        val id = args.imageData.id
        val authorName = args.imageData.author
        val url = args.imageData.url
        val downloadUrl = args.imageData.download_url
        val imageWidth = args.imageData.width
        val imageHeight = args.imageData.height

        binding.imgIdValue.text = id
        binding.authorName.text = authorName
        binding.width.text = imageWidth.toString()
        binding.height.text = imageHeight.toString()


        Glide.with(requireContext())
            .load(downloadUrl)
            .transform(RoundedCorners(16))
            .into(binding.authorImage)





    }

}