package com.example.brandat.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.brandat.ui.fragments.cart.Cart

class CartRowBinding {
    companion object {

        @JvmStatic
        @BindingAdapter("setProductImg")
        fun setProductImg(pImg:ImageView,img:Int){
            pImg.setImageResource(img)
        }

    }

}