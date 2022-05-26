package com.example.brandat.ui.fragments.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.ProductItemBinding
import com.example.brandat.utils.ProductDiffUtil


class ProductRvAdapter : RecyclerView.Adapter<ProductRvAdapter.ProductViewHolder>() {

    private var products = emptyList<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            com.example.brandat.databinding.ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = products[position]
        holder.bind(currentProduct)
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newData: ArrayList<ProductModel>) {
        val productDiffutil=ProductDiffUtil(products,newData)
        val productDiffUtilResult=DiffUtil.calculateDiff(productDiffutil)
        products = ArrayList(newData)
        productDiffUtilResult.dispatchUpdatesTo(this)

    }

    //============================================================
    class ProductViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productList: ProductModel) {
            binding.product=productList
            binding.executePendingBindings()

        }

    }
}