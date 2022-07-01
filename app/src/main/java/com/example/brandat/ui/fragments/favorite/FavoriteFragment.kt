package com.example.brandat.ui.fragments.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.R
import com.example.brandat.databinding.FragmentFavoriteBinding
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.ui.fragments.cart.IBadgeCount
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import com.example.brandat.utils.Constants.Companion.showDialogToRegister
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
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var bageCountI: IBadgeCount

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(context), container, false)
        if(ConnectionUtil.isNetworkAvailable(requireContext())){
            setupUi()
        }else{

            binding.animationView.visibility = View.VISIBLE
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bageCountI = requireActivity() as MainActivity
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
        val bundle = Bundle()
        bundle.putLong("productId", productId)

        findNavController().navigate(
            R.id.action_favoriteFragment_to_productDetailsFragment,
            bundle
        )
    }

    override fun onRemoveClicked(favourite: ProductDetails) {
        showAlertDialog(favourite)
    }

    private fun showAlertDialog(favourite: ProductDetails) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            //favouriteViewModel.removeFavouriteProduct(favourite.productId)
    //            favouriteViewModel.getFavouriteProducts()
            removeFromDraft(favourite.id)
            requireActivity().recreate()
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ ->

        }
        builder.setTitle(getString(R.string.delete))
        builder.setMessage(getString(R.string.deletfavourite))
        builder.create().show()
    }


    private fun removeFromDraft(favouriteId: Long) {
        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("fav")
            .child(favouriteId.toString())
            .removeValue()
    }

    override fun onCartClicked(product: Cart, ivCart: ImageView) {
        if (Paper.book().read<String>("email") == null) {
            showDialogToRegister(getString(R.string.login_to_add_to_cart))
        } else {

            if (ivCart.tag != "done") {
                addCartToDraft(product)
                ivCart.tag = "done"
                cartViewModel.addProductToCart(product)
                ivCart.setImageResource(R.drawable.ic_baseline_done_green)
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
        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("cart")
            .child(currentProduct.pId.toString())
            .setValue(currentProduct)
    }

    override fun onResume() {
        super.onResume()
        if(Constants.user.id <= 0) {
            binding.empty.visibility = View.VISIBLE
            binding.rvFavorits.visibility = View.GONE
        }
    }

}

