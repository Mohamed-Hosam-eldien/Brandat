package com.example.brandat.ui.fragments.favorite

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.FragmentFavoriteBinding
import com.example.brandat.models.Favourite
import com.example.brandat.models.OrderResponse
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.ProfileActivity
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class FavoriteFragment : Fragment(), OnclickListener {
    private lateinit var favouriteAdapter: FavouriteAdapter
    private lateinit var binding: FragmentFavoriteBinding
    private val favourite: List<Favourite> = ArrayList()
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(context), container, false)
        setupUi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


//    private fun observeShowData() {
//        favouriteViewModel.getFavouriteProducts()
//        favouriteViewModel.getFavouriteProducts.observe(viewLifecycleOwner) {
//            it?.let {
//                if (it.isEmpty()) {
//                    print(it.size)
//                    binding.empty.visibility = View.VISIBLE
//
//                } else {
//                    binding.empty.visibility = View.GONE
//                }
//                favouriteAdapter.setData(it)
//            }
//
//        }
//
//    }

    private fun setupUi() {
        favouriteAdapter = FavouriteAdapter(requireContext(),this)
        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("fav")
            .addValueEventListener(object :ValueEventListener {
                val list = mutableListOf<ProductDetails>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(data in snapshot.children) {
                        list.add(data.getValue(ProductDetails::class.java)!!)
                    }
                    if(list.isNotEmpty()) {
                        binding.rvFavorits.apply {
                            val layoutManager =
                                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
                            setLayoutManager(layoutManager)
                            favouriteAdapter.setData(list)
                            adapter = favouriteAdapter
                            binding.rvFavorits.visibility = View.VISIBLE
                            binding.empty.visibility = View.GONE
                        }
                    } else {
                        binding.rvFavorits.visibility = View.GONE
                        binding.empty.visibility = View.VISIBLE
                    }

                }

                override fun onCancelled(error: DatabaseError) {}

            })

//        favouriteAdapter = FavouriteAdapter(requireContext(),this)
//        binding.rvFavorits.apply {
//            val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
//            setLayoutManager(layoutManager)
//            favouriteAdapter.setData(favourite)
//            adapter = favouriteAdapter
//            observeShowData()
//        }
    }


    override fun onItemClicked(productId: Long) {

    }

    override fun onRemoveClicked(favourite: ProductDetails) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            //favouriteViewModel.removeFavouriteProduct(favourite.productId)
//            favouriteViewModel.getFavouriteProducts()
            removeFromDraft(favourite.id)
            requireActivity().recreate()
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete!")
        builder.setMessage("Are you sure you want to delete this item from favourite?")
        builder.create().show()
    }


    private fun removeFromDraft(favouriteId: Long) {
        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("fav")
            .child(favouriteId.toString())
            .removeValue()
    }

    override fun onCartClicked(product: Cart) {
        if (Paper.book().read<String>("email") == null) {
            showDialog()
        } else {
            cartViewModel.addProductToCart(product)
            Toast.makeText(requireContext(), "Added To Cart", Toast.LENGTH_SHORT).show()
        }
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

    override fun onResume() {
        super.onResume()
        if(Constants.user.id <= 0) {
            binding.empty.visibility = View.VISIBLE
            binding.rvFavorits.visibility = View.GONE
        }
    }

}

