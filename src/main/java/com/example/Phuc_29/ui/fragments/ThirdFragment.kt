package com.example.Phuc_29.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.Phuc_29.R
import com.example.Phuc_29.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {
    private lateinit var binding: FragmentThirdBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater,container,false)
        return binding.root
    }
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.btnOpenAverage.setOnClickListener {
            findNavController().navigate(R.id.to_tbc)
        }

		binding.btnOpenStudent.setOnClickListener {
            findNavController().navigate(R.id.to_sinh_vien)
        }

        binding.btnOpenPT1.setOnClickListener {
            findNavController().navigate(R.id.action_threeFragment_to_PT1Fragment)
        }

        binding.btnOpenTriagle.setOnClickListener {
            findNavController().navigate(R.id.action_threeFragment_to_triangleFragment2)
        }
        binding.btnOpenPT2.setOnClickListener {
            findNavController().navigate(R.id.action_threeFragment_to_PTBac2Fragment)
        }
	}
}