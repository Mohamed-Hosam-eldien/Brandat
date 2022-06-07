package com.example.brandat.ui.fragments.favorite

import com.example.brandat.models.Favourite
import com.example.brandat.models.Product
import com.example.brandat.ui.fragments.cart.Cart

interface OnclickListener {
    fun onItemClicked(productId:Long)
    fun onRemoveClicked(favourite: Favourite )
    fun onCartClicked(product: Cart)

}