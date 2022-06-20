package com.example.brandat.models.draft

import com.example.brandat.models.orderModel.LineItem
import com.google.gson.annotations.SerializedName

data class OrderModel(
    val order: CustomerOrder
)

data class CustomerOrder(

    @SerializedName("email")
    val email: String? = "",

//    @SerializedName("customer")
//    val customer: Customer,

    @SerializedName("note")
    val note: String,

    @SerializedName("line_items")
    val line_items: List<LineItem>? = listOf()

)
