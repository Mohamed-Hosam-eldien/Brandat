package com.example.brandat.ui.fragments.serach

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
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
import com.example.brandat.ui.ProfileActivity
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.ui.fragments.category.OnImageFavClickedListener
import com.example.brandat.ui.fragments.category.ProductRvAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlin.collections.ArrayList

@AndroidEntryPoint
class SearchFragment : Fragment(), OnImageFavClickedListener {

    private val viewModel: SearchViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var binding: FragmentSearchBinding
    private var itemList = emptyList<ProductDetails>()
    private lateinit var productRvAdapter: ProductRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.bind(inflater.inflate(R.layout.fragment_search, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllProduct()
        viewModel.liveSearch.observe(viewLifecycleOwner) {
            itemList = it
        }

        binding.imgBack.setOnClickListener { requireActivity().finish() }

        productRvAdapter = ProductRvAdapter(this)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val list: MutableList<ProductDetails> = ArrayList()
                if (query != null) {
                    itemList.forEach {
                        if(it.title.contains(query.uppercase())
                            || it.handle.contains(query.uppercase())
                            || it.productType.contains(binding.search.query.toString().uppercase())
                            || it.vendor.contains(query.uppercase())
                            || it.variants[0].price.contains(query.uppercase())) {
                            list.add(it)
                        }
                    }
                    requireActivity().runOnUiThread{
                        setDataToAdapter(list)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
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
        if(items.isNotEmpty()) {
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

    }

    override fun onCartClicked(currentProduct: Cart) {
        if (Paper.book().read<String>("email") == null) {
            showDialog()
        } else {
            cartViewModel.addProductToCart(currentProduct)
            showSnackBar(currentProduct)
        }
    }

    override fun deleteFavourite(favouriteId: Long) {
        TODO("Not yet implemented")
    }

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

    private fun showSnackBar(cart: Cart) {
        val snackbar = Snackbar.make(binding.recyclerSearch, "Added to Cart", Snackbar.LENGTH_LONG)
        snackbar.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snackbar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.purple_700))
        snackbar.setAction("undo") {
            cartViewModel.removeProductFromCart(cart)
            Toast.makeText(requireContext(), "Removed from Cart!", Toast.LENGTH_SHORT).show()
        }.show()
    }

    override fun onResume() {
        super.onResume()
        if(binding.search.query != null) {
            val list: MutableList<ProductDetails> = ArrayList()
            itemList.forEach {
                Log.e("TAG", "onResume: type --> ${it.productType}", )
                if (it.title.contains(binding.search.query.toString().uppercase())
                    || it.handle.contains(binding.search.query.toString().uppercase())
                    || it.vendor.contains(binding.search.query.toString().uppercase())
                    || it.productType.contains(binding.search.query.toString().uppercase())
                    || it.variants[0].price.contains(binding.search.query.toString().uppercase())) {

                    list.add(it)
                }
            }
            requireActivity().runOnUiThread {
                setDataToAdapter(list)
            }
        }
    }

}