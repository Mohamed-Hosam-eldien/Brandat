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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductFragment : Fragment(),OnClickedListener, IProduct {

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

        setUpRecyclerView()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.categoryResponse.observe(requireActivity()) {
            for(product in it.body()?.products!!) {
                products.add(product)
                Log.d("TAG", "Product -->  ")
            }
            productRvAdapter.setData(products)
        }

        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

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

    private fun setUpRecyclerView() {
        productRvAdapter = ProductRvAdapter(this)

        binding.rvProducts.apply {
            val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            //productRvAdapter.setData(products)
            adapter = productRvAdapter

        }
    }

    fun showShimmerEffect() {
       // binding.rvProducts
    }

    fun hideShimmerEffect() {

    }

    override fun onClicked(currentProduct: Product) {
        val bundle = Bundle()
        bundle.putLong("productId", currentProduct.id)
        Log.d("TAG", "onClicked: ray7 ${currentProduct.id}")
        findNavController().navigate(R.id.action_categoryFragment_to_productDetailsFragment, bundle)
    }


    override fun getCategories(category: String, subCategory: String) {

    }

}
