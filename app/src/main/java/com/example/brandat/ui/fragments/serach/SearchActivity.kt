package com.example.brandat.ui.fragments.serach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brandat.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }


}