package com.example.brandat.test

import com.example.brandat.test.Order
import com.google.gson.annotations.SerializedName

data class Orders(
    @SerializedName("orders")
    var orders: Order
)