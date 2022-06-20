package com.example.brandat.ui.fragments.favorite

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandat.databinding.FavouriteItemBinding
import com.example.brandat.models.Favourite
import com.example.brandat.models.OrderResponse
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.FavouriteDiffUtil

class FavouriteAdapter(val context: Context,var onClickedListener: OnclickListener) :
    RecyclerView.Adapter<FavouriteAdapter.ProductViewHolder>() {

    private var fav_products = emptyList<ProductDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            FavouriteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = fav_products[position]

        //holder.binding.ivProductFav.setImageBitmap(currentProduct.productImage)
        Glide.with(context).load(currentProduct.imageProduct.src).into(holder.binding.ivProductFav)
        holder.binding.tvProductName.text = currentProduct.title.lowercase()
        holder.binding.tvProductPrice.text = currentProduct.variants[0].price

        holder.itemView.setOnClickListener {
            onClickedListener.onItemClicked(currentProduct.id)
        }
        holder.binding.ivFavorite.setOnClickListener {
            onClickedListener.onRemoveClicked(currentProduct)
        }
        holder.binding.ivCart.setOnClickListener {
            onClickedListener.onCartClicked(setProductDataToCartModel(currentProduct))
            Log.e("TAG", "onBindViewHolder:${setProductDataToCartModel(currentProduct).pImage} ")
        }
    }

    override fun getItemCount(): Int {
        return fav_products.size
    }

//    fun setData(newData: List<Favourite>) {
//        val favouriteDiffUtil = FavouriteDiffUtil(fav_products, newData)
//        val favDiffUtilResult = DiffUtil.calculateDiff(favouriteDiffUtil)
//        fav_products = ArrayList(newData)
//        favDiffUtilResult.dispatchUpdatesTo(this)
//
//    }

    fun setData(newData: MutableList<ProductDetails>) {
        val favouriteDiffUtil = FavouriteDiffUtil(fav_products, newData)
        val favDiffUtilResult = DiffUtil.calculateDiff(favouriteDiffUtil)
        fav_products = ArrayList(newData)
        favDiffUtilResult.dispatchUpdatesTo(this)

    }

    private fun setProductDataToCartModel(favProduct: ProductDetails): Cart {
        // Log.d("TAG", "setProductDataToCartModel: ${productDetails.variants[0].price.toInt()}")
        return Cart(
            favProduct.title,
            variant_id = favProduct.variants[0].id,
            favProduct.variants[0].price,
            favProduct.imageProduct.src,
            pId = favProduct.id
        )
    }

    //============================================================
    class ProductViewHolder(val binding: FavouriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}
