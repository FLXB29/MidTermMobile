package com.example.midbyme.triangle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.midbyme.databinding.FragmentTriangleAreaBinding

class TriangleFragment : Fragment() {
    private lateinit var binding: FragmentTriangleAreaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTriangleAreaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTinh.setOnClickListener {
            val a = binding.edtCanhA.text.toString().toDoubleOrNull()
            val b = binding.edtCanhB.text.toString().toDoubleOrNull()
            val c = binding.edtCanhC.text.toString().toDoubleOrNull()

            if (a == null || b == null || c == null) {
                Toast.makeText(requireContext(), "Vui lòng nhập đủ 3 cạnh", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isTriangle = (a + b > c) && (a + c > b) && (b + c > a)

            if (!isTriangle) {
                binding.tvResult.text = "Ba cạnh không tạo thành tam giác hợp lệ"
                return@setOnClickListener
            }

            val p = (a + b + c) / 2
            val area = Math.sqrt(p * (p - a) * (p - b) * (p - c))

            val formattedArea = String.format("%.2f", area)

            binding.tvResult.text = "Diện tích tam giác: $formattedArea"
        }

        binding.btnHuy.setOnClickListener {
            binding.edtCanhA.text?.clear()
            binding.edtCanhB.text?.clear()
            binding.edtCanhC.text?.clear()
            binding.tvResult.text = ""
        }
    }
}