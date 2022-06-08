package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("accepts_marketing")
    val acceptsMarketing: Boolean=true,
    @SerializedName("accepts_marketing_updated_at")
    val acceptsMarketingUpdatedAt: String="",
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String="",
    @SerializedName("created_at")
    val createdAt: String="",
    @SerializedName("currency")
    val currency: String="",
//    @SerializedName("default_address")
//    val defaultAddress: DefaultAddress,
    @SerializedName("email")
    val email: String,//=========================
    @SerializedName("first_name")
    val firstName: String,//=====================
    @SerializedName("id")
    val id: Long=0L,//==========================
    @SerializedName("last_name")
    val lastName: String="aziza",//====================
    @SerializedName("last_order_id")
    val lastOrderId: Long=0L,
    @SerializedName("last_order_name")
    val lastOrderName: String="",
    @SerializedName("note")
    val note: Any="7",//=============================
    @SerializedName("orders_count")
    val ordersCount: Int=0,
    @SerializedName("phone")
    val phone: String="99",//==========================
    @SerializedName("total_spent")
    val totalSpent: String="",
    @SerializedName("updated_at")
    val updatedAt: String="",
    @SerializedName("verified_email")
    val verifiedEmail: Boolean=true//==========================



/*

    @SerializedName( "id")
    val customerId: Long? = null,

    @SerializedName( "email")
    val email: String?,

    @SerializedName( "phone")
    val phone: String? = "",

    @SerializedName( "first_name")
    val firstName: String? = "",

    @SerializedName( "last_name")
    val lastName: String? = "",

    @SerializedName( "orders_count")
    val ordersCount: Int = 0,

    @SerializedName( "state")
    val state: String? = "",

    @SerializedName( "currency")
    val currency: String? = "EGP",

    @SerializedName( "note")
    val note: String? = "",

    @SerializedName( "total_spent")
    val totalSpent: String? = "",
//
//    @SerializedName( "addresses")
//    val addresses: List<Address>? = listOf(),

    @SerializedName( "password")
    val password: String? = "",

    @SerializedName( "password_confirmation")
    val passwordConfirmation: String? = "",


 */
)