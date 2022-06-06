package com.example.brandat.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.brandat.databinding.ActivityOrderStatusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderStatus : AppCompatActivity() {

    private lateinit var binding : ActivityOrderStatusBinding

    private val stepList = arrayOf("Address","Payment Method","Verify")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // binding.stepView.statusView.setStatusList(stepList.toList())

//        binding.next.setOnClickListener{
//            if(binding.stepView.statusView.currentCount <= 2)
//                binding.stepView.statusView.currentCount = binding.stepView.statusView.currentCount + 1
//
//        }
//
//        binding.back.setOnClickListener{
//            if(binding.stepView.statusView.currentCount > 1)
//                binding.stepView.statusView.currentCount = binding.stepView.statusView.currentCount - 1
//
//        }

    }





}