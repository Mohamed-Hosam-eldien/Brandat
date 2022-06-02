package com.example.brandat.ui.fragments.favorite

import com.example.brandat.models.Favourite
import com.example.brandat.models.Product

interface OnclickListener {
    fun onItemClicked(productId:Long)
    fun onRemoveClicked(favourite: Favourite )
}