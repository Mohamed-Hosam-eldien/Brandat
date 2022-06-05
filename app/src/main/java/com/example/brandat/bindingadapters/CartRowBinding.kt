package com.example.brandat.bindingadapters

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.example.brandat.ui.fragments.cart.Cart

class CartRowBinding {

    companion object {


        @JvmStatic
        @BindingAdapter("onMinusClicked")
        fun onMinusClicked(minues:CardView,cart:Cart){
            minues.setOnClickListener {

                Log.e("TAG", "onMinusClicked: ", )
            }
        }

    }

}