package com.example.brandat.welcomescreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.brandat.R
import com.example.brandat.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderFragment : Fragment() {


    companion object{
        lateinit var viewPager: ViewPager
    }
    lateinit var adapter:SliderPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.view_pager_vp)
        adapter = context?.let { SliderPagerAdapter(it) }!!
        viewPager.adapter = adapter
        if (isOpenAlread()) {

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        } else {

            val editor = context?.getSharedPreferences("slide", AppCompatActivity.MODE_PRIVATE)?.edit()
            editor?.putBoolean("slide", true)
            editor?.commit()
        }
    }
    private fun isOpenAlread(): Boolean {
        val sharedPreferences =
            context?.getSharedPreferences("slide", AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("slide", false)
    }
}