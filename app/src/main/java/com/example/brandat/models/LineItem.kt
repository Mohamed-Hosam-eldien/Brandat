package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class LineItem(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String = "",
    @SerializedName("discount_allocations")
    val discountAllocations: List<Any> = emptyList(),
    @SerializedName("duties")
    val duties: List<Any> = emptyList(),
    @SerializedName("fulfillable_quantity")
    val fulfillableQuantity: Int = 0,
    @SerializedName("fulfillment_service")
    val fulfillmentService: String = "",
    @SerializedName("fulfillment_status")
    val fulfillmentStatus: Any  = "",
    @SerializedName("gift_card")
    val giftCard: Boolean = false,
    @SerializedName("grams")
    val grams: Int=0,
    @SerializedName("id")
    val id: Long=0,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_exists")
    val productExists: Boolean = false,
    @SerializedName("product_id")
    val productId: Long = 0,
    @SerializedName("properties")
    val properties: List<Any>  = emptyList(),
    @SerializedName("quantity")
    val quantity: Int = 0,
    @SerializedName("requires_shipping")
    val requiresShipping: Boolean = false,
    @SerializedName("sku")
    val sku: String = "",
    @SerializedName("tax_lines")
    val taxLines: List<Any> = emptyList(),
    @SerializedName("taxable")
    val taxable: Boolean=false,
    @SerializedName("title")
    val title: String,
    @SerializedName("total_discount")
    val totalDiscount: String="",
    @SerializedName("vendor")
    val vendor: String
)