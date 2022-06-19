package com.example.brandat.models.orderModel

import com.example.brandat.models.Customer
import com.google.gson.annotations.SerializedName

 data class OrderModel(
     val order: CustomerOrder
 )
data class CustomerOrder(
    @SerializedName( "customer")
    val customer:Customer?= null,
    @SerializedName( "email")
   val email: String? = "",
    @SerializedName( "line_items")
    val line_items: List<LineItem>? = listOf(),
    @SerializedName("gateway")
   val gateway: String = "",
    @SerializedName("billing_address")
    val billing_address: Address? = null,


    )
