package com.example.brandat.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment() {

    private lateinit var _binding : FragmentProductDetailsBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         _binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)

        sliderAds()

        binding.shareBtn.setOnClickListener {
            shareProduct()
        }

        return binding.root
    }

    private fun sliderAds(){

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://bit.ly/2YoJ77H", "The animal population decreased by 58 percent in 42 years."))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct."))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", "And people do that."))
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

    }

    private fun shareProduct(){
        var shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "Mohamed Galal Elsheikh")
            this.type = "text/plain"
        }
        startActivity(shareIntent)
    }
}