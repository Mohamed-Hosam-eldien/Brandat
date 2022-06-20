package com.example.brandat.ui.fragments.category

import android.widget.ImageView
import com.example.brandat.models.Favourite
import com.example.brandat.models.Product
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart

interface OnImageFavClickedListener {
    fun onItemClicked(currentProductId:Long)
    fun onFavClicked(favourite: ProductDetails, ivImage:ImageView)
     fun deleteFavourite(favouriteId: Long)
     fun onCartClicked(productDataToCartModel: Cart)
}