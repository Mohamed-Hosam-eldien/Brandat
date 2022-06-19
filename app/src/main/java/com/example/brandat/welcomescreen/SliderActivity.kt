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

//    companion object{
//        lateinit var viewPager:ViewPager
//    }
//
//    private lateinit var mAdapter: SliderPagerAdapter
//    private var _binding:ActivitySliderBinding?=null
//    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)
//
//        _binding = ActivitySliderBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
//
//        viewPager = findViewById(R.id.view_pager_vp)
//
//        mAdapter = SliderPagerAdapter(this)
//        viewPager.adapter = mAdapter
//
//
//        if (isOpenAlread() == true) {
//
//            val intent = Intent(this, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//
//        } else {
//
//            val editor = getSharedPreferences("slide", MODE_PRIVATE).edit()
//            editor.putBoolean("slide", true)
//            editor.commit()
//
//        }


    }

    private fun isOpenAlread(): Boolean? {
        val sharedPreferences =
            getSharedPreferences("slide", MODE_PRIVATE)
        return sharedPreferences.getBoolean("slide", false)

    }
}