package com.example.brandat.ui.fragments.newcategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.CategoryViewModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentNewCategoryBinding
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
    private var productID: Long = 0
    private var type: String = ""
    private lateinit var brandName:String

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var model: SharedViewModel

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

        val view = inflater.inflate(R.layout.fragment_new_category, container, false)

        binding = FragmentNewCategoryBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        brandName = requireArguments().getString("brandId").toString()

        setUpRecyclerView()

        if (brandName != "null") {

            binding.chipCat.text = brandName
            binding.chipCatSub.text = "All"
            binding.groupChip.visibility = View.VISIBLE

            viewModel.getAllProductsByName()
            filterProducts()

            viewModel.productsLive.observe(viewLifecycleOwner) {
                val products: ArrayList<Product> = ArrayList()
                for (product in it.body()?.products!!) {
                    if (product.vendor == brandName.uppercase()) {
                        products.add(product)
                    }
                }

                if (products.size > 0)
                    binding.imgEmpty.visibility = View.GONE
                else
                    binding.imgEmpty.visibility = View.VISIBLE

                productRvAdapter.setData(products)
            }

        } else {

            binding.chipCat.text = "Men"
            binding.chipCatSub.text = "Shoes"
            binding.groupChip.visibility = View.VISIBLE

            when (binding.chipCat.text) {
                "Men" -> {
                    productID = 395728126181L
                }
                "Women" -> {
                    productID = 395728158949L
                }
                "Kids" -> {
                    productID = 395728191717L
                }
                "Sale" -> {
                    productID = 395728224485L
                }
            }

        viewModel.getCategory(productID)
        viewModel.categoryResponse.observe(requireActivity()) {
            val products: ArrayList<ProductDetails> = ArrayList()
            for(product in it.body()?.products!!) {
                products.add(product)
            }
            productRvAdapter.setData(products)
            viewModel.getCategory(productID)

            filterCategories()

            viewModel.categoryLive.observe(requireActivity()) {
                val products: ArrayList<Product> = ArrayList()
                for (product in it.body()?.products!!) {
                    if (product.productType == binding.chipCatSub.text.toString().uppercase()) {
                        products.add(product)
                    }
                }

                if (products.size > 0)
                    binding.imgEmpty.visibility = View.GONE
                else
                    binding.imgEmpty.visibility = View.VISIBLE

                productRvAdapter.setData(products)

            }
        }

        binding.imgFilter.setOnClickListener {

            if (brandName != "null") {

                val bundle = Bundle()
                if (binding.chipCatSub.text.toString().isNotEmpty()) {
                    bundle.putString("type", binding.chipCatSub.text.toString())
                }

                findNavController().navigate(R.id.action_newCategoryFragment_to_categoryBottomSheetType, bundle)

            } else {

                val bundle = Bundle()
                val list = ArrayList<String>()
                if (binding.chipCat.text.toString().isNotEmpty() && binding.chipCatSub.text.toString().isNotEmpty()) {
                    list.add(binding.chipCat.text.toString())
                    list.add(binding.chipCatSub.text.toString())
                    bundle.putStringArrayList("chipList", list)
                }

                findNavController().navigate(R.id.action_newCategoryFragment_to_categoryBottomSheet, bundle)
            }

        }

    }


    private fun setUpRecyclerView() {
        productRvAdapter = ProductRvAdapter(this)

        binding.recyclerCategory.apply {
            val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            adapter = productRvAdapter
        }
    }

    override fun onClicked(currentProduct: Product) {
        val bundle = Bundle()
        bundle.putLong("productId", currentProduct.id)
        findNavController().navigate(R.id.action_categoryFragment_to_productDetailsFragment, bundle)
    }

    private fun filterCategories() {
        model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        model.positionMutable.observe(viewLifecycleOwner, Observer {
            val cat = it[0]
            val subCat = it[1]

            when (cat) {
                "MEN" -> {
                    productID = 395728126181L
                    binding.chipCat.text = cat
                    binding.chipCatSub.text = subCat
                }
                "WOMEN" -> {
                    productID = 395728158949L
                    binding.chipCat.text = cat
                    binding.chipCatSub.text = subCat
                }
                "KIDS" -> {
                    productID = 395728191717L
                    binding.chipCat.text = cat
                    binding.chipCatSub.text = subCat
                }
                "SALE" -> {
                    productID = 395728224485L
                    binding.chipCat.text = cat
                    binding.chipCatSub.text = subCat
                }
            }

            viewModel.getCategory(productID)
            binding.groupChip.visibility = View.VISIBLE


        })

    }

    private fun filterProducts() {
        model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        model.typeMutable.observe(viewLifecycleOwner) {

            when (it) {
                "Shoes" -> {
                    type = "SHOES"
                }
                "T-Shirts" -> {
                    type = "T-SHIRTS"
                }
                "Accessories" -> {
                    type = "ACCESSORIES"
                }
                "All" -> {
                    type = "ALL"
                }
            }

            Toast.makeText(requireContext(), type, Toast.LENGTH_SHORT).show()

            if(type == "ALL")
                viewModel.getAllProductsByName()
            else
                viewModel.getAllProductsByType(type)

            binding.chipCat.text = brandName
            binding.chipCatSub.text = type
            binding.groupChip.visibility = View.VISIBLE
        }

    }

    override fun onResume() {
        super.onResume()
        if(brandName == "null") {
            binding.chipCat.text = "Men"
            binding.chipCatSub.text = "Shoes"
        } else {
            binding.chipCat.text = brandName
            binding.chipCatSub.text = "All"
        }
    }



}
