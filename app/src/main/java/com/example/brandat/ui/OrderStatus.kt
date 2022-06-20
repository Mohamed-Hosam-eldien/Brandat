package com.example.brandat.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.brandat.databinding.ActivityOrderStatusBinding
import com.example.brandat.ui.fragments.orderStatus.IChangeOrderStatus
import com.example.brandat.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderStatus : AppCompatActivity(), IChangeOrderStatus {

    private lateinit var binding : ActivityOrderStatusBinding
    private val stepList = arrayOf("Address","Payment Method","Done")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var  intent =intent

        if (intent!=null) {
            Constants.totalPrice = intent.getStringExtra("total")?.toDouble()
        }
        binding = ActivityOrderStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.stepView.statusView.setStatusList(stepList.toList())
        binding.imgBack.setOnClickListener { finish() }


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

    override fun changeStatus(count: Int) {
        binding.stepView.statusView.currentCount = count + 1
    }

}