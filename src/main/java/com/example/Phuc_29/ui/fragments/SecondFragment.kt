package com.example.Phuc_29.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.Phuc_29.R
import com.example.Phuc_29.databinding.FragmentSecondBinding
import com.example.Phuc_29.ui.viewmodel.ShareViewModel

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private val shareViewModel: ShareViewModel by activityViewModels()

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
        val student = shareViewModel.student
        binding.apply {
            tvDetailId.text = getString(R.string.student_id_label, student?.Id ?: "")
            tvDetailName.text = getString(R.string.student_name_label, student?.Name ?: "")
            tvDetailSex.text = getString(R.string.student_sex_label, student?.Sex ?: "")
            tvDetailHobbies.text = getString(R.string.student_hobbies_label, student?.Hobbies ?: "")
            tvHomeTown.text ="Quê quán: ${student?.HomeTown}"
            btnBackBai2.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}