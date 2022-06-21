package com.example.brandat.ui.fragments.serach

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.R
import com.example.brandat.databinding.FragmentSearchBinding
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.ProfileActivity
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.ui.fragments.cart.IBadgeCount
import com.example.brandat.ui.fragments.category.OnImageFavClickedListener
import com.example.brandat.ui.fragments.category.ProductRvAdapter
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class SearchFragment : Fragment(), OnImageFavClickedListener {

    private val viewModel: SearchViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var iSnackBar: ISnackBar
    private lateinit var bageCountI: IBadgeCount

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
            viewModel.liveSearch.observe(viewLifecycleOwner) {
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
            adapter = productRvAdapter

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
//                bageCountI.updateBadgeCount(Paper.book().read<Int>("count")!!)
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
        builder.setTitle(getString(R.string.worning_msg))
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
    }

    private fun showSnackBar(cart: Cart) {
        val snackbar = Snackbar.make(binding.recyclerSearch, getString(R.string.added_to_cart), Snackbar.LENGTH_LONG)
        snackbar.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snackbar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.purple_700))
        snackbar.setAction("undo") {
            cartViewModel.removeProductFromCart(cart)
            Toast.makeText(requireContext(), getString(R.string.removed_from_cart), Toast.LENGTH_SHORT).show()
        }.show()
    }

    private fun showMessage() {
        Snackbar.make(requireView(), getString(R.string.no_connection), Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
                requireActivity().resources.getColor(
                    R.color.black2
                )
            )
            .setActionTextColor(requireActivity().resources.getColor(R.color.white)).setAction("Close") {
            }.show()
    }

    override fun onResume() {
        super.onResume()
        if (binding.search.query != null) {
            val list: MutableList<ProductDetails> = ArrayList()
            itemList.forEach {
                Log.e("TAG", "onResume: type --> ${it.productType}")
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