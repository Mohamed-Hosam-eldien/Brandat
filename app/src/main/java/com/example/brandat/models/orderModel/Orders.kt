package com.example.brandat.models.orderModel

import com.google.gson.annotations.SerializedName

data class Orders(
    @SerializedName("orders")
    var orders: Order
)