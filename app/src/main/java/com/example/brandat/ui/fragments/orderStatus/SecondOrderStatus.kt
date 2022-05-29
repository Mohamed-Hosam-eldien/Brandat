package com.example.brandat.ui.fragments.orderStatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.brandat.R
import com.example.brandat.databinding.SecondOrderStateBinding

class SecondOrderStatus: Fragment() {

    lateinit var binding : SecondOrderStateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = LayoutInflater.from(container?.context).inflate(R.layout.second_order_state, null)
        binding = SecondOrderStateBinding.bind(view)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener{
            // nav to second screen

        }

    }

}