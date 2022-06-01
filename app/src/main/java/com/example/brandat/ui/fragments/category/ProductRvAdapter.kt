package com.example.brandat.ui.fragments.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandat.databinding.ProductItemBinding
import com.example.brandat.models.Product
import com.example.brandat.utils.ProductDiffUtil
import okhttp3.internal.wait


class ProductRvAdapter(var onClickedListener: OnClickedListener) :
    RecyclerView.Adapter<ProductRvAdapter.ProductViewHolder>() {

    private var products = emptyList<Product>()

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
        val currentProduct = products[position]
        holder.bind(currentProduct)

        Glide.with(holder.binding.root)
            .load(currentProduct.imageProduct.src)
            .into(holder.binding.ivProduct)

        holder.itemView.setOnClickListener {
            onClickedListener.onClicked(currentProduct)

        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newData: List<Product>) {
        val productDiffutil = ProductDiffUtil(products, newData)
        val productDiffUtilResult = DiffUtil.calculateDiff(productDiffutil)
        products = ArrayList(newData)
        productDiffUtilResult.dispatchUpdatesTo(this)
    }

    //============================================================
    class ProductViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productList: Product) {
            binding.product = productList

            binding.executePendingBindings()
        }

    }
}