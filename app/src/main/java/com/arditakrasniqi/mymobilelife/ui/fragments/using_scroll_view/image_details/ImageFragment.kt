package com.arditakrasniqi.mymobilelife.ui.fragments.using_scroll_view.image_details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.databinding.FragmentImageBinding
import com.arditakrasniqi.mymobilelife.utils.DataState
import com.arditakrasniqi.mymobilelife.utils.LoadingDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL


@AndroidEntryPoint
class ImageFragment : Fragment(
) {
    private val imageViewModel by viewModels<ImageViewModel>()

    lateinit var binding: FragmentImageBinding
    private lateinit var photoId: String
    var photo: Image? = null
    private var imageUrl: URL? = null
    private var imageDownloadUrl: URL? = null
    private var imageWidth: Int = 0
    private var imageHeight: Int = 0
    private lateinit var authorName: String
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
        initLayout()
        onClick()
    }
    // getting object from previous fragment
    private fun getArgs(){
        val args: ImageFragmentArgs by navArgs()
        photo = args.image
        photoId = args.image.id
        imageWidth = args.image.width
        imageHeight = args.image.height
        imageUrl = args.image.url
        authorName = args.image.author
        imageDownloadUrl = args.image.download_url
        Glide.with(requireContext())
            .load(args.image.download_url)
            .transform(RoundedCorners(16))
            .into(binding.authorImage)

    }

    private fun initLayout() {
        binding.authorName.text = authorName
        binding.imgIdValue.text = photoId
        binding.downloadURL.text = imageDownloadUrl.toString()
        binding.url.text = imageUrl.toString()
        binding.width.text = imageWidth.toString()
        binding.height.text = imageHeight.toString()
    }

    private fun onClick(){
        binding.normalImage.setOnClickListener {
            changeImage(null)
        }

        binding.blurImage.setOnClickListener {
            getBlurImageFromAPI()
        }

        binding.grayscaleImage.setOnClickListener {
            getGrayScaleImageFromAPI()
        }
    }



    private fun getGrayScaleImageFromAPI() {
        imageViewModel.getGrayscaleImageFromAPI(photoId, imageWidth, imageHeight)
        imageViewModel.grayscaleImageFromAPI.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    Log.d("TAG", "loading")
                    loadingDialog.show()
                }

                is DataState.Success -> {
                    loadingDialog.dismiss()
                    Log.d("TAG", "sukses")
                    changeImage(BitmapFactory.decodeStream(it.data?.byteStream()))
                    binding.authorName.text = photo?.author
                    return@observe
                }

                is DataState.Error -> {
                    Log.d("TAG", "error")
                    loadingDialog.dismiss()
                    Toast.makeText(
                        requireContext(),
                        it.error?.name.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }

    private fun getBlurImageFromAPI() {
        imageViewModel.getBlurImageFromAPI(photoId, 250, 180)
        imageViewModel.blurImageFromAPI.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    Log.d("TAG", "loading")
                    loadingDialog.show()
                }

                is DataState.Success -> {
                    loadingDialog.dismiss()
                    Log.d("TAG", "sukses")
                    changeImage(BitmapFactory.decodeStream(it.data?.byteStream()))
                    binding.authorName.text = photo?.author
                    return@observe
                }

                is DataState.Error -> {
                    Log.d("TAG", "error")
                    loadingDialog.dismiss()
                    Toast.makeText(
                        requireContext(),
                        it.error?.name.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }

    private fun changeImage(bitmap: Bitmap?) {
        if (bitmap != null) {
            binding.authorImage.setImageBitmap(bitmap)
        } else if (photo != null) {
            Glide.with(requireContext())
                .load(photo?.download_url)
                .into(binding.authorImage)
        }
    }
}