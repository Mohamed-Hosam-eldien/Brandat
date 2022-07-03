package com.example.brandat.ui.fragments.serach

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.R
import com.example.brandat.databinding.FragmentSearchBinding
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.ProfileActivity
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.ui.fragments.newcategory.OnImageFavClickedListener
import com.example.brandat.ui.fragments.newcategory.ProductRvAdapter
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import com.example.brandat.utils.observeOnce
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

@AndroidEntryPoint
class SearchFragment : Fragment(), OnImageFavClickedListener {

    private val viewModel: SearchViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var iSnackBar: ISnackBar
    private lateinit var binding: FragmentSearchBinding
    private var itemList = emptyList<ProductDetails>()
    private lateinit var productRvAdapter: ProductRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            FragmentSearchBinding.bind(inflater.inflate(R.layout.fragment_search, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ConnectionUtil.isNetworkAvailable(requireContext())) {

            viewModel.getAllProduct()
            viewModel.liveSearch.observeOnce(viewLifecycleOwner) {
                itemList = it
            }

        } else {

            iSnackBar = requireActivity() as SearchActivity
            iSnackBar.showSnack()
            binding.animationView.visibility = View.VISIBLE
        }

        binding.imgBack.setOnClickListener { requireActivity().finish() }

        productRvAdapter = ProductRvAdapter(this, requireActivity())

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val list: MutableList<ProductDetails> = ArrayList()
                if (query != null) {
                    itemList.forEach {
                        if (it.title.contains(query.uppercase())
                            || it.handle.contains(query.uppercase())
                            || it.productType.contains(binding.search.query.toString().uppercase())
                            || it.vendor.contains(query.uppercase())
                            || it.variants?.get(0)?.price?.contains(query.uppercase())!!
                        ) {
                            list.add(it)
                        }
                    }
                    requireActivity().runOnUiThread {
                        setDataToAdapter(list)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        ConnectionUtil.registerConnectivityNetworkMonitor(
            requireContext(),
            viewModel,
            requireActivity()
        )
    }

    private fun setDataToAdapter(items: List<ProductDetails>) {
        productRvAdapter.setData(items)
        binding.recyclerSearch.apply {
            val layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            val scaleAdapter = ScaleInAnimationAdapter(productRvAdapter).apply {
                setDuration(825)
                setFirstOnly(false)
            }
            adapter = productRvAdapter
            adapter = scaleAdapter


            checkEmpty(items)
        }
    }

    private fun checkEmpty(items: List<ProductDetails>) {
        if (items.isNotEmpty()) {
            binding.recyclerSearch.visibility = View.VISIBLE
            binding.imgEmpty.visibility = View.GONE
        } else {
            binding.recyclerSearch.visibility = View.GONE
            binding.imgEmpty.visibility = View.VISIBLE
        }
    }

    override fun onItemClicked(currentProductId: Long) {
        val bundle = Bundle()
        bundle.putLong("productId", currentProductId)

        findNavController().navigate(
            R.id.action_searchFragment2_to_productDetailsFragment2,
            bundle
        )
    }

    override fun onFavClicked(favourite: ProductDetails, ivImage: ImageView) {
        saveFavToDraft(favourite, ivImage)
    }

    private fun addCartToDraft(currentProduct: Cart) {
        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("cart")
            .child(currentProduct.pId.toString())
            .setValue(currentProduct)
    }


    override fun onCartClicked(product: Cart, ivCart: ImageView) {
        if (Paper.book().read<String>("email") == null) {
            showDialog()
        } else {

            if (ivCart.tag != "done") {
                addCartToDraft(product)
                ivCart.tag = "done"
                cartViewModel.addProductToCart(product)
                ivCart.setImageResource(R.drawable.ic_baseline_done_green)
                ivCart.setBackgroundResource(R.drawable.cart_shape_back_done)

                if (Paper.book().read<Int>("count") != null) {
                    Paper.book().write("count", Paper.book().read<Int>("count")!! + 1)
                } else {
                    Paper.book().write("count", 1)
                }
            }
        }
    }

    override fun deleteFavourite(favouriteId: Long, ivFavorite: ImageView) {
        removeFromDraft(favouriteId, ivFavorite)
    }


    private fun saveFavToDraft(details: ProductDetails, ivImage: ImageView) {
        if (Paper.book().read<String>("email") == null) {
            showDialog()
        } else {
            FirebaseDatabase.getInstance()
                .getReference(Constants.user.id.toString())
                .child("fav")
                .child(details.id.toString())
                .setValue(details)
            ivImage.setImageResource(R.drawable.ic_favorite_filled)
        }
    }

    private fun removeFromDraft(favouriteId: Long, ivFavorite: ImageView) {
        if (Paper.book().read<String>("email") == null) {
            showDialog()
        } else {
            FirebaseDatabase.getInstance()
                .getReference(Constants.user.id.toString())
                .child("fav")
                .child(favouriteId.toString())
                .removeValue()
            ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
        }
    }
    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.login_now)) { _, _ ->
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
        }
        builder.setTitle(getString(R.string.login_to_add_to_cart))
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
    }

    override fun onResume() {
        super.onResume()
        if (binding.search.query != null) {
            val list: MutableList<ProductDetails> = ArrayList()
            itemList.forEach {

                if (it.title.contains(binding.search.query.toString().uppercase())
                    || it.handle.contains(binding.search.query.toString().uppercase())
                    || it.vendor.contains(binding.search.query.toString().uppercase())
                    || it.productType.contains(binding.search.query.toString().uppercase())
                    || it.variants?.get(0)?.price?.contains(binding.search.query.toString().uppercase())!!
                ) {

                    list.add(it)
                }
            }
            requireActivity().runOnUiThread {
                setDataToAdapter(list)
            }
        }
    }

}