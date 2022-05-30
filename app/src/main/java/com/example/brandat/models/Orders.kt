package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class Orders(
    @SerializedName("orders")
    val orders: List<Order>
)