package com.example.Phuc_29.tbc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.Phuc_29.databinding.FragmentTBCOuputBinding


class TBC_OuputFragment : Fragment() {
    private lateinit var binding : FragmentTBCOuputBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTBCOuputBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val res = arguments?.getString("res")
        binding.tvRes.text = res

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}