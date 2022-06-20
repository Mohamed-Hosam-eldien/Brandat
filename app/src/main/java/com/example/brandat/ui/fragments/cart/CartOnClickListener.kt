package com.example.brandat.ui.fragments.cart

import android.widget.TextView

interface CartOnClickListener {
   // fun onLongClicked(order: ArrayList<Cart>)
    fun onClicked(order: Cart)
    fun onPluseMinusClicked(count: Int, pId: Cart)
}