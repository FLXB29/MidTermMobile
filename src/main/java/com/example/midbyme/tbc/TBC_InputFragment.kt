package com.example.midbyme.tbc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.midbyme.R
import com.example.midbyme.databinding.FragmentFirstBinding
import com.example.midbyme.databinding.FragmentTBCInputBinding


class TBC_InputFragment : Fragment() {
    private lateinit var binding: FragmentTBCInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTBCInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDone.setOnClickListener {
            val input = binding.edtNum.text.toString()

            val listNum = input.split(" ").mapNotNull { it.trim().toIntOrNull() }

            val listPrime = listNum.filter { isPrime(it) }

            if(listPrime.isEmpty()){
                val bundle = Bundle().apply{
                    putString("res","Không có số nguyên tố nào")
                }
                findNavController().navigate(R.id.tbc_got_to_result,bundle)

            }
            else{
                val res = listPrime.average()
                val bundle = Bundle().apply{
                    putString("res",res.toString())
                }
                findNavController().navigate(R.id.tbc_got_to_result,bundle)
            }
        }
    }
    fun isPrime( x:Int): Boolean{
        if(x<2) return false
        for(i in 2.. Math.sqrt(x.toDouble()).toInt()){
            if(x%i==0) return false
        }
        return true
    }
}