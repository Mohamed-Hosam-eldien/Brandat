package com.example.brandat.ui.fragments.productdetails

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentProductDetailsBinding
import com.example.brandat.models.Product
import com.example.brandat.ui.User
import com.example.brandat.viewmodels.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var random: Float = 4.5F
    private lateinit var mProduct: Product
    private var user: List<User> = listOf(
        User("Mohamed", "this product is so beautiful wwooooww", 3),
        User("Ahmed", "I really liked this product so awesome", 4),
        User("Galal", "Incredible product I bought it and I'll buy it again", 5),
        User("Salem", "it's fine when you wanna a bag for you school", 4),
        User("Noor", "I got one for my sister and she loves it so much", 3),
        User("Yasmeen", "I did't like it at all so bad material", 2)
    )

    var soso = "7782820643045"

    private val viewModel: ProductDetailsViewModel by viewModels()
    private val mAdapter by lazy { UserAdapter() }

    private lateinit var _binding: FragmentProductDetailsBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        random = (3..5).random().toFloat()
        binding.ratingBar.rating = random

        binding.shareBtn.setOnClickListener {
            shareProduct()
        }

        binding.backBtn.setOnClickListener {
            requireActivity().finish()
        }

        binding.favoriteBtn.setOnClickListener {


        }

        //7782820643045

        setupRecyclerView()

        var usersList : User? = user.asSequence().shuffled().find { true }
        val numberOfElements = 3

        val randomElements = user.asSequence().shuffled().take(numberOfElements).toList()
        mAdapter.setDatat(randomElements)



        observeShowData()

    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun shareProduct() {
        var shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "Mohamed Galal Elsheikh")
            this.type = "text/plain"
        }
        startActivity(shareIntent)
    }

    private fun observeShowData() {
        Log.i("TAG", "observeShowData: ")

        //7782820708581L
        viewModel.getProductDetailsFromDatabase(7782820708581L)
        viewModel.getProduct.observe(viewLifecycleOwner) {
            if (it != null) {
                showData(it.body())
            }
        }


    }

    private fun showData(body: Product?) {
        if (body != null) {

            //mProduct = body

            binding.productNameTv.text = body.productDetails.title
            binding.productPriceTv.text = body.productDetails.variants[0].price
            binding.description.text = body.productDetails.bodyHtml
            binding.oneSize.text = body.productDetails.options[0].values[0]
            when (body.productDetails.options[1].values[0]) {
                "blue" -> binding.colorView.setBackgroundColor(Color.BLUE)
                "red" -> binding.colorView.setBackgroundColor(Color.RED)
                "black" -> binding.colorView.setBackgroundColor(Color.BLACK)
                "yellow" -> binding.colorView.setBackgroundColor(Color.YELLOW)
            }


            val imageList = ArrayList<SlideModel>()
            imageList.add(SlideModel(body.productDetails.imageProducts[0].src))
            imageList.add(SlideModel(body.productDetails.imageProducts[1].src))
            imageList.add(SlideModel(body.productDetails.imageProducts[2].src))
            binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        }


    }
}