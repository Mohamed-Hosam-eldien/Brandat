package com.example.brandat.ui.fragments.category

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.brandat.R
import com.example.brandat.databinding.FragmentCategoryBinding
import com.example.brandat.models.Product
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.abs

@AndroidEntryPoint
class CategoryFragment : Fragment(), OnClickedListener {

    private lateinit var binding: FragmentCategoryBinding
    private val iPosition: IProduct = ProductFragment()


    //slider
    private lateinit var sliderAdapter: SliderCategoryAdapter
    private lateinit var viewPager2: ViewPager2
    private val categories: ArrayList<CategoryModel> = ArrayList()

    //viewpager+tabs
    private lateinit var tabLayout: TableLayout
    private lateinit var tabsViewPager: ViewPager2

//    val model : SharedViewModel by viewModels()

    lateinit var model : SharedViewModel

    //connect Category With Tabs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Navigation.findNavController(view!!).navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, pressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(LayoutInflater.from(context), container, false)

        val args: Bundle = requireArguments()
        val brandId = args.getLong("brandId")
        Toast.makeText(requireContext(), "brandId $brandId", Toast.LENGTH_SHORT).show()

        return binding.root
    }

    //=================================================================
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categories.add(CategoryModel("Shoes", R.drawable.shoes))
        categories.add(CategoryModel("T_Shirts", R.drawable.shirt))
        categories.add(CategoryModel("Accessories", R.drawable.bag))

        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)


        viewPager2 = binding.viewPagerCategories
        tabsViewPager = binding.viewPagerSubCategory
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
            when {
                position < -1 -> {
                    page.translationX = -myOffset
                }
                position <= 1 -> {
                    val scaleFactor = Math.max(0.7f, 1 - abs(position - 0.14285715f))
                    Log.d(TAG, "transformPageVALUE: $scaleFactor")
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                }
                else -> {
                    page.alpha = 0f
                    page.translationX = myOffset
                }
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

        binding.viewPagerCategories.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
          //      Toast.makeText(requireContext(), ""+position, Toast.LENGTH_SHORT).show()
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })


        binding.viewPagerSubCategory.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
//                iPosition.getPosition(position,requireContext())
                model.setPosition(position)
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

    }

    //=================================================================
    private fun initializeTabLayout() {
        tabsViewPager.adapter = ProductViewPagerAdapter(requireActivity())
        TabLayoutMediator(binding.tablayout, binding.viewPagerSubCategory) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    "Kid"
                }
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

    override fun onClicked(currentProduct: Product) {
        findNavController().navigate(R.id.action_categoryFragment_to_productDetailsFragment)
    }


}