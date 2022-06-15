package com.example.brandat.models

import com.example.brandat.models.draftOrder.Customer
import com.google.gson.annotations.SerializedName

data class OrderModel(
     val order: CustomerOrder
     )

data class CustomerOrder(

    @SerializedName( "email")
    val email: String? = "",


    @SerializedName( "customer")
    val customer: Customer,

    @SerializedName( "line_items")
    val line_items: List<LineItem>? = listOf()

//    @SerializedName( "billing_address")
//    val billingAddress: BillingAddress?,
//    @SerializedName( "financial_status")
//    val financialStatus: String? = "",




)
