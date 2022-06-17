package com.example.brandat.ui.fragments.newcategory

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import com.example.brandat.models.Favourite
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.ProfileActivity
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.ui.fragments.cart.IBadgeCount
import com.example.brandat.ui.fragments.category.OnImageFavClickedListener
import com.example.brandat.ui.fragments.category.ProductRvAdapter
import com.example.brandat.ui.fragments.category.SharedViewModel
import com.example.brandat.ui.fragments.favorite.FavouriteViewModel
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants.Companion.count
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

@AndroidEntryPoint
class NewCategoryFragment : Fragment(), OnImageFavClickedListener {

    private lateinit var binding: FragmentNewCategoryBinding
    private lateinit var productRvAdapter: ProductRvAdapter
    private var productID: Long = 0
    private var type: String = ""
    private lateinit var brandName: String
    private lateinit var bageCountI: IBadgeCount
    private var isClicked: Boolean = false
    private val viewModel: CategoryViewModel by viewModels()
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

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
        Paper.init(requireContext())
        //  Paper.book().read<Int>("countFromCart")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bageCountI = requireActivity() as MainActivity
        if (ConnectionUtil.isNetworkAvailable(requireContext())) {
           showShimmerEffect()
            viewModel.getAllProductsByName()
                   filterProducts()
//            Timer("SettingUp", false).schedule(10000) {
//                requireActivity().runOnUiThread {
//
//                }
//            }

        }else{
            showMessage("no Connection")
            hideShimmerEffect()

        }

        brandName = requireArguments().getString("brandId").toString()
        setUpRecyclerView()
        observeOnFavourite()

        if (brandName != "null") {

            binding.chipCat.text = brandName
            binding.chipCatSub.text = "All"
            binding.groupChip.visibility = View.VISIBLE



            Toast.makeText(requireContext(), "==$productID", Toast.LENGTH_SHORT).show()

//            viewModel.isOrderd.observe(viewLifecycleOwner) {
//                if(it != null) {
//                    isClicked=true
//                }
//            }
            viewModel.productsLive.observe(viewLifecycleOwner) {
                val products: ArrayList<ProductDetails> = ArrayList()
                for (product in it) {
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
                    productID = 395964875010L
                }
                "Women" -> {
                    productID = 395963629826L
                }
                "Kids" -> {
                    productID = 395963662594L
                }
                "Sale" -> {
                    productID = 395963695362L
                }
            }

            filterCategories()

            viewModel.getCategory(productID)
            viewModel.categoryResponse.observe(requireActivity()) {
                val products: ArrayList<ProductDetails> = ArrayList()
                Log.e("TAG", "onViewCategoryCreated:${it} ")

                for (product in it) {
                    if (product.productType == binding.chipCatSub.text.toString().toUpperCase())
                        products.add(product)
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

                findNavController().navigate(
                    R.id.action_newCategoryFragment_to_categoryBottomSheetType,
                    bundle
                )

            } else {

                val bundle = Bundle()
                val list = ArrayList<String>()

                if (binding.chipCat.text.toString()
                        .isNotEmpty() && binding.chipCatSub.text.toString().isNotEmpty()
                ) {
                    list.add(binding.chipCat.text.toString())
                    list.add(binding.chipCatSub.text.toString())
                    bundle.putStringArrayList("chipList", list)
                }

                findNavController().navigate(
                    R.id.action_newCategoryFragment_to_categoryBottomSheet,
                    bundle
                )
            }

        }
        ConnectionUtil.registerConnectivityNetworkMonitor(requireContext(),viewModel, requireActivity())

    }

    private fun hideShimmerEffect() {
        binding.shimmerRecycle.hideShimmerAdapter()
        binding.shimmerRecycle.visibility = View.GONE
        binding.recyclerCategory.visibility=View.VISIBLE
    }

    private fun showMessage(it: String) {

        //  snackbar = Snackbar.make(requireView(), it, Snackbar.LENGTH_INDEFINITE)
        Snackbar.make(requireView(), it, Snackbar.LENGTH_INDEFINITE).
        setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
            resources.getColor(
                R.color.black2
            )
        )
            .setActionTextColor(resources.getColor(R.color.white)).setAction("Close") {
            }.show()


    }

    private fun showShimmerEffect() {
        binding.shimmerRecycle.showShimmerAdapter()
        binding.shimmerRecycle.visibility = View.VISIBLE
        binding.recyclerCategory.visibility = View.GONE
    }


    private fun setUpRecyclerView() {
        productRvAdapter = ProductRvAdapter(this)

        binding.recyclerCategory.apply {
            val layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            adapter = productRvAdapter
        }
    }

    private fun filterCategories() {
        model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        model.positionMutable.observe(viewLifecycleOwner, Observer {
            val cat = it[0]
            val subCat = it[1]

            when (cat) {
                "Men" -> {
                    productID = 395964875010L
                    binding.chipCat.text = cat
                    binding.chipCatSub.text = subCat
                }
                "Women" -> {
                    productID = 395963629826L
                    binding.chipCat.text = cat
                    binding.chipCatSub.text = subCat
                }
                "Kids" -> {
                    productID = 395963662594L
                    binding.chipCat.text = cat
                    binding.chipCatSub.text = subCat
                }
                "Sale" -> {
                    productID = 395963695362L
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


            if (type == "ALL")
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
        if (brandName == "null") {
            binding.chipCat.text = "Men"
            binding.chipCatSub.text = "Shoes"
        } else {
            binding.chipCat.text = brandName
            binding.chipCatSub.text = "All"
        }
    }


    override fun onItemClicked(currentProductId: Long) {
        val bundle = Bundle()
        bundle.putLong("productId", currentProductId)

        findNavController().navigate(
            R.id.action_newCategoryFragment_to_productDetailsFragment,
            bundle
        )

    }

    override fun onFavClicked(favourite: Favourite, ivImage: ImageView) {
        favouriteViewModel.insertFavouriteProduct(favourite)
    }

    override fun deleteFavourite(favouriteId: Long) {
        favouriteViewModel.removeFavouriteProduct(favouriteId)
    }

    override fun onCartClicked(currentProduct: Cart) {
        // isClicked=true
        println("===id===${currentProduct.pId}")
        if (Paper.book().read<String>("email") == null) {
            showDialog()
        } else {
            // print("==========$isClicked")
            cartViewModel.addProductToCart(currentProduct)
            bageCountI.updateBadgeCount(count++)
            Paper.book().write("CountfromHome", count)
            //cartViewModel.isAdded(currentProduct.pName)
//            if (!isClicked) {
//                print("======clicked====$isClicked")
//
//                isClicked = true
//            }
//            if (!isClicked) {
//
//                isClicked=true
//            }
        }
    }
    //======================
    // viewModel.isOrderd(currentProduct.pId)
//            if (!isClicked){
//                print("======clicked====$isClicked")
//                cartViewModel.addProductToCart(currentProduct)
//                Paper.book().write("CountfromHome", count++)
//                bageCountI.updateBadgeCount(count)
//                isClicked = true
//            }
//            else{
//                isClicked=true
//            }


//            Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
    // viewModel.isOrderd(currentProduct.pId)
    // print("d3d3$isClicked")
//            if (!isClicked) {//false
//                Paper.book().write("CountfromHome", ++count)
//                bageCountI.updateBadgeCount(count)
//               // isClicked = true
//            }

    //showSnackBar(currentProduct)


    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Login Now") { _, _ ->
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        builder.setNegativeButton("cancel") { _, _ ->
        }
        builder.setTitle("please register or login to add item in cart")
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
    }

    private fun observeOnFavourite() {
        favouriteViewModel.getFavouriteProducts()
        favouriteViewModel.getFavouriteProducts.observe(viewLifecycleOwner) {
            if (it != null) {
                productRvAdapter.setFavData(it)
            }
        }
    }

    private fun showSnackBar(cart: Cart) {
        val snackbar = Snackbar.make(binding.newCat, "Added to Cart", Snackbar.LENGTH_LONG)
        snackbar.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setBackgroundTint(resources.getColor(R.color.black2))
            .setActionTextColor(resources.getColor(R.color.white)).setAction("Undo") {
                cartViewModel.removeProductFromCart(cart)
                Toast.makeText(requireContext(), "Removed from Cart!", Toast.LENGTH_SHORT).show()
            }.show()
    }
}
