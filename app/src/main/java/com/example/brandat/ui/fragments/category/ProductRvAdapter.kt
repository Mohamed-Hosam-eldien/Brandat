package com.example.brandat.ui.fragments.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandat.databinding.ProductItemBinding
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.ProductDiffUtil


class ProductRvAdapter(var onClickedListener: OnClickedListener) :
    RecyclerView.Adapter<ProductRvAdapter.ProductViewHolder>() {

    private var products = emptyList<ProductDetails>()


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
        holder.binding.ivCart.setOnClickListener {
            onClickedListener.onCartClicked(setProductDataToCartModel(currentProduct))

        }
        holder.itemView.setOnClickListener {
            onClickedListener.onClicked(currentProduct)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newData: List<ProductDetails>) {
        val productDiffutil = ProductDiffUtil(products, newData)
        val productDiffUtilResult = DiffUtil.calculateDiff(productDiffutil)
        products = ArrayList(newData)
        productDiffUtilResult.dispatchUpdatesTo(this)
    }

    fun setProductDataToCartModel(productDetails: ProductDetails): Cart {
        return Cart(
            productDetails.title,
            pImage = productDetails.imageProduct.src,
            pId = productDetails.id
        )
    }

    //============================================================
    class ProductViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productList: ProductDetails) {
            binding.product = productList
            binding.executePendingBindings()
        }

    }
}