package com.example.brandat.ui.fragments.newcategory

import android.widget.ImageView
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart

interface OnImageFavClickedListener {
    fun onItemClicked(currentProductId: Long)
    fun onFavClicked(favourite: ProductDetails, ivImage: ImageView)
    fun deleteFavourite(favouriteId: Long, ivFavorite: ImageView)
    fun onCartClicked(product: Cart, ivCart: ImageView)
}