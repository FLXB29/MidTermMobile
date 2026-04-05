package com.example.Phuc_29.pt_bac1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.Phuc_29.databinding.FragmentPT1Binding


class PT1Fragment : Fragment() {
    private lateinit var binding: FragmentPT1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPT1Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{

            btnTinh.setOnClickListener {
                val soA= edtSoA.text.toString().toDoubleOrNull()
                val soB= edtSoB.text.toString().toDoubleOrNull()
                if(soA==null || soB==null){
                    Toast.makeText(requireContext(),"Vui lòng nhập đầy đủ các số", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val res = when{
                    soA == 0.0 && soB ==0.0 -> "Phương trình vô số nghiệm"
                    soA==0.0 -> "Phương trình vô nghiệm"
                    else->{
                        val x = -soB/soA
                        "Nghiệm x = $x"
                    }
                }
                Toast.makeText(requireContext(),res, Toast.LENGTH_SHORT).show()
            }
        }
    }
}