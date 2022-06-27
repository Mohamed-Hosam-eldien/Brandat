package com.example.brandat.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.orderModel.Address
import com.example.brandat.models.orderModel.LineItem
import com.example.brandat.ui.fragments.cart.Cart

fun List<Cart>.getPrice(): Double {
    var price = 0.0
    this.forEach {
        price += it.pQuantity * (it.pPrice?.toDouble() ?: 1.0)
    }
    return price
}


fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}


fun List<Cart>.toLineItem(): List<LineItem> {
    return map {
        LineItem(
            quantity = it.pQuantity,
            variant_id = it.variant_id,
            price = it.pPrice,
            name = it.pName,
            sku = it.pImage
        )
    }
}

fun convertToBillingAddress(address: CustomerAddress): Address {
    return Address(
        firstName = Constants.user.firstName,
        address1 = address.address1,
        lastName = Constants.user.lastName,
        city = address.city,
        country = address.country,
    )
}