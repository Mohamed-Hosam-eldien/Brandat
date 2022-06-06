package com.example.brandat.ui.fragments.category

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.CategoryViewModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentProductBinding
import com.example.brandat.models.Product
import com.example.brandat.models.ProductDetails
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductFragment : Fragment(), IProduct {

    private lateinit var productRvAdapter: ProductRvAdapter
    private lateinit var binding: FragmentProductBinding
    private val products: ArrayList<Product> = ArrayList()
    private var productID = 395728191717L

    //val model : SharedViewModel by viewModels()


    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        products.add(ProductModel("CLASSIC BACKPACK", "379 Eg", R.drawable.sh5))
//        products.add(ProductModel(" BACKPACK", "3700 Eg", R.drawable.sh3))
//        products.add(ProductModel("CLASSIC", "3 Eg", R.drawable.sh2))
//        products.add(ProductModel("Dress", "370 Eg", R.drawable.sh))
//        products.add(ProductModel("CLASSIC BACKPACK", "379 Eg", R.drawable.dress_kid))


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(LayoutInflater.from(context), container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        }

        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    override fun getCategories(category: String, subCategory: String) {
        TODO("Not yet implemented")
    }

    //model.positionMutable.observe(viewLifecycleOwner, Observer {
//            when(it) {
//
//                0 -> {
//                    productID = 395728191717L
//                }
//                1 -> {
//                    productID = 395728158949L
//                }
//                2 -> {
//                    productID = 395728126181L
//                }
//                3 -> {
//                    productID = 395728224485L
//                }
//
//            }
//
//            viewModel.getCategory(productID)
//
//        })

    }

