package com.example.brandat.ui.fragments.category

import com.example.brandat.models.Product
import com.example.brandat.models.ProductDetails

interface OnClickedListener {
    fun onClicked(currentProduct: ProductDetails)
}