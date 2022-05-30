package com.example.brandat.ui.fragments.category

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.brandat.R
import com.example.brandat.databinding.FragmentCategoryBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.Math.abs

class CategoryFragment : Fragment() ,OnClickedListener {

    private lateinit var binding: FragmentCategoryBinding

    //slider
    private lateinit var sliderAdapter: SliderCategoryAdapter
    private lateinit var viewPager2: ViewPager2
    private val categories: ArrayList<CategoryModel> = ArrayList()

    //viewpager+tabs
    private lateinit var tabLayout: TableLayout
    private lateinit var tabsViewPager: ViewPager2


    //connect Category With Tabs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    //=================================================================
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categories.add(CategoryModel("Dresses", R.drawable.sh2))
        categories.add(CategoryModel("T_Shirts", R.drawable.dress_kid))
        categories.add(CategoryModel("Shooes", R.drawable.bag))
        categories.add(CategoryModel("Accessories", R.drawable.sh3))
        categories.add(CategoryModel("Dresses", R.drawable.dress_kid))
        categories.add(CategoryModel("Dresses", R.drawable.sh5))


        viewPager2 = binding.viewPagerCategories
        tabsViewPager=binding.viewPagerSubCategory
        initializeSlider()
        sliderWork()
        initializeTabLayout()

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->

            val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
            val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

            val TAG = "VIEWPAGER_POS"
            val myOffset: Float = position * -(2 * pageOffset + pageMargin)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) {
                val scaleFactor = Math.max(0.7f, 1 - abs(position - 0.14285715f))
                Log.d(TAG, "transformPageVALUE: $scaleFactor")
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0f
                page.translationX = myOffset
            }
        }

        binding.viewPagerCategories.apply {
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(compositePageTransformer)
        }
    }
//=================================================================
    private fun sliderWork() {
//binding.viewPagerCategories.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//    }
//
//    override fun onPageSelected(position: Int) {
//        super.onPageSelected(position)
//    }
//
//    override fun onPageScrollStateChanged(state: Int) {
//        super.onPageScrollStateChanged(state)
//    }
//}
    }
//=================================================================
    private fun initializeTabLayout() {
        tabsViewPager.adapter = ProductViewPagerAdapter(requireActivity())
        TabLayoutMediator(binding.tablayout, binding.viewPagerSubCategory) { tab, index ->
            tab.text = when (index) {
                0 -> "Kid"
                1 -> "Women"
                2 -> "Men"
                3 -> "sale"
                else -> {
                    throw Resources.NotFoundException("Position Not Found")
                }
            }
        }.attach()
    }

    private fun initializeSlider() {
        sliderAdapter = SliderCategoryAdapter(categories, viewPager2)
        viewPager2.adapter = sliderAdapter


    }

    override fun onClicked(currentProduct: ProductModel) {
        findNavController().navigate(R.id.action_categoryFragment_to_productDetailsFragment)

    }


}