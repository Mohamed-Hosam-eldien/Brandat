package com.example.brandat.ui.fragments.newcategory

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.CategoryViewModel
import com.example.brandat.R
import com.example.brandat.databinding.CategoryBottomSheetBinding
import com.example.brandat.databinding.CategoryBottomSheetTypeBinding
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
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import com.example.brandat.utils.observeOnce
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper


@AndroidEntryPoint
class NewCategoryFragment : Fragment(), OnImageFavClickedListener {

    private lateinit var binding: FragmentNewCategoryBinding
    private lateinit var productRvAdapter: ProductRvAdapter
    private var productID: Long = 0
    private var brandName: String = "null"
    private lateinit var bageCountI: IBadgeCount
    private val viewModel: CategoryViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bageCountI = requireActivity() as MainActivity
        showShimmerEffect()

        if (ConnectionUtil.isNetworkAvailable(requireContext())) {

            brandName = requireArguments().getString("brandId").toString()

            setUpRecyclerView()

            if (brandName != "null") {

                binding.chipCat.text = brandName
                binding.chipCatSub.text = "All"
                binding.groupChip.visibility = View.VISIBLE

                observeAllProductData("ALL")

            } else {

                binding.chipCat.text = "Men"
                binding.chipCatSub.text = "Shoes"
                binding.groupChip.visibility = View.VISIBLE
                productID = 395964875010L

                observeCategoryData()
            }

            binding.imgFilter.setOnClickListener {
                if (brandName != "null") {
                    showAllProductFilterDialog()
                } else {
                    showCategoryFilterDialog()
                }
            }

        } else {
            showMessage("no Connection")
            hideShimmerEffect()
            binding.animationView.visibility = View.VISIBLE
        }

        ConnectionUtil.registerConnectivityNetworkMonitor(
            requireContext(),
            viewModel,
            requireActivity()
        )

    }

    private fun observeCategoryData() {
        viewModel.categoryResponse = MutableLiveData()
        viewModel.getCategory(productID)

        viewModel.categoryResponse.observeOnce(requireActivity()) { it ->
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
    }

    private fun observeAllProductData(type: String) {
        viewModel.allProductResponse = MutableLiveData()
        viewModel.getAllProductsByType(type)

        viewModel.allProductResponse.observeOnce(viewLifecycleOwner) {
            val products: ArrayList<ProductDetails> = ArrayList()

            it.forEach { product ->
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
            7706245759234 -> "220"
            7706245562626 -> "99.95"
            7706242416898 -> "129.95"
            7706242154754 -> "169.95"
            7706241630466 -> "99.95"
            7706241335554 -> "119.95"
            7706241302786 -> "129.95"
            7706241106178 -> "169.95"
            7706248315138 -> "110"
            7706241433858 -> "40"
            7706242515202 -> "40"
            7706246316290 -> "40"
            7706243006722 -> "29.99"
            7706247102722 -> "29.99"
            7706242023682 -> "29.99"
            7706241532162 -> "249"
            7706241499394 -> "229"
            7706242547970 -> "229"
            7706242646274 -> "249"
            7706242613506 -> "229"
            7706241466626 -> "229"
            7706242908418 -> "19.95"
            7706241892610 -> "19.75"

            7706241859842 -> "90"
            7706242875650 -> "90"
            7706242777346 -> "70"
            7706241728770 -> "70"
            7706242842882 -> "70"
            7706241794306 -> "70"

            else -> "0"
        }
    }

    private fun hideShimmerEffect() {
        binding.shimmerRecycle.hideShimmerAdapter()
        binding.shimmerRecycle.visibility = View.GONE
        binding.recyclerCategory.visibility = View.VISIBLE
    }

    private fun showMessage(it: String) {

        Snackbar.make(requireView(), it, Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
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
        productRvAdapter = ProductRvAdapter(this, requireActivity())

        binding.recyclerCategory.apply {
            val layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            adapter = productRvAdapter
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
        saveFavToDraft(details, ivImage)
    }

    override fun deleteFavourite(favouriteId: Long, ivFavorite: ImageView) {
        removeFromDraft(favouriteId, ivFavorite)
    }

    override fun onCartClicked(currentProduct: Cart, ivCart: ImageView) {
        if (Paper.book().read<String>("email") == null) {
            showDialog()
        } else {

            if (ivCart.tag != "done") {
                addCartToDraft(currentProduct)
                ivCart.tag = "done"
                cartViewModel.addProductToCart(currentProduct)
                ivCart.setImageResource(R.drawable.cart_done)
                ivCart.setBackgroundResource(R.drawable.cart_shape_back_done)

                if (Paper.book().read<Int>("count") != null) {
                    Paper.book().write("count", Paper.book().read<Int>("count")!! + 1)
                } else {
                    Paper.book().write("count", 1)
                }
                bageCountI.updateBadgeCount(Paper.book().read<Int>("count")!!)
            }
        }
    }

    private fun saveFavToDraft(details: ProductDetails, ivImage: ImageView) {
        if (Paper.book().read<String>("email") == null) {
            showDialog()
        } else {
            listener
                .child("fav")
                .child(details.id.toString())
                .setValue(details)
            ivImage.setImageResource(R.drawable.ic_favorite_filled)
            ivImage.tag = "favorite"
        }
    }

    private fun removeFromDraft(favouriteId: Long, ivFavorite: ImageView) {
        if (Paper.book().read<String>("email") == null) {
            showDialog()
        } else {
            listener
                .child("fav")
                .child(favouriteId.toString())
                .removeValue()
            ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
            ivFavorite.tag = "unfavorite"
        }
    }

    private fun addCartToDraft(currentProduct: Cart) {
        listener.child("cart")
            .child(currentProduct.pId.toString())
            .setValue(currentProduct)
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Login Now") { _, _ ->
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        builder.setNegativeButton("cancel") { _, _ -> }

        builder.setTitle("please register or login to add item in cart")
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
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

    private fun showAllProductFilterDialog() {
        val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val view: View = layoutInflater.inflate(R.layout.category_bottom_sheet_type, null)
        val bindingBottom = CategoryBottomSheetTypeBinding.bind(view)
        bindingBottom.root
        bottomSheet.setContentView(view)

        var type = binding.chipCatSub.text.toString()

        when (type) {
            "SHOES" -> bindingBottom.chipGroupSub.check(R.id.chipShoes)
            "T-SHIRTS" -> bindingBottom.chipGroupSub.check(R.id.chipShirt)
            "ACCESSORIES" -> bindingBottom.chipGroupSub.check(R.id.chipAccess)
            "ALL" -> bindingBottom.chipGroupSub.check(R.id.chipAll)
        }

        bindingBottom.chipGroupSub.setOnCheckedChangeListener { group, selectedChipId ->
            type = group.findViewById<Chip>(selectedChipId).text.toString()
        }

        bindingBottom.btnApply.setOnClickListener {
            binding.recyclerCategory.visibility = View.GONE
            filterProductWithType(type)
            bottomSheet.dismiss()
        }

        bottomSheet.show()
    }

    private fun filterProductWithType(selectedType: String) {
        var type = ""
        when (selectedType) {
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

        binding.chipCat.text = brandName
        binding.chipCatSub.text = type
        binding.groupChip.visibility = View.VISIBLE

        observeAllProductData(type)
    }

    private fun showCategoryFilterDialog() {
        val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val view: View = layoutInflater.inflate(R.layout.category_bottom_sheet, null)
        val bindingBottom = CategoryBottomSheetBinding.bind(view)
        bindingBottom.root
        bottomSheet.setContentView(view)

        var type = binding.chipCat.text.toString()
        var subType = binding.chipCatSub.text.toString()

        when (type) {
            "Men" -> bindingBottom.chipGroupCategory.check(R.id.chipMen)
            "Women" -> bindingBottom.chipGroupCategory.check(R.id.chipWomen)
            "Kids" -> bindingBottom.chipGroupCategory.check(R.id.chipKids)
            "Sale" -> bindingBottom.chipGroupCategory.check(R.id.chipSale)
        }

        when (subType) {
            "Shoes" -> bindingBottom.chipGroupSub.check(R.id.chipShoes)
            "T-Shirts" -> bindingBottom.chipGroupSub.check(R.id.chipShirt)
            "Accessories" -> bindingBottom.chipGroupSub.check(R.id.chipAccess)
        }

        bindingBottom.chipGroupCategory.setOnCheckedChangeListener { group, selectedChipId ->
            type = group.findViewById<Chip>(selectedChipId).text.toString()
        }

        bindingBottom.chipGroupSub.setOnCheckedChangeListener { group, selectedChipId ->
            subType = group.findViewById<Chip>(selectedChipId).text.toString()
        }

        bindingBottom.btnApply.setOnClickListener {
            filterCategory(type, subType)
            bottomSheet.dismiss()
        }

        bottomSheet.show()
    }

    private fun filterCategory(type: String, subType: String) {

        when (type) {
            "Men" -> {
                productID = 395964875010L
                binding.chipCat.text = type
                binding.chipCatSub.text = subType
            }
            "Women" -> {
                productID = 395963629826L
                binding.chipCat.text = type
                binding.chipCatSub.text = subType
            }
            "Kids" -> {
                productID = 395963662594L
                binding.chipCat.text = type
                binding.chipCatSub.text = subType
            }
            "Sale" -> {
                productID = 395963695362L
                binding.chipCat.text = type
                binding.chipCatSub.text = subType
            }
        }

        viewModel.getCategory(productID)
        binding.groupChip.visibility = View.VISIBLE
        observeCategoryData()
    }
}
