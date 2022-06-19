package com.example.brandat.utils

import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.orderModel.Address
import com.example.brandat.models.orderModel.CustomerOrder
import com.example.brandat.models.orderModel.LineItem
import com.example.brandat.ui.fragments.cart.Cart

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
        firstName = "Doaa",
        address1="el sharkia" ,
        address = "sharkia",
        lastName = "essam",
        city = "sh",
        country = "egypt",
        phone = "01023566677",
    )



}