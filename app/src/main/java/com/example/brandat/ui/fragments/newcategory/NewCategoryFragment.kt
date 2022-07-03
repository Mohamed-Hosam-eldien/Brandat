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
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import com.example.brandat.utils.observeOnce
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter


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
                productID = 397089505538L

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
                    Log.d("TAG", "observeCategoryData: ${it.id}, ${it.title}")
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
            //men shoes
            7727816999170 -> "109.95"
            7727817064706 -> "129.95"
            7727817294082 -> "299.95"
            7727816605954 -> "159.95"
            7727817687298 -> "140.00"
            7727816278274 -> "29.00"
            7727816900866 -> "170.00"
            7727816671490 -> "220.00"
            7727816540418 -> "179.95"
            7727816409346 -> "109.95"
            7727816737026 -> "110.00"
            7727816802562 -> "169.95"
            7727817163010 -> "129.95"
            7727816343810 -> "99.95"
            7727817228546 -> "119.95"
            7727817621762 -> "100.00"
            7727816474882 -> "119.95"
            //men accessory
            7727818047746 -> "29.99"
            7727817916674 -> "19.95"
            //women shoes
            7727817556226 -> "249.00"
            7727817490690 -> "229.00"
            7727817457922 -> "229.00"
            //kids shoes
            7727817851138 -> "90.00"
            7727817818370 -> "70.00"
            7727817752834 -> "70.00"

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

            val scaleAdapter = ScaleInAnimationAdapter(productRvAdapter).apply {
                setDuration(825)
                setFirstOnly(false)
            }

            adapter = scaleAdapter
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
                ivCart.setImageResource(R.drawable.ic_baseline_done_green)
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
                productID = 397089505538L
                binding.chipCat.text = type
                binding.chipCatSub.text = subType
            }
            "Women" -> {
                productID = 397089538306L
                binding.chipCat.text = type
                binding.chipCatSub.text = subType
            }
            "Kids" -> {
                productID = 397089571074L
                binding.chipCat.text = type
                binding.chipCatSub.text = subType
            }
            "Sale" -> {
                productID = 397089603842L
                binding.chipCat.text = type
                binding.chipCatSub.text = subType
            }
        }

        viewModel.getCategory(productID)
        binding.groupChip.visibility = View.VISIBLE
        observeCategoryData()
    }
}
