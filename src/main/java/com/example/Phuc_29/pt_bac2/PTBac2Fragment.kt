package com.example.Phuc_29.pt_bac2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.Phuc_29.databinding.FragmentPtBac2Binding

class PTBac2Fragment : Fragment() {
    private lateinit var binding: FragmentPtBac2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPtBac2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTinh.setOnClickListener {
            val a = binding.edtSoA.text.toString().toDoubleOrNull()
            val b = binding.edtSoB.text.toString().toDoubleOrNull()
            val c = binding.edtSoC.text.toString().toDoubleOrNull()

            if (a == null || b == null || c == null) {
                Toast.makeText(requireContext(), "Vui lòng nhập đủ 3 hệ số", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = when {
                a == 0.0 -> {
                    // Nếu a = 0, phương trình trở thành bậc 1: bx + c = 0
                    if (b == 0.0) {
                        if (c == 0.0) "Phương trình vô số nghiệm" else "Phương trình vô nghiệm"
                    } else {
                        "Phương trình bậc 1: x = ${-c / b}"
                    }
                }
                else -> {
                    // Công thức Delta: Δ = b^2 - 4ac
                    val delta = b * b - 4 * a * c

                    when {
                        delta < 0 -> "Phương trình vô nghiệm (nghiệm phức)"
                        delta == 0.0 -> {
                            val x = -b / (2 * a)
                            "Phương trình có nghiệm kép: x = $x"
                        }
                        else -> {
                            val x1 = (-b + Math.sqrt(delta)) / (2 * a)
                            val x2 = (-b - Math.sqrt(delta)) / (2 * a)
                            "Phương trình có 2 nghiệm phân biệt:\nx1 = $x1\nx2 = $x2"
                        }
                    }
                }
            }
            binding.tvResult.text = result
        }

        binding.btnHuy.setOnClickListener {
            binding.edtSoA.text?.clear()
            binding.edtSoB.text?.clear()
            binding.edtSoC.text?.clear()
            binding.tvResult.text = ""
        }
    }
}