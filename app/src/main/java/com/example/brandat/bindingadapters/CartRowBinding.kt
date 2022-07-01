package com.example.brandat.bindingadapters

import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.example.brandat.ui.fragments.cart.Cart

class CartRowBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("onMinusClicked")
        fun onMinusClicked(minues:CardView,cart:Cart){
            minues.setOnClickListener {

            }
        }

    }

}