package com.example.brandat.ui.fragments.favorite

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.FavouriteItemBinding
import com.example.brandat.models.Favourite
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.FavouriteDiffUtil

class FavouriteAdapter(var onClickedListener: OnclickListener) :
    RecyclerView.Adapter<FavouriteAdapter.ProductViewHolder>() {

    private var fav_products = emptyList<Favourite>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            com.example.brandat.databinding.FavouriteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = fav_products[position]

        holder.binding.ivProductFav.setImageBitmap(currentProduct.productImage)
        holder.binding.tvProductName.text = currentProduct.productName
        holder.binding.tvProductPrice.text = currentProduct.productPrice

        holder.itemView.setOnClickListener {
            onClickedListener.onItemClicked(currentProduct.productId)
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

    fun setData(newData: List<Favourite>) {
        val favouriteDiffUtil = FavouriteDiffUtil(fav_products, newData)
        val favDiffUtilResult = DiffUtil.calculateDiff(favouriteDiffUtil)
        fav_products = ArrayList(newData)
        favDiffUtilResult.dispatchUpdatesTo(this)

    }

    private fun setProductDataToCartModel(favProduct: Favourite): Cart {
        // Log.d("TAG", "setProductDataToCartModel: ${productDetails.variants[0].price.toInt()}")
        return Cart(
            favProduct.productName,
            favProduct.productPrice,
            pImageFav = favProduct.productImage,
            pId = favProduct.productId
        )
    }

    //============================================================
    class ProductViewHolder(val binding: FavouriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}
