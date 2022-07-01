package com.example.brandat.welcomescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.brandat.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SliderFragment : Fragment() {

    companion object{
        lateinit var viewPager: ViewPager
    }
    lateinit var adapter:SliderPagerAdapter

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
    }
}