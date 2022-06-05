package com.example.brandat.ui.fragments.category

import com.example.brandat.models.Product
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart

interface OnClickedListener {
    fun onClicked(currentProduct: ProductDetails)
    fun onCartClicked(currentProduct: Cart)
}