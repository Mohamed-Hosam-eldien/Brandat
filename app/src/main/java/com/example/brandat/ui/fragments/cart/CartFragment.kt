package com.example.brandat.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.R
import com.example.brandat.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartRvAdapter
    private var cartList: ArrayList<Cart> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartList.add(Cart("CLASSIC BACKPACK","235 $", R.drawable.dress_kid,2))
        cartList.add(Cart("CLASSIC BACKPACK","235 $", R.drawable.bag,2))
        cartList.add(Cart("CLASSIC BACKPACK","235 $", R.drawable.sh5,2))
        cartList.add(Cart("Dress","235 $", R.drawable.sh2,2))
        cartList.add(Cart("CLASSIC BACKPACK","235 $", R.drawable.dress_kid,2))
        cartList.add(Cart("Dress","235 $", R.drawable.dress_kid,2))
        cartList.add(Cart("CLASSIC BACKPACK","235 $", R.drawable.bag,2))
        cartList.add(Cart("CLASSIC BACKPACK","235 $", R.drawable.dress_kid,2))
        cartList.add(Cart("Dress","235 $", R.drawable.sh3,2))
        cartList.add(Cart("CLASSIC BACKPACK","235 $", R.drawable.dress_kid,2))
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
    }

    private fun setUpRecyclerView() {
        binding.rvCart.apply {
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            cartAdapter= CartRvAdapter()
            cartAdapter.setData(cartList)
            adapter=cartAdapter
        }
    }
}