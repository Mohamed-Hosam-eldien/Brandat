package com.example.brandat.models.orderModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Orders(
    @PrimaryKey
    @SerializedName("orders")
    var orders: List<Order>
)