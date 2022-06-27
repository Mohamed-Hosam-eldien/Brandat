package com.example.brandat.ui.fragments.newcategory

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.CategoryViewModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentNewCategoryBinding
import com.example.brandat.models.ProductDetails
import com.example.brandat.models.Variant
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.ProfileActivity
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.ui.fragments.cart.IBadgeCount
import com.example.brandat.ui.fragments.category.OnImageFavClickedListener
import com.example.brandat.ui.fragments.category.ProductRvAdapter
import com.example.brandat.ui.fragments.category.SharedViewModel
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import com.example.brandat.utils.Constants.Companion.showDialogToRegister
import com.example.brandat.utils.observeOnce
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper


@AndroidEntryPoint
class NewCategoryFragment : Fragment(), OnImageFavClickedListener {

    private lateinit var binding: FragmentNewCategoryBinding
    private lateinit var productRvAdapter: ProductRvAdapter
    private var productID: Long = 0
    private var type: String = ""
    private var brandName: String = "null"
    private lateinit var bageCountI: IBadgeCount
    private val viewModel: CategoryViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private var mCount = 0
    private lateinit var model: SharedViewModel
    private val listener by lazy {
        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(requireActivity())

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
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bageCountI = requireActivity() as MainActivity
        showShimmerEffect()

        if (ConnectionUtil.isNetworkAvailable(requireContext())) {

            brandName = requireArguments().getString("brandId").toString()

            if (brandName != "null") {

                binding.chipCat.text = brandName
                binding.chipCatSub.text = "All"
                binding.groupChip.visibility = View.VISIBLE

                viewModel.getAllProductsByName()
                filterProducts()

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

                viewModel.getCategory(productID)
                viewModel.categoryLive
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

        } else {
            showMessage(getString(R.string.no_connection))
            hideShimmerEffect()
            binding.animationView.visibility = View.VISIBLE
        }
        filterCategories()
        viewModel.productsLive.observe(viewLifecycleOwner) {
            val products: ArrayList<ProductDetails> = ArrayList()

            for (product in it) {
                if (product.vendor == brandName.uppercase()) {
                    products.add(product)
                }
            }

            hideShimmerEffect()

            if (products.size > 0)
                binding.imgEmpty.visibility = View.GONE
            else
                binding.imgEmpty.visibility = View.VISIBLE

            productRvAdapter.setData(products)
        }
        viewModel.categoryResponse.observe(requireActivity()) { it ->
            val products: ArrayList<ProductDetails> = ArrayList()

            it.forEach {
                if (it.productType == binding.chipCatSub.text.toString().uppercase()) {
                    val price: String = setPrice(it.id)
                    it.variants = mutableListOf()
                    it.variants?.add(Variant(price = price))
                    products.add(it)
                }
            }


            hideShimmerEffect()

            if (products.size > 0)
                binding.imgEmpty.visibility = View.GONE
            else
                binding.imgEmpty.visibility = View.VISIBLE

            productRvAdapter.setData(products)
        }
        ConnectionUtil.registerConnectivityNetworkMonitor(
            requireContext(),
            viewModel,
            requireActivity()
        )

    }

    private fun setPrice(id: Long): String {
        return when (id) {
            7706240647426 -> "29"
            7706241564930 -> "29"
            7706242253058 -> "170"
            7706241138946 -> "170"
            7706242711810 -> "140"
            7706241827074 -> "179.95"
            7706246578434 -> "100"
            7706242449666 -> "119.95"
            7706242384130 -> "129.95"
            7706241597698 -> "100.00"
            7706241007874 -> "220.00"
            7706240844034 -> "119.95"
            7706241925378 -> "159.95"
            7706241270018 -> "129.95"
            7706240975106 -> "159.95"
            7706245955842 -> "170"
            7706242679042 -> "100"
            7706242482434 -> "299.95"
            7706241696002 -> "109.95"
            7706241663234 -> "140.00"
            7706241204482 -> "109.95"
            7706245988610 -> "109.95"
            7706245693698 -> "179.95"
            7706245529858 -> "29"
            7706241990914 -> "220"
            7706241761538 -> "119.95"
            7706241401090 -> "299.95"
            7706240811266 -> "109.95"
            7706240712962 -> "99.95"
            7706248151298 -> "195.95"
            7706248085762 -> "179.95"
            7706248020226 -> "119.95"
            7706247954690 -> "109.95"
            7706246250754 -> "119.95"
            7706246119682 -> "192.95"
            7706246054146 -> "129.95"
            7706245759234 -> "220.0"
            7706245562626 -> "99.95"
            7706242416898 -> "129.95"
            7706242154754 -> "169.95"
            7706241630466 -> "99.95"
            7706241335554 -> "119.95"
            7706241302786 -> "129.95"
            7706241106178 -> "169.95"
            7706248315138 -> "110.0"
            7706241433858 -> "40.00"
            7706242515202 -> "40.00"
            7706246316290 -> "40.00"
            7706243006722 -> "29.99"
            7706247102722 -> "29.99"
            7706242023682 -> "29.99"
            7706241532162 -> "249.0"
            7706241499394 -> "229.0"
            7706242547970 -> "229.0"
            7706242646274 -> "249.0"
            7706242613506 -> "229.0"
            7706241466626 -> "229.0"
            7706242908418 -> "19.95"
            7706241892610 -> "19.75"

            7706241859842 -> "90.00"
            7706242875650 -> "90.00"
            7706242777346 -> "70.00"
            7706241728770 -> "70.00"
            7706242842882 -> "70.00"
            7706241794306 -> "70.00"

            else -> "0.00"
        }
    }

    private fun hideShimmerEffect() {
        binding.shimmerRecycle.hideShimmerAdapter()
        binding.shimmerRecycle.visibility = View.GONE
        binding.recyclerCategory.visibility = View.VISIBLE
    }

    private fun showMessage(it: String) {

        Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
                resources.getColor(
                    R.color.black2
                )
            )
            .setActionTextColor(resources.getColor(R.color.white)).setAction(getString(R.string.close)) {
            }.show()
    }

    private fun showShimmerEffect() {
        binding.shimmerRecycle.showShimmerAdapter()
        binding.shimmerRecycle.visibility = View.VISIBLE
        binding.recyclerCategory.visibility = View.GONE
    }

    private fun setUpRecyclerView() {
        productRvAdapter = ProductRvAdapter(this, requireActivity())
        binding.recyclerCategory.apply {
            val layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            adapter = productRvAdapter
        }
    }

    private fun filterCategories() {
        model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        model.positionMutable.observeOnce(viewLifecycleOwner) {

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

        }

    }

    private fun filterProducts() {
        model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        model.typeMutable.observeOnce(viewLifecycleOwner) {

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
            binding.chipCat.text = getString(R.string.men)
            binding.chipCatSub.text = getString(R.string.shoes)
        } else {
            binding.chipCat.text = brandName
            binding.chipCatSub.text = getString(R.string.all)
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

    override fun onFavClicked(details: ProductDetails, ivImage: ImageView) {
//        favouriteViewModel.insertFavouriteProduct(favourite)
//        saveToDraft(details)
        saveFavToDraft(details, ivImage)
    }

    private fun saveFavToDraft(details: ProductDetails, ivImage: ImageView) {
        if (Paper.book().read<String>("email") == null) {
            showDialogToRegister(getString(R.string.login_to_add_to_cart))
        } else {
            listener
                .child("fav")
                .child(details.id.toString())
                .setValue(details)
            ivImage.setImageResource(R.drawable.ic_favorite_filled)
        }
    }

    private fun removeFromDraft(favouriteId: Long, ivFavorite: ImageView) {
        if (Paper.book().read<String>("email") == null) {
           // showDialog()
            showDialogToRegister(getString(R.string.login_to_add_to_cart))//it's imposible case!!
        } else {
            listener
                .child("fav")
                .child(favouriteId.toString())
                .removeValue()
            ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
        }
    }

    override fun deleteFavourite(favouriteId: Long, ivFavorite: ImageView) {
        removeFromDraft(favouriteId, ivFavorite)
    }

    override fun onCartClicked(currentProduct: Cart, ivCart: ImageView) {
        // isClicked=true
        if (Paper.book().read<String>("email") == null) {
            showDialogToRegister(getString(R.string.login_to_add_to_cart))
        } else {

            if (ivCart.tag != "done") {
                addCartToDraft(currentProduct)
                ivCart.tag = "done"
                cartViewModel.addProductToCart(currentProduct)
                ivCart.setImageResource(R.drawable.cart_done)
                ivCart.setBackgroundResource(R.drawable.cart_shape_back_done)
                /*bageCountI.updateBadgeCount(count++)
            cartViewModel.getAllCartProduct()
            cartViewModel.cartProduct.observe(viewLifecycleOwner) {
                count = it.size
                cartViewModel.addProductToCart(currentProduct)
                bageCountI.updateBadgeCount(count)
            }
            Paper.book().write("CountfromHome", count)*/

                if (Paper.book().read<Int>("count") != null) {
                    Paper.book().write("count", Paper.book().read<Int>("count")!! + 1)
                } else {
                    Paper.book().write("count", 1)
                }
                bageCountI.updateBadgeCount(Paper.book().read<Int>("count")!!)
            }
        }
    }


    private fun addCartToDraft(currentProduct: Cart) {
        listener.child("cart")
            .child(currentProduct.pId.toString())
            .setValue(currentProduct)
    }



}
