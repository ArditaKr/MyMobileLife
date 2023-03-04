package com.arditakrasniqi.mymobilelife.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arditakrasniqi.mymobilelife.R
import com.arditakrasniqi.mymobilelife.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageOnBtnClick()
    }


    private fun manageOnBtnClick() {
        binding.goToFirstScreen.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }
        binding.goToSecondScreen.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_secondListFragment)
        }
    }
}
