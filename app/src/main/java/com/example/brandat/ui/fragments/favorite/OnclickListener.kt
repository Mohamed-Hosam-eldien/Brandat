package com.example.brandat.ui.fragments.favorite

import android.widget.ImageView
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart

interface OnclickListener {
    fun onItemClicked(productId:Long)
    fun onRemoveClicked(favourite: ProductDetails)
    fun onCartClicked(product: Cart, ivCart: ImageView)
}