package com.example.brandat.ui.fragments.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.ProductItemBinding

class ProductRvAdapter(private val productList: ArrayList<ProductModel>) :
    RecyclerView.Adapter<ProductRvAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.ivProduct.setImageResource(productList[position].productImg)
        holder.binding.tvProductPrice.text = productList[position].ProductPrice
        holder.binding.tvProductName.text = productList[position].productName
    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class ProductViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}