package com.example.brandat.ui.fragments.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.FavouriteItemBinding
import com.example.brandat.models.Favourite
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.Constants
import com.example.brandat.utils.FavouriteDiffUtil
import com.example.brandat.utils.convertCurrency


class FavouriteAdapter (context: Context,var onClickedListener: OnclickListener) : RecyclerView.Adapter<FavouriteAdapter.ProductViewHolder>() {

        private var fav_products = emptyList<Favourite>()
    var currency :String = "USD"

    init {
        var  sharedPreferences =context.getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)
        currency  = sharedPreferences.getString(Constants.CURRENCY_TYPE,"USD")!!

    }
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
            holder.binding.tvProductPrice.text=convertCurrency(currentProduct.productPrice.toDouble(),holder.itemView.context)
            holder.binding.favCurrency.text= currency

            holder.itemView.setOnClickListener {

                onClickedListener.onItemClicked(currentProduct.productId)

            }
            holder.binding.ivFavorite.setOnClickListener {
                onClickedListener.onRemoveClicked(currentProduct)

            }
            holder.binding.ivCart.setOnClickListener {
                onClickedListener.onCartClicked(setProductDataToCartModel(currentProduct))
            }
        }

        override fun getItemCount():Int {
            return fav_products.size
        }

        fun setData(newData: List<Favourite>) {
            val favouriteDiffUtil= FavouriteDiffUtil(fav_products,newData)
            val favDiffUtilResult= DiffUtil.calculateDiff(favouriteDiffUtil)
            fav_products = ArrayList(newData)
            favDiffUtilResult.dispatchUpdatesTo(this)

        }
    private fun setProductDataToCartModel(favProduct: Favourite): Cart {
        // Log.d("TAG", "setProductDataToCartModel: ${productDetails.variants[0].price.toInt()}")
        return Cart(
            favProduct.productName,
            favProduct.productPrice,
            pImage = favProduct.productImage.toString(),
            pId = favProduct.productId
        )
    }
        //============================================================
        class ProductViewHolder(val binding: FavouriteItemBinding) :
            RecyclerView.ViewHolder(binding.root)


    }
