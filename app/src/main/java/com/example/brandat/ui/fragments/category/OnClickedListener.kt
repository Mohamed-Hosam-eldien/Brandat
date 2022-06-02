package com.example.brandat.ui.fragments.category

import android.widget.ImageView
import com.example.brandat.models.Favourite
import com.example.brandat.models.Product

interface OnClickedListener {
    fun onItemClicked(currentProduct: ProductModel)
    fun onFavClicked(favourite: Favourite,ivImage:ImageView)
    abstract fun checkFavourite(favourite: Favourite,ivFav: ImageView)
}