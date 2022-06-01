package com.example.brandat.ui.fragments.category

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ProductViewPagerAdapter(fragmentActivity:FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount()=4

    override fun createFragment(position: Int): Fragment {
        return ProductFragment()
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("TAG", "getItemId: asdasd ${position}")
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {

        return super.getItemId(position)
    }


}