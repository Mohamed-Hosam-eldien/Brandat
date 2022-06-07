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
class CartFragment : Fragment(), CartOnClickListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartRvAdapter

    //    private var cartList: ArrayList<Cart> = ArrayList()
    private val cartViewModel: CartViewModel by viewModels()
    private val totalPrice = 0

    //private val args by navArgs<CartFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(LayoutInflater.from(context), container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        cartViewModel.getAllPrice()

        cartViewModel.allPrice.observe(viewLifecycleOwner) {
            binding.tvTprice.text = "$it $"
        }

        binding.buyButn.setOnClickListener {
            //go to eng hossam
            startActivity(Intent(requireContext(), OrderStatus::class.java))
        }
        cartViewModel.getAllCartproduct()

        cartViewModel.cartProduct.observe(viewLifecycleOwner) {
            Log.e("Cart", "============${it.size} ")
            cartAdapter.setData(it)
            checkEmptyList(it)
            binding.tvConut.text = "(${it.size} item)"
            cartAdapter.notifyDataSetChanged()
        }

    }

    private fun setUpRecyclerView() {
        cartAdapter = CartRvAdapter(requireContext(), requireActivity(), this)
        binding.rvCart.apply {
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            //layoutManager.stackFromEnd = true
            // layoutManager.reverseLayout = true
            setLayoutManager(layoutManager)

            adapter = cartAdapter
        }
    }

    private fun setRvItemSwipe() {

    }

    override fun onLongClicked(order: ArrayList<Cart>) {
        Log.d("TAG", "size before delete: ${order.size}")
        cartViewModel.removeSelectedProductsFromCart(order)
        cartViewModel.getAllCartproduct()
        Log.d("TAG", "size after delete: ${order.size}")

    }

    override fun onClicked(order: Cart) {
        //show dialog
        cartViewModel.removeProductFromCart(order)
        cartViewModel.getAllCartproduct()
        requireActivity().recreate()
    }

    override fun onPluseMinusClicked(count: Int, pId: Long, price: String) {

        val priceChange = price.toDouble()
        val _price = (count * priceChange)

        val currentOrder = Cart(pQuantity = count, pId = pId, tPrice = _price)

        cartViewModel.updateOrder(currentOrder)

        cartViewModel.getAllCartproduct()
        cartViewModel.getAllPrice()
        cartAdapter.notifyDataSetChanged()

        //requireActivity().recreate()
    }

    private fun checkEmptyList(list: List<Cart>) {
        if (list.isEmpty()) {
            binding.ivPlaceholder.visibility = View.VISIBLE
            binding.rvCart.visibility = View.GONE
        } else {
            binding.ivPlaceholder.visibility = View.GONE
            binding.rvCart.visibility = View.VISIBLE

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // binding = null
        cartAdapter.clearContextualActionMode()
    }

}