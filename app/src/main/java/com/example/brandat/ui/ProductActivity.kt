package com.example.brandat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brandat.R
import com.example.brandat.databinding.ActivityMainBinding
import com.example.brandat.databinding.ActivityProductBinding
import com.example.brandat.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}