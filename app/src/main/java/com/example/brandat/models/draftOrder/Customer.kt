package com.example.brandat.models.draftOrder

import com.example.brandat.models.DefaultAddress
import com.google.gson.annotations.SerializedName

data class Customer(
//    val accepts_marketing: Boolean,
//    val accepts_marketing_updated_at: String,
//    val admin_graphql_api_id: String,
//    val created_at: String,
//    val currency: String,
//    val default_address: DefaultAddress,
//    val email: String,
//    val first_name: String,
//    val id: Long,
//    val last_name: String,
//    val last_order_id: Any,
//    val last_order_name: Any,
//    val marketing_opt_in_level: Any,
//    val multipass_identifier: String,
//    val note: String,
//    val orders_count: Int,
//    val phone: Any,
//    val sms_marketing_consent: Any,
//    val state: String,
//    val tags: String,
//    val tax_exempt: Boolean,
//    val tax_exemptions: List<Any>,
//    val total_spent: String,
//    val updated_at: String,
//    val verified_email: Boolean

    @SerializedName("accepts_marketing")
    val acceptsMarketing: Boolean = true,
    @SerializedName("accepts_marketing_updated_at")
    val acceptsMarketingUpdatedAt: String = "2022-06-08T19:56:03+02:00",
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String = "",
    @SerializedName("created_at")
    val createdAt: String = "2022-06-08T19:56:03+02:00",
    @SerializedName("currency")
    val currency: String = "EGP",
    @SerializedName("default_address")
    val defaultAddress: DefaultAddress? = null,
    @SerializedName("email")
    val email: String,//=========================
    @SerializedName("first_name")
    val firstName: String,//=====================
    @SerializedName("id")
    val id: Long = 0L,//==========================
    @SerializedName("last_name")
    val lastName: String,//====================
    @SerializedName("last_order_id")
    val lastOrderId: Any? = null,
    @SerializedName("last_order_name")
    val lastOrderName: Any? = null,
    @SerializedName("note")
    val note: Any? = null,
    @SerializedName("orders_count")
    val ordersCount: Int = 0,
    @SerializedName("phone")
    val phone: Any? = null,//==========================
    @SerializedName("total_spent")
    val totalSpent: String = "0.00",
    @SerializedName("updated_at")
    val updatedAt: String = "2022-06-08T19:56:03+02:00",
    @SerializedName("verified_email")
    val verifiedEmail: Boolean = true,
    @SerializedName("state")
    val state: String = "disabled",
    @SerializedName("multipass_identifier")
    val multipass_identifier: Any? = null,
    @SerializedName("tax_exempt")
    val tax_exempt: Boolean = false,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("addresses")
    val addresses: List<DefaultAddress>? = listOf(),
    @SerializedName("marketing_opt_in_level")
    val marketing_opt_in_level: Any? = null,
    @SerializedName("sms_marketing_consent")
    val sms_marketing_consent: Any? = null
)