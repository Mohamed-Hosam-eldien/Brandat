package com.example.brandat.ui.fragments.orderStatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.FirstOrderStatusBinding

class FirstOrderStatus: Fragment() {

    lateinit var binding : FirstOrderStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = LayoutInflater.from(container?.context).inflate(R.layout.first_order_status, null)
        binding = FirstOrderStatusBinding.bind(view)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btnNext.setOnClickListener{
//            // nav to second screen
//            findNavController().navigate(R.id.action_firstOrderStatus_to_secondOrderStatus)
//        }

    }

}