package com.example.brandat.ui.fragments.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.FragmentCartBinding
import com.example.brandat.ui.OrderStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(), setOnLongClicked {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartRvAdapter
    private var cartList: ArrayList<Cart> = ArrayList()
    private val cartViewModel: CartViewModel by viewModels()
    //private val args by navArgs<CartFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Log.e("args", "onCreate: ${args.product.title}", )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(LayoutInflater.from(context), container, false)

        setUpRecyclerView()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buyButn.setOnClickListener {
            //go to eng hossam
            startActivity(Intent(requireContext(), OrderStatus::class.java))
        }

        cartViewModel.getAllCartproduct()

        cartViewModel.cartProduct.observe(viewLifecycleOwner) {
            cartList = it as ArrayList<Cart>
            Log.e("Cart", "============${cartList} ")
            cartAdapter.setData(cartList)
            binding.tvConut.text = "(${cartList.size.toString()} item)"
        }

    }

    private fun setUpRecyclerView() {
        cartAdapter = CartRvAdapter(requireContext(), requireActivity(), this)
        binding.rvCart.apply {
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//            layoutManager.stackFromEnd = true
//            layoutManager.reverseLayout = true
            setLayoutManager(layoutManager)

            adapter = cartAdapter
        }
    }

    private fun setRvItemSwipe() {

    }

    override fun onLongClicked(order: ArrayList<Cart>) {
        cartViewModel.removeSelectedProductsFromCart(order)
        cartViewModel.getAllCartproduct()
    }

    override fun onClicked(order: Cart) {
        cartViewModel.removeProductFromCart(order)
        cartViewModel.getAllCartproduct()
    }
}