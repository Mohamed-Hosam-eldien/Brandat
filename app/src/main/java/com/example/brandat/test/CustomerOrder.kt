package com.example.brandat.test

import com.google.gson.annotations.SerializedName
enum class FinancialStatus {
    Cash {
        override fun toString(): String {
            return "cash"
        }
    },
    PayPal {
        override fun toString(): String {
            return "PayPal"
        }
    };

}
 data class OrderModel(

     val order: CustomerOrder
 )
data class CustomerOrder(
//    @SerializedName( "customer")
//   val customer:Customer?= null,
    @SerializedName( "email")
   val email: String? = "",
    @SerializedName( "line_items")
    val line_items: List<LineItem>? = listOf(),
    @SerializedName( "billing_address")
    val billing_address: ShippingAddress = ShippingAddress(),
    @SerializedName( "shipping_address")
   val shipping_address: ShippingAddress = ShippingAddress(),
    @SerializedName("gateway")
   val gateway: String = "",



    )
