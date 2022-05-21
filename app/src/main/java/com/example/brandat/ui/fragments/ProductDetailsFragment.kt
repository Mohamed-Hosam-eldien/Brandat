package com.example.brandat.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentProductDetailsBinding
import com.example.brandat.ui.User
import com.example.brandat.ui.UserAdapter

class ProductDetailsFragment : Fragment() {

    private var user : List<User> = listOf(User("mohamed","this product is so beautiful and useful")
        ,User("ahmed","it's fine when you wanna a bag for you school")
        ,User("hoasm","I did't like it at all so bad material"))

    private val mAdapter by lazy { UserAdapter() }

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
        binding.backBtn.setOnClickListener {
            requireActivity().finish()
        }

        binding.favoriteBtn.setOnClickListener {
            if(binding.favoriteBtn.tag.equals("1")){
                binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_filled)
                binding.favoriteBtn.tag = "0"
            }else{
                binding.favoriteBtn.tag = "1"
            }
        }


        setupRecyclerView()
        mAdapter.setDatat(user)

        return binding.root
    }
    private fun setupRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun sliderAds(){

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://cdn.shopify.com/s/files/1/0643/6637/9237/products/85cc58608bf138a50036bcfe86a3a362.jpg?v=1652442194"))
        imageList.add(SlideModel("https://cdn.shopify.com/s/files/1/0643/6637/9237/products/8a029d2035bfb80e473361dfc08449be.jpg?v=1652442194"))
        imageList.add(SlideModel("https://cdn.shopify.com/s/files/1/0643/6637/9237/products/ad50775123e20f3d1af2bd07046b777d.jpg?v=1652442194"))
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