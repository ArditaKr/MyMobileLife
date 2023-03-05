package com.arditakrasniqi.mymobilelife.ui.fragments.using_paging_library.image_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arditakrasniqi.mymobilelife.databinding.FragmentSecondImageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondImageFragment : Fragment() {
    private lateinit var binding: FragmentSecondImageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val args: SecondImageFragmentArgs by navArgs()
//
//        val id = args.imageDetails.id
//        val authorName = args.imageDetails.author
//        val url = args.imageDetails.url
//        val downloadUrl = args.imageDetails.download_url
//        val imageWidth = args.imageDetails.width
//        val imageHeight = args.imageDetails.height
//
//        binding.imgIdValue.text = id
//        binding.authorName.text = authorName
//        binding.width.text = imageWidth.toString()
//        binding.height.text = imageHeight.toString()
//        binding.url.text = url.toString()
//        binding.downloadURL.text = downloadUrl.toString()
//
//
//        //show image from URL in background of Linear Layout
//        Glide.with(requireContext())
//            .asDrawable()
//            .load(downloadUrl)
//            .into(object : CustomTarget<Drawable?>() {
//                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
//                override fun onResourceReady(
//                    resource: Drawable,
//                    transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?
//                ) {
//                    binding.imageLinearLayout.background = resource.current
//                    binding.imageLinearLayout.background.alpha = 150
//                }
//            })
//        //show image from URL in imageView
//        Glide.with(requireContext())
//            .load(downloadUrl)
//            .transform(RoundedCorners(16))
//            .into(binding.authorImage)

    }
}