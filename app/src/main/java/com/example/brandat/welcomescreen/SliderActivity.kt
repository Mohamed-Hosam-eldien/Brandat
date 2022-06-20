package com.example.brandat.welcomescreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.Pager
import androidx.viewpager.widget.ViewPager
import com.example.brandat.R
import com.example.brandat.databinding.ActivitySliderBinding
import com.example.brandat.ui.MainActivity
import io.paperdb.Paper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)

    }
}