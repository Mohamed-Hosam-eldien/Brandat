package com.example.brandat.ui.fragments.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.CartItemBinding
import com.example.brandat.utils.CartDiffUtil

class CartRvAdapter : RecyclerView.Adapter<CartRvAdapter.CartViewHolder>() {
    private var carts: List<Cart> = ArrayList()

    fun setData(newData: ArrayList<Cart>) {
        val cartDiffUtil=CartDiffUtil(carts,newData)
        val cartDiffUtilResult=DiffUtil.calculateDiff(cartDiffUtil)
        carts = newData
        cartDiffUtilResult.dispatchUpdatesTo(this)
    }

    class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartList: Cart) {
            binding.cart = cartList
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            CartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentCart = carts[position]
        holder.bind(currentCart)
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount() = carts.size

}