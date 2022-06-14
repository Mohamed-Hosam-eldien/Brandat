package com.example.brandat.utils

import com.example.brandat.ui.fragments.cart.Cart

fun List<Cart>.getPrice() :Double{
    var price = 0.0
    this.forEach {
        price += it.pQuantity* (it.pPrice.toDouble() ?: 1.0)
    }
     return price
}



//fun List<Cart>.toLineItem(): List<LineItem> {
//    return map {
//        LineItem(
//            quantity = it.pQuantity,
//            variant_id= it.variantId!!
//            )
//    }
//}