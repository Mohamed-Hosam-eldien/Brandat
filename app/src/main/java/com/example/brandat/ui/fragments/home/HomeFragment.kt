package com.example.brandat.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

lateinit var  binding : FragmentHomeBinding
lateinit var brandAdapter: BrandAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageSlider()
        initView()

    }
    private fun imageSlider() {

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.slider_placeholder_image))
        imageList.add(SlideModel(R.drawable.slider_image_1))
        imageList.add(SlideModel(R.drawable.slider_image_2))
        imageList.add(SlideModel(R.drawable.slider_image_3))


         binding.imageSlider.setImageList(imageList)
         binding.imageSlider.setImageList(imageList)
    }

    private fun initView(){
        // when get data remove next comment
        // brandAdapter= BrandAdapter(arrayListOf())
        // and remove next line
        brandAdapter= BrandAdapter(fakeDataTestRecycle())
        binding.bradsRecycler.apply {
            var layoutManager = GridLayoutManager(context, 2)
            setLayoutManager(layoutManager)

            adapter= brandAdapter

        }
    }

    private  fun fakeDataTestRecycle() : ArrayList<BrandModel>{

        var fakeList  =ArrayList<BrandModel>()

            fakeList.add(BrandModel(R.drawable.img,"ZARA"))
        fakeList.add(BrandModel(R.drawable.addidas_logo,"Adidas"))
        fakeList.add(BrandModel(R.drawable.converse_logo,"CONVERSE"))
        fakeList.add(BrandModel(R.drawable.martener_logo,"DR MARTENS"))
        fakeList.add(BrandModel(R.drawable.asics_logo,"ASCS TIGER "))
        fakeList.add(BrandModel(R.drawable.img,"ZARA"))
        fakeList.add(BrandModel(R.drawable.converse_logo,"CONVERSE"))
        fakeList.add(BrandModel(R.drawable.martener_logo,"DR MARTENS"))
        fakeList.add(BrandModel(R.drawable.converse_logo,"CONVERSE"))
        fakeList.add(BrandModel(R.drawable.img,"ZARA"))













        return  fakeList

    }
}