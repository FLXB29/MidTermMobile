package com.example.midbyme

import kotlin.jvm.java
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.midbyme.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
        binding.apply{
            tvDetailId.text = viewModel.Student?.Id
            tvDetailName.text = viewModel.Student?.Name
            tvDetailSex.text = viewModel.Student?.Sex
            tvDetailHobbies.text = viewModel.Student?.Hobbies
        }
    }
}