package com.example.brandat.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.orderModel.Address
import com.example.brandat.models.orderModel.CustomerOrder
import com.example.brandat.models.orderModel.LineItem
import com.example.brandat.ui.fragments.cart.Cart
import java.text.NumberFormat

fun List<Cart>.getPrice() :Double{
    var price = 0.0
    this.forEach {
        price += it.pQuantity* (it.pPrice.toDouble() ?: 1.0)
    }
     return price
}



fun List<Cart>.toLineItem(): List<LineItem> {
    return map {
        LineItem(
            quantity = it.pQuantity,
            variant_id = it.variantId!!,
            price = it.pPrice,
            name = it.pName,
            sku = it.pImage
        )
    }
}

fun convertToBillingAddress(address: CustomerAddress): Address {
    return Address(
        firstName = "doaa",
        address1 = address.address1,

        lastName = "Essam",
        city = address.city,
        country = address.country,
    )
}



 fun String.convertCurrency(context: Context?): String {
    return convertToCurrency(this, context)
}

fun convertToCurrency(s: String, context: Context?): String {
//
    return ""
}


