package com.arditakrasniqi.mymobilelife.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arditakrasniqi.mymobilelife.R
import com.arditakrasniqi.mymobilelife.adapter.ImageAdapter
import com.arditakrasniqi.mymobilelife.data.model.Image
import com.arditakrasniqi.mymobilelife.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding

    private lateinit var recyclerDataArrayList: ArrayList<Image>
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateData()
    }

    private fun populateData() {
        recyclerDataArrayList = ArrayList()
        recyclerDataArrayList.add(Image("0", "Ardita Krasniqi", 100, 100, URL("https://test.com") ,URL("https://test.com")))
        recyclerDataArrayList.add(Image("1", "Ardita Krasniqi", 100, 100, URL("https://test.com") ,URL("https://test.com")))
        recyclerDataArrayList.add(Image("2", "Ardita Krasniqi", 100, 100, URL("https://test.com") ,URL("https://test.com")))

        imageAdapter = ImageAdapter()

        binding.imageListRV.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        imageAdapter.differ.submitList(recyclerDataArrayList)

        imageAdapter.setOnItemClickListener { it, position ->
            findNavController().navigate(R.id.action_listFragment_to_imageFragment)
        }
    }
}