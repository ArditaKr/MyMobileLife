package com.arditakrasniqi.mymobilelife.presentation.fragments.image_details_screens

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arditakrasniqi.mymobilelife.databinding.FragmentImageBinding
import com.arditakrasniqi.mymobilelife.utils.LoadingDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding
    private val imageViewModel by viewModels<ImageViewModel>()
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }


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
        binding.url.text = url.toString()
        binding.downloadURL.text = downloadUrl.toString()

        Glide.with(requireContext())
            .asDrawable()
            .load(downloadUrl)
            .into(object : CustomTarget<Drawable?>() {

                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?
                ) {
                    binding.imageLinearLayout.background = resource.current
                    binding.imageLinearLayout.background.alpha = 150
                }
            })

        Glide.with(requireContext())
            .load(downloadUrl)
            .transform(RoundedCorners(16))
            .into(binding.authorImage)

        binding.segmented2.setTintColor(Color.BLACK)

    }
}