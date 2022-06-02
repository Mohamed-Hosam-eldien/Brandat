package com.example.brandat.bindingadapters

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

class FavouriteRowBinding {
    companion object{


        @BindingAdapter("setImgForFav")
        @JvmStatic
        fun setImgForProduct(image: ImageView, bitmap: Bitmap) {
            image.load(bitmap)

        }
    }
}