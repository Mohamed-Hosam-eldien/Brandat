package com.example.brandat.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentHomeBinding
import com.example.brandat.models.Brand
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class HomeFragment : Fragment(),BrandOnClickListner {

    lateinit var binding: FragmentHomeBinding
    lateinit var brandAdapter: BrandAdapter
    private lateinit var brands: List<Brand>
    private lateinit var bundle: Bundle
    private val brandViewModel: BrandViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initView()
        //showShimmerEffect()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        brandViewModel.getBrands()
        brandViewModel.brandResponse.observe(requireActivity()) {
            Log.e("TAG", "onViewCreated:${it.body()} ")
            brands = it.body()!!.brands

            brandAdapter.setData(brands)
        }
        imageSlider()


    }

    private fun imageSlider() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.img_1,ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.img_2,ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.img_3,ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.sh5,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.sh2,ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.shose_image,ScaleTypes.CENTER_CROP))


        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setImageList(imageList)
    }

    private fun initView() {

        brandAdapter = BrandAdapter(requireContext(),this)

        binding.brandsRecycler.apply {
            var layoutManager = GridLayoutManager(context, 2)
            setLayoutManager(layoutManager)
            adapter = brandAdapter

        }
    }
    private fun showShimmerEffect() {
        binding.shimmerRecycle.showShimmerAdapter()
        binding.shimmerRecycle.visibility = View.VISIBLE
        binding.brandsRecycler.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerRecycle.hideShimmerAdapter()
        binding.shimmerRecycle.visibility = View.GONE
        binding.brandsRecycler.visibility = View.VISIBLE
    }

    override fun onBrandClick(brandId: Long) {
        bundle = Bundle()
        bundle.putLong("brandId", brandId)
        findNavController().navigate(R.id.action_homeFragment_to_categoryFragment,bundle)
    }

}