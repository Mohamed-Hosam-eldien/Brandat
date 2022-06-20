package com.example.brandat.models.orderModel

import com.google.gson.annotations.SerializedName

data class AllOrderResponse(
    @SerializedName("orders")
    var orders: List<Order>
)
