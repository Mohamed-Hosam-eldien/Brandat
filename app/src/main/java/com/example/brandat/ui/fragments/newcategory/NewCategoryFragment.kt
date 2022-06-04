package com.example.brandat.ui.fragments.newcategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.CategoryViewModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentNewCategoryBinding
import com.example.brandat.databinding.FragmentProductBinding
import com.example.brandat.models.Product
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.category.IProduct
import com.example.brandat.ui.fragments.category.OnClickedListener
import com.example.brandat.ui.fragments.category.ProductRvAdapter
import com.example.brandat.ui.fragments.category.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewCategoryFragment : Fragment(), OnClickedListener {

    private lateinit var binding: FragmentNewCategoryBinding
    private lateinit var productRvAdapter: ProductRvAdapter
    private var productID:Long = 0

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var model:SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_new_category, container, false)

        binding = FragmentNewCategoryBinding.bind(view)

        binding.imgFilter.setOnClickListener {
            val bundle = Bundle()
            val list = ArrayList<String>()
            if(binding.chipCat.text.toString().isNotEmpty() && binding.chipCatSub.text.toString().isNotEmpty()) {
                list.add(binding.chipCat.text.toString())
                list.add(binding.chipCatSub.text.toString())
                bundle.putStringArrayList("chipList", list)
            }

            findNavController().navigate(R.id.action_newCategoryFragment_to_categoryBottomSheet, bundle)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        val cat = binding.chipCat.text
        val subCat = binding.chipCatSub.text

        if(cat=="Men" && subCat == "Shoes") {
            productID = 395728126181L
        } else if(cat=="Women" && subCat == "Shoes") {
            productID = 395728158949L
        } else if(cat=="Kids" && subCat == "Shoes") {
            productID = 395728191717L
        } else if(cat=="Sale" && subCat == "Shoes") {
            productID = 395728224485L
        }

        viewModel.getCategory(productID)
        viewModel.categoryResponse.observe(requireActivity()) {
            val products: ArrayList<ProductDetails> = ArrayList()
            for(product in it.body()?.products!!) {
                products.add(product)
            }
            productRvAdapter.setData(products)
        }

        fetchDataFromApi()
    }


    private fun setUpRecyclerView() {
        binding.chipCat.text = "Men"
        binding.chipCatSub.text = "Shoes"
        binding.groupChip.visibility = View.VISIBLE

        productRvAdapter = ProductRvAdapter(this)

        binding.recyclerCategory.apply {
            val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            adapter = productRvAdapter
        }
    }

    private fun fetchDataFromApi() {
        model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        model.positionMutable.observe(viewLifecycleOwner, Observer {
            val cat = it[0]
            val subCat = it[1]

            Log.d("TAG", "onCreateVar: $cat /// $subCat")

            if(cat=="Men" && subCat == "Shoes") {
                productID = 395728126181L
                binding.chipCat.text = cat
                binding.chipCatSub.text = subCat
            } else if(cat=="Women" && subCat == "Shoes") {
                productID = 395728158949L
                binding.chipCat.text = cat
                binding.chipCatSub.text = subCat
            } else if(cat=="Kids" && subCat == "Shoes") {
                productID = 395728191717L
                binding.chipCat.text = cat
                binding.chipCatSub.text = subCat
            } else if(cat=="Sale" && subCat == "Shoes") {
                productID = 395728224485L
                binding.chipCat.text = cat
                binding.chipCatSub.text = subCat
            }

            viewModel.getCategory(productID)
            binding.groupChip.visibility = View.VISIBLE
        })

    }

    override fun onClicked(currentProduct: ProductDetails) {
        val bundle = Bundle()
        bundle.putLong("productId", currentProduct.id)

       findNavController().navigate(R.id.action_newCategoryFragment_to_productDetailsFragment, bundle)
    }

    override fun onCartClicked(currentProduct: Cart) {
        Toast.makeText(requireContext(), "Hi Azoza !", Toast.LENGTH_SHORT).show()
        viewModel.addProductToCart(currentProduct)
    }
}
